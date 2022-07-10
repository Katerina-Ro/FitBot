package com.example.demo.dao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Pass {
    private Integer numPass;
    private String phoneNumber;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer visitLimit;
    private Integer freezeLimit;
    private LocalDate dateStartFreeze;
    private Integer visitsLeft;

    @Override
    public String toString() {
        return "Номер телефона владельца абонемента " + phoneNumber + '\'' +
                ", Дата начала действия абонемента " + dateStart + '\'' +
                ", До какого числа абонемент (включительно) " + dateEnd + '\'' +
                ", На какое количество занятий куплен абонемент " + visitLimit + '\'' +
                ", Количество использованных в данном абонементе заморозок " + freezeLimit + '\'' +
                ", Дата начала последней заморозки " + dateStartFreeze;
    }
}