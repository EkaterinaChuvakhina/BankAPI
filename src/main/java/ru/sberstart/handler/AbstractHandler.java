package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHandler<T> implements HttpHandler {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String response = null;
        T result = null;
        int statusCode;
        try {
            if ("GET".equals(requestMethod)) {
                result = doGet(exchange);
            } else if ("POST".equals(requestMethod)) {
                result = doPost(exchange);
            } else {
                throw new IllegalArgumentException("Unknown request method " + requestMethod);
            }
            response = MAPPER.writer().writeValueAsString(result);
            statusCode = 200;
        } catch (RuntimeException e) {
            response = e.getMessage();
            statusCode = 500;
            e.printStackTrace();
        }
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
    }

    protected abstract T doPost(HttpExchange exchange);

    protected abstract T doGet(HttpExchange exchange);

    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
