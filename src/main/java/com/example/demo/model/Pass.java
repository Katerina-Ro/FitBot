package com.example.demo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
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
    private IntegerProperty numPass;
    private StringProperty phoneNumber;
    private ObjectProperty<LocalDate> dateStart;
    private ObjectProperty<LocalDate> dateEnd;
    private IntegerProperty visitLimit;
    private IntegerProperty freezeLimit;
    private ObjectProperty<LocalDate> dateStartFreeze;

    @Override
    public String toString() {
        return "Номер телефона владельца абонемента " + phoneNumber + '\'' +
                ", Дата начала действия абонемента " + dateStart + '\'' +
                ", До какого числа абонемент (включительно) " + dateEnd + '\'' +
                ", На какое количество занятий куплен абонемент " + visitLimit;
    }
}