package enteties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "pass")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pass {
    @Id
    @Column(name="pass_id")
    @NotBlank
    private Integer numPass;

    @Column(name="date_start")
    @NotBlank
    private LocalDate dateStart;

    @Column(name="date_end")
    private LocalDate dateEnd;

    @Column(name="visit_limit")
    @NotBlank
    private Integer visitLimit;

    //@Column(name="exercise_left")
    //private Integer exerciseLeft;

    private Visits visits;

    @Override
    public String toString() {
        return "Дата начала действия абонемента " + dateStart + '\'' +
                ", До какого числа абонемент (включительно) " + dateEnd + '\'' +
                ", На какое количество занятий куплен абонемент " + visitLimit + '\'' +
                ", Информация об абонементе " + visits;
    }
}
