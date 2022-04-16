package telegramBot.service.commandBot.receiver.commands;

import appStudentAttedanceRecord.db.dto.PlanToComeToDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.enteties.Pass;
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
    private static final String NO_HAVE_CLASSES_IN_PASS = "Не осталось занятий в абонементе либо нет информации " +
            "об абонементе в базе данных";
    private final VisitorsService visitorsService;
    private final PassService passService;

    @Autowired
    public YesCommand(VisitorsService visitorsService, PassService passService) {
        this.visitorsService = visitorsService;
        this.passService = passService;
    }

    @Override
    //@Transactional
    public EditMessageText execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        PlanToComeToDay planToComeToDay = new PlanToComeToDay();
        Optional<Visitors> visitors = visitorsService.getVisitorByChatId(numberUser);
        Optional<Pass> pass;
        Optional<String> classesLeft;
        if (visitors.isPresent()) {
            Visitors v = visitors.get();
            planToComeToDay.setChatId(v.getChatId());
            planToComeToDay.setName(v.getName());
            planToComeToDay.setTelephoneNum(v.getTelephoneNum());
            // добавляем в список (map) тех, кто придет, и отправляем администратору
            passService.getMapVisitorsToDay().put(v.getTelephoneNum(), planToComeToDay);

            /*
            pass = passService.getPassByPassNumber(v.);
            classesLeft = pass.map(value -> String.format("Ждем Вас сегодня на занятиях. " + "У Вас осталось %s занятий",
                    passService.calculateClassesLeft(value))).or(() -> Optional.of("Нет информации о Вашем абонементе. " +
                    "Обратитесь к администратору")); */
        } else {
            classesLeft = Optional.of("Нет о Вас информации. Обратитесь к администратору");
        }
        return SendMessageUtils.sendEditMessage(update,
                "Ждем Вас на занятиях. Хорошего дня",
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}
