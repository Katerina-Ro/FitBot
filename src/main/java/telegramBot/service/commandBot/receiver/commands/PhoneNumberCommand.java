package telegramBot.service.commandBot.receiver.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import telegramBot.service.commandBot.Command;
import telegramBot.service.commandBot.receiver.utils.CheckingInputLinesUtil;
import telegramBot.service.commandBot.receiver.utils.FindingDataUtil;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.entetiesService.VisitorsService;

import javax.transaction.Transactional;

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
    @Transactional
    public SendMessage execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        System.out.println("Это phonenumber ");
        String phoneNumber = update.getMessage().getText();
        if (CheckingInputLinesUtil.checkEmptyString(phoneNumber) && FindingDataUtil.isPhoneNumber(phoneNumber)) {
            if (visitorsService.havPhoneNumber(numberUser)) {
                SendMessage sendMessage = SendMessageUtils.sendMessage(update, ASK_MESSAGE, false);
                sendMessage.setReplyMarkup(MakerInlineKeyboardMarkup.get2x2InlineKeyboardMarkup(
                        Buttons.getKeyBoardYes(),
                        Buttons.getKeyBoardNo(),
                        Buttons.getKeyBoardButtonLessonLeft(),
                        Buttons.getKeyBoardBackToStart()));
                return sendMessage;
            } else {
                return messageError(update);
            }
        }
        return messageErrorInputNumber(update);
    }

    private SendMessage messageError(Update update) {
        // вписать в список, что этот номер придет сегодня. Номера нет в базе данных
        SendMessage sendMessage = SendMessageUtils.sendMessage(update, NO_NUMBER_ERROR_MESSAGE, false);
                sendMessage.setReplyMarkup(MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(
                        Buttons.getKeyBoardBackToStart()));
        return sendMessage;
    }

    private SendMessage messageErrorInputNumber(Update update) {
        return SendMessageUtils.sendMessage(update, ERROR_MESSAGE_INPUT_NUMBER,true);
    }
}
