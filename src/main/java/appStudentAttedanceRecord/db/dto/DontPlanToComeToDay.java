package telegramBot.dto;

import telegramBot.enteties.Pass;

import javax.validation.constraints.NotBlank;

public class DontPlanToComeToDay {
    private Long chatId;
    @NotBlank
    private String telephoneNum;
    @NotBlank
    private String surname;
    @NotBlank
    private String name;
    private String patronymic;
    private Pass passList;

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + (patronymic != null ? patronymic : "") + '\'' +
                ", Информация об абонементе " + passList;
    }
}
