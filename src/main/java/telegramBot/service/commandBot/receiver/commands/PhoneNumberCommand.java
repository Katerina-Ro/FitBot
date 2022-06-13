package telegramBot.service.commandBot.receiver.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.Command;
import telegramBot.service.commandBot.receiver.utils.FindingDataUtil;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;

/**
 * Класс-Receiver для обработки запроса (есть ли введенный номер телефона в базе данных),
 * возвращает сообщение типа {@link SendMessage}
 */
@Service
public class PhoneNumberCommand implements Command {
    private static final String ERROR_MESSAGE_INPUT_NUMBER = "Неккоректно введен номер телефона. \n" +
            "Введите, пожалуйста, номер телефона в формате: 79991231234, - начиная с '7'";
    private static final String MESSAGE_AFTER_PHONE = "Ваш телефон будет вписан в базу данных для отправки уведомлений " +
            "в ближайшее время. Если в течение 24 часов этого не произойдет, то обратитесь к администратору";

    @Override
    public SendMessage execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        String phoneNumber = update.getMessage().getText().trim();
        if (FindingDataUtil.isPhoneNumber(phoneNumber)) {
            // вписываем номер телефона и его chatId в промежуточную таблицу
            // здесь вписываем во временную таблицу данные
            return SendMessageUtils.sendMessage(update, MESSAGE_AFTER_PHONE, false);
        } else {
            return messageErrorInputNumber(update);
        }
    }

    private SendMessage messageErrorInputNumber(Update update) {
        return SendMessageUtils.sendMessage(update, ERROR_MESSAGE_INPUT_NUMBER,true);
    }
}