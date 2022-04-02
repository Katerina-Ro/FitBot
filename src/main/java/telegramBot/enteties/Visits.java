package telegramBot.enteties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telegramBot.enteties.key.VisitsKey;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
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
    @NotBlank
    private Integer pass;

    @Id
    @Column(name="date_visit")
    private Date dateVisit;

    @Column(name="count_visit")
    private Integer countVisit;

    @Override
    public String toString() {
        return "Номер абонемента " + pass + '\'' +
                ", Дата посещения " + dateVisit + '\'' +
                ", Количество посещений " + countVisit;
    }
}
