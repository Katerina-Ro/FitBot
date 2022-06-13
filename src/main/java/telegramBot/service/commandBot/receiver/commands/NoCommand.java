package telegramBot.service.commandBot.receiver.commands;

import supportTable.DontComeToDay;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.model.Visitors;
import telegramBot.repositories.IDontComeToDayRepository;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.modelService.VisitorsService;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Класс-Receiver команды {@link COMMANDS} {@link CommandEditSendMessage}
 */
@Log4j2
@Service
public class NoCommand implements CommandEditSendMessage {
    private static final String NO_MESSAGE = "Приходите в следующий раз. Хорошего дня";
    private final VisitorsService visitorsService;
    private final IDontComeToDayRepository dontComeToDayRepository;

    @Autowired
    public NoCommand(VisitorsService visitorsService, IDontComeToDayRepository dontComeToDayRepository) {
        this.visitorsService = visitorsService;
        this.dontComeToDayRepository = dontComeToDayRepository;
    }

    @Override
    public EditMessageText execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        DontComeToDay dontComeToDay = new DontComeToDay();
        Optional<Visitors> visitors = visitorsService.getVisitorByChatId(numberUser);
        if (visitors.isPresent()) {
            Visitors v = visitors.get();
            dontComeToDay.setChatId(v.getChatId());
            dontComeToDay.setSurname(v.getSurname());
            dontComeToDay.setName(v.getName());
            dontComeToDay.setPatronymic(v.getPatronymic());
            dontComeToDay.setTelephoneNum(v.getTelephoneNum());
            dontComeToDay.setCurrencyDate(LocalDate.now());
            // добавляем в список (map) тех, кто отметил, что не придет, и вписываем в промежуточную таблицу
            try {
                dontComeToDayRepository.createDontComeToDay(dontComeToDay);
            } catch (DataAccessException e) {
                log.error(String.format("Ошибка при записи в БД: %s", e));
                SendMessageUtils.sendEditMessage(update, "Нет о Вас информации в БД. Обратитесь к администратору",
                        MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
            }
        } else {
                SendMessageUtils.sendEditMessage(update,"Нет о Вас информации в БД. Обратитесь к администратору",
                        MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
        }
        return SendMessageUtils.sendEditMessage(update, NO_MESSAGE,
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}

