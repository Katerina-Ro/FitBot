package com.example.demo.supportTables;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
public class DontComeToDay {
    private Long chatId;
    private String telephoneNum;
    private LocalDate currencyDate = LocalDate.now(ZoneId.of("GMT+03:00"));

    @Override
    public String toString() {
        return "Не придет сегодня: " +
                "Номер телефона: " + telephoneNum + '\'' +
                ", сегодня: " + currencyDate;
    }
}
