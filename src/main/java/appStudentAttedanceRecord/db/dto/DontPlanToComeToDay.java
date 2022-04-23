package appStudentAttedanceRecord.db.dto;

import lombok.Getter;
import lombok.Setter;
import telegramBot.model.Pass;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
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
    private LocalDate currencyDate = LocalDate.now();

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + (patronymic != null ? patronymic : "") + '\'' +
                ", Информация об абонементе " + passList;
    }
}
