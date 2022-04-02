package studentAttedanceRecord;

import lombok.Getter;
import lombok.Setter;
import telegramBot.enteties.Pass;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
public class PlanToComeToDay {
    private Long chatId;
    @NotBlank
    private String telephoneNum;
    @NotBlank
    private String surname;
    @NotBlank
    private String name;
    private String patronymic;
    private Pass passList;
    @NotBlank
    private LocalDate currencyDate = LocalDate.now(ZoneId.of("GMT+03:00"));

    public PlanToComeToDay() {}

    public PlanToComeToDay(String telephoneNum, String name) {
        this.telephoneNum = telephoneNum;
        this.name = name;
    }

    public PlanToComeToDay(Long chatId, String telephoneNum, String surname, String name, String patronymic, Pass passList, LocalDate currencyDate) {
        this.chatId = chatId;
        this.telephoneNum = telephoneNum;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.passList = passList;
    }

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + (patronymic != null ? patronymic : "") + '\'' +
                ", Информация об абонементе " + passList;
    }
}
