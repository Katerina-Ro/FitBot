package telegramBot.enteties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "visitors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visitors {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    @Column(name="chat_id", unique = true)
    private Long chatId;

    @Column(name="tel_num", unique = true)
    @NotBlank
    private String telephoneNum;

    @Column(name="surname")
    @NotBlank
    private String surname;

    @Column(name="name")
    @NotBlank
    private String name;

    @Column(name="patronumic")
    private String patronymic;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pass_id")
    private List<Pass> passList;

    @Override
    public String toString() {
        return "Номер телефона посетителя " + telephoneNum +
                ", Фамилия " + surname +
                ", Имя " + name +
                ", Отчество (может быть не указано) " + patronymic + '\'' +
                ", Информация об абонементе " + passList;
    }
}
