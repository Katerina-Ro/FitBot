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
    @Column(name="pass_id", unique = true)
    @NotBlank
    private Integer numPass;

    @Column(name="chat_id", unique = true)
    private Long chatId;

    @Column(name="date_start")
    @NotBlank
    private LocalDate dateStart;

    @Column(name="date_end")
    private LocalDate dateEnd;

    @Column(name="visit_limit")
    @NotBlank
    private Integer visitLimit;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Visits> visits;

    @Override
    public String toString() {
        return "Дата начала действия абонемента " + dateStart + '\'' +
                ", До какого числа абонемент (включительно) " + dateEnd + '\'' +
                ", На какое количество занятий куплен абонемент " + visitLimit + '\'' +
                ", Информация об абонементе " + visits;
    }
}
