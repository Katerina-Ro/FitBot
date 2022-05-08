package telegramBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.DBConfig;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
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

@Service
public class MyTimerTask extends TimerTask {
    private static final String botToken = "5132814541:AAFSh1Oj9ihGfi2Vt_SwhTZ9mUaxU0I86t8";
    private static final String IMAGE_WAVING_HAND = String.valueOf(Character.toChars(0x1F44B));
    private static final String START_MESSAGE = "Привет " + IMAGE_WAVING_HAND + " \nВы придете сегодня на занятие?";
    private static HttpURLConnection conn = null;
    private final IVisitorsRepository visitorsRepository = new VisitorsRepository(new NamedParameterJdbcTemplate(new DBConfig().dataSource()));

    @Override
    public void run() {
        String urlToken = "https://api.telegram.org/bot" + botToken + "/sendMessage";
        String chatId = null;
        List<Long> listChatId = visitorsRepository.findAllChatId();
        if (!listChatId.isEmpty()) {
            for (Long l: listChatId) {
                chatId = String.valueOf(l);
                ObjectMapper mapper = new ObjectMapper();
                StringWriter writer = new StringWriter();
                ReplyKeyboard replyKeyboard = Buttons.getKeyBoardStartMenu();
                try {
                    mapper.writeValue(writer, replyKeyboard);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String urlParameters = "chat_id="+chatId+"&text="+START_MESSAGE+"&parse_mode="+ ParseMode.HTML+"&reply_markup="+writer;
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
                    System.out.println(content);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    conn.disconnect();
                }
            }
        }
    }
}
