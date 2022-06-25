package com.example.demo.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Visitors {

    private Long chatId;
    private String telephoneNum;
    private String surname;
    private String name;
    private String patronymic;
    private String newPhoneNumber;

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + patronymic;
    }
}