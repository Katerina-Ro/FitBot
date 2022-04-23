package telegramBot.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class Visits {

    @NotBlank
    private Integer pass;
    private LocalDate dateVisit;
    private Integer countVisit;

    @Override
    public String toString() {
        return "Номер абонемента " + pass + '\'' +
                ", Дата посещения " + dateVisit + '\'' +
                ", Количество посещений " + countVisit;
    }
}