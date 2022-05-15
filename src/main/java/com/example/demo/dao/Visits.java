package com.example.demo.dao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Visits {

    private Integer pass;
    private LocalDate dateVisit;
    private Integer countVisit;

    @Override
    public String toString() {
        return "Номер абонемента " + pass + '\'' +
                ", Дата посещения " + dateVisit + '\'' +
                ", Количество посещений " + countVisit;
    }
}