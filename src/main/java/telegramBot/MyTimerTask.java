package telegramBot;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private final static String botToken =
    private final static String chatId =
    private static final String IMAGE_WAVING_HAND = String.valueOf(Character.toChars(0x1F44B));
    private static final String START_MESSAGE = "Привет " + IMAGE_WAVING_HAND + " \nВы придете сегодня на занятие?";
    private static HttpURLConnection conn = null;

    @Override
    public void run() {
        String urlToken =
        ReplyKeyboard replyKeyboard = MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(
                Buttons.getKeyBoardButtonInfo());
        String urlParameters = "chat_id="+chatId+"&text="+START_MESSAGE+"&parse_mode="+ParseMode.HTML+"&reply_markup="+replyKeyboard;
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
