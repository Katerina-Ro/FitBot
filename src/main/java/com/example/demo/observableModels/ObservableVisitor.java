package com.example.demo.observableModels;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ObservableVisitor {
    private Long chatId;
    private String telephoneNum;
    private String surname;
    private String name;
    private String patronymic;
    private String existActualPass;
    private String isFreezePass;
    private LocalDate dateStartFreeze;
}
