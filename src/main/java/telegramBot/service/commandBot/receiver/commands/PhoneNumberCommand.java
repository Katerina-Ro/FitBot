package telegramBot.service.commandBot.receiver.commands;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import supportTable.ComeToDay;
import telegramBot.service.commandBot.Command;
import telegramBot.service.commandBot.receiver.utils.FindingDataUtil;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.modelService.ComeToDayService;

/**
 * Класс-Receiver для обработки запроса (есть ли введенный номер телефона в базе данных),
 * возвращает сообщение типа {@link SendMessage}
 */
@Log4j2
@Service
public class PhoneNumberCommand implements Command {
    private static final String ERROR_MESSAGE_INPUT_NUMBER = "Неккоректно введен номер телефона. \n" +
            "Введите, пожалуйста, номер телефона в формате: 79991231234, - начиная с '7'";
    private static final String MESSAGE_AFTER_PHONE = "Ваш телефон будет вписан в базу данных для отправки уведомлений " +
            "в ближайшее время. Если в течение 24 часов этого не произойдет, то обратитесь к администратору";
    private final ComeToDayService comeToDayService;

    @Autowired
    public PhoneNumberCommand(ComeToDayService comeToDayService) {
        this.comeToDayService = comeToDayService;
    }

    @Override
    public SendMessage execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        String phoneNumber = update.getMessage().getText().trim();
        if (FindingDataUtil.isPhoneNumber(phoneNumber)) {
            ComeToDay comeToDay = new ComeToDay();
            comeToDay.setChatId(numberUser);
            comeToDay.setTelephoneNum(phoneNumber);

            if (!comeToDayService.existThisComeToday(comeToDay)) {
                try {
                    comeToDayService.createComeToDay(comeToDay);
                } catch (DataAccessException e) {
                    log.error(String.format("Ошибка при записи в БД: %s", e));
                    SendMessageUtils.sendEditMessage(update, "Нет о Вас информации в БД. Обратитесь к администратору",
                            MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
                }
            }
            return SendMessageUtils.sendMessage(update, MESSAGE_AFTER_PHONE, false);
        } else {
            return messageErrorInputNumber(update);
        }
    }

    private SendMessage messageErrorInputNumber(Update update) {
        return SendMessageUtils.sendMessage(update, ERROR_MESSAGE_INPUT_NUMBER,true);
    }
}