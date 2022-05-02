package appStudentAttedanceRecord.db.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class DontPlanToComeToDay {
    private Long chatId;
    @NotBlank
    private String telephoneNum;
    private String surname;
    @NotBlank
    private String name;
    private String patronymic;
    private LocalDate currencyDate = LocalDate.now();

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия (может быть не указано) " + (surname != null ? surname : "") +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + (patronymic != null ? patronymic : "");
    }
}
