package telegramBot.service.commandBot.receiver.commands;

import appStudentAttedanceRecord.db.dto.PlanToComeToDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.enteties.Visitors;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.entetiesService.PassService;
import telegramBot.service.entetiesService.VisitorsService;

import java.util.Optional;

/**
 * Класс-Receiver команды {@link COMMANDS} {@link CommandEditSendMessage}
 */
@Service
public class YesCommand implements CommandEditSendMessage {
    private final VisitorsService visitorsService;
    private final PassService passService;
    @Autowired
    public YesCommand(VisitorsService visitorsService, PassService passService) {
        this.visitorsService = visitorsService;
        this.passService = passService;
    }

    @Override
    @Transactional
    public EditMessageText execute(Update update) {
        long numberUser = update.getMessage().getChatId();
        //Optional<Pass> pass = planToConeService.getActualPassByChatId(numberUser);
        // получить номер телефона, имя
        // вписать дто, что придет в мар, где ключ номер телефона
        // если номера телефона нет, то вписать в список лист, что придет
        /*

        if (pass.isPresent()) {
            planToComeToDay.setChatId(numberUser);
            boolean isSuccess = planToConeService.deductVisitIfYes(pass.get());
            if (!isSuccess) {
               // если неуспешно, то сформировать инфу для админа
            }

        } */
        PlanToComeToDay planToComeToDay = new PlanToComeToDay();
        Optional<Visitors> visitors = visitorsService.getVisitor(numberUser);
        if (visitors.isPresent()) {
            Visitors v = visitors.get();
            planToComeToDay.setChatId(v.getChatId());
            planToComeToDay.setName(v.getName());
            planToComeToDay.setTelephoneNum(v.getTelephoneNum());
            passService.getMapVisitorsToDay().put(v.getTelephoneNum(), planToComeToDay);
        }
        Integer classesLeft = 0;
        // classesLeft = passService.calculateClassesLeft(pass.get());

        return SendMessageUtils.sendEditMessage(update,
                String.format("Ждем Вас сегодня на занятиях. У Вас осталось %s занятий", classesLeft),
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}
