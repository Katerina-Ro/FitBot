package db.enteties;

import db.enteties.key.VisitsKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visits")
@IdClass(VisitsKey.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visits {
    @Id
    @Column(name="pass_id")
    private Integer pass;

    @Id
    @Column(name="date_visit")
    private LocalDate dateVisit;

    @Column(name="count_visit")
    private Integer countVisit;

    @Override
    public String toString() {
        return "Номер абонемента " + pass + '\'' +
                ", Дата посещения " + dateVisit + '\'' +
                ", Количество посещений " + countVisit;
    }
}
