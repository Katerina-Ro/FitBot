package supportTable;

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
    //@NotBlank
    private String name;
    private String patronymic;
    private LocalDate currencyDate = LocalDate.now(ZoneId.of("GMT+03:00"));

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия (может быть не указано) " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + (patronymic != null ? patronymic : "") +
                ", Текущий день " + currencyDate;
    }
}
