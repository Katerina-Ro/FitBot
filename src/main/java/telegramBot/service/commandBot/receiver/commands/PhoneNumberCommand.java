package telegramBot.service.commandBot.receiver.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.Command;
import telegramBot.service.commandBot.receiver.utils.CheckingInputLinesUtil;
import telegramBot.service.commandBot.receiver.utils.FindingDataUtil;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.entetiesService.VisitorsService;

/**
 * Класс-Receiver для обработки запроса (есть ли введенный номер телефона в базе данных),
 * возвращает сообщение типа {@link SendMessage}
 */
@Service
public class PhoneNumberCommand implements Command {
    private static final String ASK_MESSAGE = "Вы придете сегодня на занятие?";
    private static final String NO_NUMBER_ERROR_MESSAGE = "Мы передадим информацию, что Вы сегодня придете. " +
            "Но Ваш номер телефона еще не занесен в базу студии. \n" +
            "Обратитесь к администратору";
    private static final String ERROR_MESSAGE_INPUT_NUMBER = "Неккоректно введен номер телефона. \n" +
            "Введите, пожалуйста, номер телефона в формате: 7 9991231234";

    private final VisitorsService visitorsService;

    @Autowired
    public PhoneNumberCommand(VisitorsService visitorsService) {
        this.visitorsService = visitorsService;
    }

    @Override
    public SendMessage execute(Update update) {
        long numberUser = update.getMessage().getChatId();
        String phoneNumber = update.getMessage().getText();
        if (CheckingInputLinesUtil.checkEmptyString(phoneNumber) && FindingDataUtil.isPhoneNumber(phoneNumber)) {
            if (visitorsService.havPhoneNumber(numberUser)) {
                return SendMessageUtils.sendMessage(update, ASK_MESSAGE, false)
                        .setReplyMarkup(MakerInlineKeyboardMarkup.get2x2InlineKeyboardMarkup(
                                Buttons.getKeyBoardYes(),
                                Buttons.getKeyBoardNo(),
                                Buttons.getKeyBoardButtonLessonLeft(),
                                Buttons.getKeyBoardBackToStart()));
            } else {
                return messageError(update);
            }
        }
        return messageErrorInputNumber(update);
    }

    private SendMessage messageError(Update update) {
        // вписать в список, что этот номер придет сегодня. Номера нет в базе данных
        return SendMessageUtils.sendMessage(update, NO_NUMBER_ERROR_MESSAGE, false)
                .setReplyMarkup(MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }

    private SendMessage messageErrorInputNumber(Update update) {
        return SendMessageUtils.sendMessage(update, ERROR_MESSAGE_INPUT_NUMBER,true);
    }
}
