package appStudentAttedanceRecord.db.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
public class DontPlanToComeToDay {
    private Long chatId;
    @NotBlank
    private String telephoneNum;
    private LocalDate currencyDate = LocalDate.now(ZoneId.of("GMT+03:00"));

    @Override
    public String toString() {
        return "Не придет сегодня: " +
                "Номер телефона: " + telephoneNum + '\'' +
                ", сегодня: " + currencyDate;
    }
}
