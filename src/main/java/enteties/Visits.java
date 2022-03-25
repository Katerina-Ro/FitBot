package enteties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "visits")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visits {
    @Column(name="gym_pass")
    @NotBlank
    private Pass gymPass;

    @Column(name="date_visit")
    private LocalDate dateVisit;

    @Column(name="count_visit")
    private Integer countVisit;

    @Override
    public String toString() {
        return "Номер абонемента " + gymPass + '\'' +
                ", Дата посещения " + dateVisit + '\'' +
                ", Количество посещений " + countVisit;
    }
}
