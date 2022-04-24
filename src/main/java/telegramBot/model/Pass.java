package telegramBot.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class Pass {

    private Integer numPass;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer visitLimit;
    private Integer freezeLimit;
    private LocalDate dateStartFreeze;

    @Override
    public String toString() {
        return "Номер телефона владельца абонемента " + phoneNumber + '\'' +
                ", Дата начала действия абонемента " + dateStart + '\'' +
                ", До какого числа абонемент (включительно) " + dateEnd + '\'' +
                ", На какое количество занятий куплен абонемент " + visitLimit;
    }
}