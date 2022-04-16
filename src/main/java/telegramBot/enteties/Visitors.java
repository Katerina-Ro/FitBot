package telegramBot.enteties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "pass_schema.visitors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visitors {

    @Column(name="chat_id") //, unique = true)
    private Long chatId;

    @Id
    @Column(name="tel_num", nullable = false) //, unique = true)
    @NotBlank
    private String telephoneNum;

    @Column(name="surname")
    private String surname;

    @Column(name="name")
    private String name;

    @Column(name="patronumic")
    private String patronymic;

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinColumn(name = "visitors_tel_num")
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