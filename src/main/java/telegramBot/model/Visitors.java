package telegramBot.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Visitors {

    private Long chatId;
    @NotBlank
    private String telephoneNum;
    private String surname;
    private String name;
    private String patronymic;

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + patronymic;
    }
}