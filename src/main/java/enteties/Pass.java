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
    @Column(name="gym_pass")
    @NotBlank
    private Integer gymPass;

    @Column(name="date_start")
    @NotBlank
    private LocalDate dateStart;

    @Column(name="date_end")
    private LocalDate dateEnd;

    @Column(name="exercise_max")
    @NotBlank
    private Integer exerciseMax;

    @Column(name="exercise_left")
    private Integer exerciseLeft;

    @Override
    public String toString() {
        return "Дата начала действия абонемента " + dateStart + '\'' +
                ", До какого числа абонемент (включительно) " + dateEnd + '\'' +
                ", На какое количество занятий куплен абонемент " + exerciseMax + '\'' +
                ", Осталось занятий в абонементе на текущий момент " + exerciseLeft;
    }
}
