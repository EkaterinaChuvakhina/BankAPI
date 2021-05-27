package ru.sberstart.dategenerator;

import java.time.LocalDate;

public class DateExpireAfterThreeYears implements DateExpiryGenerator {
    @Override
    public LocalDate generate() {
        return LocalDate.now().plusYears(3);
    }
}
