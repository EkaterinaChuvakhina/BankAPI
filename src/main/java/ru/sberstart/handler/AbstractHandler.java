package ru.sberstart.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.sberstart.exception.BusinessException;
import ru.sberstart.exception.ClientNotFoundException;
import ru.sberstart.exception.DaoException;
import ru.sberstart.exception.NegativeAmountException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHandler implements HttpHandler {
    private static final ObjectMapper MAPPER = new ObjectMapper();

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

//    public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
//        httpExchange.sendResponseHeaders(200, response.length());
//        OutputStream os = httpExchange.getResponseBody();
//        os.write(response.getBytes());
//        os.close();
//    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String response = null;
        Object result = null;
        Integer statusCode = null;
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
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        } catch (IllegalArgumentException e) {
            statusCode = 400;
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        } catch (BusinessException e) {
            response = e.getMessage();
            statusCode = 500;
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        } catch (DaoException e) {
            response = e.getMessage();
            statusCode = 500;
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        }
        exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
    }

    protected Object doPost(HttpExchange exchange) {
        return null;
    }

    protected Object doGet(HttpExchange exchange) {
        return null;
    }
}
