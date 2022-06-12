package appStudentAttedanceRecord.db.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
public class ComeToDay {
    private Long chatId;
    @NotBlank
    private String telephoneNum;
    private String surname;
    @NotBlank
    private String name;
    private String patronymic;
    private LocalDate currencyDate = LocalDate.now(ZoneId.of("GMT+03:00"));

    public ComeToDay() {}

    public ComeToDay(String telephoneNum, String name) {
        this.telephoneNum = telephoneNum;
        this.name = name;
    }

    public ComeToDay(Long chatId, String telephoneNum, String surname, String name, String patronymic,
                     LocalDate currencyDate) {
        this.chatId = chatId;
        this.telephoneNum = telephoneNum;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + (patronymic != null ? patronymic : "") +
                ", Текущий день " + currencyDate;
    }
}
