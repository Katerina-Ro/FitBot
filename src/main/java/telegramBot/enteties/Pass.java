package telegramBot.enteties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pass_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pass {
    @Id
    @Column(name="pass_id", nullable = false)//, unique = true)
    @NotBlank
    private Integer numPass;

    @Column(name="tel_num", nullable = false)//, unique = true)
    @NotBlank
    private String phoneNumber;

    @Column(name="date_start", nullable = false)
    @NotBlank
    private LocalDate dateStart;

    @Column(name="date_end", nullable = false)
    @NotBlank
    private LocalDate dateEnd;

    @Column(name="visit_limit", nullable = false)
    @NotBlank
    private Integer visitLimit;

    @Column(name="freeze_limit")
    private Integer freezeLimit;

    @Column(name="date_freeze")
    private LocalDate dateStartFreeze;

    @OneToMany //(cascade = CascadeType.ALL)
    private List<telegramBot.enteties.Visits> visits;

    @Override
    public String toString() {
        return "Дата начала действия абонемента " + dateStart + '\'' +
                ", До какого числа абонемент (включительно) " + dateEnd + '\'' +
                ", На какое количество занятий куплен абонемент " + visitLimit + '\'' +
                ", Информация об абонементе " + visits;
    }
}