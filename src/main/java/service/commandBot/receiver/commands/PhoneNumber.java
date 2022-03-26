package service.commandBot.receiver.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.commandBot.CommandEditSendMessage;
import service.commandBot.receiver.utils.SendMessageUtils;
import service.commandBot.receiver.utils.keyboard.Buttons;
import service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import service.entetiesService.VisitorsService;

/**
 * Класс-Receiver для обработки запроса (есть ли введенный номер телефона в базе данных),
 * возвращает сообщение типа {@link CommandEditSendMessage}
 */
@Service
public class PhoneNumber implements CommandEditSendMessage {
    private static final String ASK_MESSAGE = "Вы придете сегодня на занятие?";
    private static final String NO_NUMBER_ERROR_MESSAGE = "Мы передадим информацию, что Вы сегодня придете. " +
            "Но Ваш номер телефона еще не занесен в базу студии. \n" +
            "Обратитесь к администратору";

    private final VisitorsService visitorsService;

    @Autowired
    public PhoneNumber(VisitorsService visitorsService) {
        this.visitorsService = visitorsService;
    }

    @Override
    public EditMessageText execute(Update update) {
        long numberUser = update.getMessage().getChatId();
        if (visitorsService.havPhoneNumber(numberUser)) {
            return SendMessageUtils.sendEditMessage(update, ASK_MESSAGE,
                    MakerInlineKeyboardMarkup.get2x2InlineKeyboardMarkup(Buttons.getKeyBoardYes(),
                            Buttons.getKeyBoardNo(),
                            Buttons.getKeyBoardButtonLessonLeft(),
                            Buttons.getKeyBoardBackToStart()));
        } else {
            return messageError(update);
        }
    }

    private EditMessageText messageError(Update update){
        // вписать в список, что этот номер придет сегодня. Номера нет в базе данных
        return SendMessageUtils.sendEditMessage(update, NO_NUMBER_ERROR_MESSAGE,
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}
