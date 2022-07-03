package com.example.demo.dao.supportTables;

public class ComeToDayButTheyNoInDB {
    private Long chatId;
    private String telephoneNum;

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum;
    }
}
