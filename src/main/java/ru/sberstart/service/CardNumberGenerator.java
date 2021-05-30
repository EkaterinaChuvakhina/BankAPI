package ru.sberstart.service;

import java.util.Random;

public class CardNumberGenerator {
    private static final Random random = new Random();

    public CardNumberGenerator() {
    }

    public String generate() {
        return String.valueOf(Math.abs(random.nextInt()));
    }
}
