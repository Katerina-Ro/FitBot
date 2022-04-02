package dto;

import javax.validation.constraints.NotBlank;

public class PlanToComeToDayButTheyNoInDB {
    private Long chatId;
    @NotBlank
    private String telephoneNum;

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum;
    }
}
