package telegramBot.service.commandBot.receiver.commands;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import supportTable.ComeToDay;
import telegramBot.model.Visitors;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.modelService.ComeToDayService;
import telegramBot.service.modelService.VisitorsService;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Класс-Receiver команды {@link COMMANDS} {@link CommandEditSendMessage}
 */
@Log4j2
@Service
public class YesCommand implements CommandEditSendMessage {
    private static final String NO_HAVE_CLASSES_IN_PASS = "Не осталось занятий в абонементе либо нет информации " +
            "об абонементе в базе данных";
    private final VisitorsService visitorsService;
    private final ComeToDayService comeToDayService;

    @Autowired
    public YesCommand(VisitorsService visitorsService, ComeToDayService comeToDayService) {
        this.visitorsService = visitorsService;
        this.comeToDayService = comeToDayService;
    }

    @Override
    public EditMessageText execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        ComeToDay comeToDay = new ComeToDay();
        Optional<Visitors> visitors = visitorsService.getVisitorByChatId(numberUser);
        if (visitors.isPresent()) {
            Visitors v = visitors.get();
            comeToDay.setChatId(v.getChatId());
            comeToDay.setSurname(v.getSurname());
            comeToDay.setName(v.getName());
            comeToDay.setPatronymic(v.getPatronymic());
            comeToDay.setTelephoneNum(v.getTelephoneNum());
            comeToDay.setCurrencyDate(LocalDate.now());
            // добавляем в список (map) тех, кто придет, и вписываем в промежуточную таблицу
            if (!comeToDayService.existThisComeToday(comeToDay)) {
                try {
                    comeToDayService.createComeToDay(comeToDay);
                } catch (DataAccessException e) {
                    log.error(String.format("Ошибка при записи в БД: %s", e));
                    SendMessageUtils.sendEditMessage(update, "Нет о Вас информации в БД. Обратитесь к администратору",
                            MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
                }
            }
        } else {
            SendMessageUtils.sendEditMessage(update,"Нет о Вас информации в БД. Обратитесь к администратору",
                    MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
        }
        return SendMessageUtils.sendEditMessage(update,
                "Ждем Вас на занятиях. Хорошего дня",
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}
