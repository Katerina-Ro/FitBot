package com.example.demo.dao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Pass {

    /**
     * В JavaFX для всех полей класса-модели предпочтительно использовать [Properties].
     * Property позволяет получать автоматические уведомления при любых изменениях переменных.
     * Это позволяет поддерживать синхронность представления и данных.
     */
    private Integer numPass;
    private String phoneNumber;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer visitLimit;
    private Integer freezeLimit;
    private LocalDate dateStartFreeze;

    @Override
    public String toString() {
        return "Номер телефона владельца абонемента " + phoneNumber + '\'' +
                ", Дата начала действия абонемента " + dateStart + '\'' +
                ", До какого числа абонемент (включительно) " + dateEnd + '\'' +
                ", На какое количество занятий куплен абонемент " + visitLimit;
    }
}