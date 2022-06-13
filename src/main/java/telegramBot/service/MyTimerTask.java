package telegramBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.DBConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import telegramBot.repositories.IVisitorsRepository;
import telegramBot.repositories.impl.VisitorsRepository;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.TimerTask;

@Log4j2
public class MyTimerTask extends TimerTask {
    private static final String botToken =
    private static final String IMAGE_WAVING_HAND = String.valueOf(Character.toChars(0x1F44B));
    private static final String START_MESSAGE = "Привет " + IMAGE_WAVING_HAND + " \nВы придете сегодня на занятие?";
    private static HttpURLConnection conn = null;
    private final IVisitorsRepository visitorsRepository = new VisitorsRepository(new NamedParameterJdbcTemplate(new DBConfig().dataSource()));

    @Override
    public void run() {
        String urlToken =
        String chatId;
        List<Long> listChatId = visitorsRepository.findAllChatId();
        if (listChatId != null && !listChatId.isEmpty()) {
            for (Long l: listChatId) {
                chatId = String.valueOf(l);
                ObjectMapper mapper = new ObjectMapper();
                StringWriter writer = new StringWriter();
                ReplyKeyboard replyKeyboard = Buttons.getKeyBoardStartMenu();
                try {
                    mapper.writeValue(writer, replyKeyboard);
                } catch (IOException e) {
                    log.error(String.format("Не удалось вписать ответ при формировании уведомления: %s", e));
                }

                String urlParameters = "chat_id=" + chatId + "&text=" + START_MESSAGE + "&parse_mode=" +
                        ParseMode.HTML + "&reply_markup=" + writer;
                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

                try {
                    URL url = new URL(urlToken);
                    conn = (HttpURLConnection) url.openConnection();

                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("User-Agent", "Java upread.ru client");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                        wr.write(postData);
                    }
                    StringBuilder content;

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                        String line;
                        content = new StringBuilder();

                        while ((line = br.readLine()) != null) {
                            content.append(line);
                            content.append(System.lineSeparator());
                        }
                    }
                } catch (IOException e) {
                    log.error(String.format("Ошибка при чтении или записи уведомления: %s", e));
                } finally {
                    conn.disconnect();
                }
            }
        }
    }
}
