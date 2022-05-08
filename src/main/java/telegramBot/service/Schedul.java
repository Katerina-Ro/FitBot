package telegramBot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

@Service
@PropertySource(value = "classpath:botsecret.properties")
@Getter
public class Schedul {

    private final MyTimerTask myTimerTask;

    @Setter
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    public Schedul(MyTimerTask myTimerTask) {
        this.myTimerTask = myTimerTask;
    }

    public void getSchedul() {
        LocalDateTime localDate = LocalDateTime.of(LocalDate.of(2022, 5, 4),
                LocalTime.of(8, 18, 0));
        Date dateFirstLaunch = Date.from(localDate.atZone(ZoneId.of("GMT+03:00")).toInstant());

        //MyTimerTask myTimerTask = new MyTimerTask(visitorsRepository);
        Timer timer = new Timer();

        // периодичность 24 часа
        long period = 86400000;
        timer.scheduleAtFixedRate(myTimerTask, dateFirstLaunch, period);
    }
}
