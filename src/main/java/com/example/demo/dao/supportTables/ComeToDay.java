package com.example.demo.dao.supportTables;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
public class ComeToDay {
    private Long chatId;
    private String telephoneNum;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate currencyDate = LocalDate.now(ZoneId.of("GMT+03:00"));

    public ComeToDay() {
    }

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + (patronymic != null ? patronymic : "") +
                ", ChatId " + chatId;
    }
}
