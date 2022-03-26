package service.commandBot.receiver.utils.keyboard;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.commandBot.COMMANDS;

@Getter
public class Buttons {
    private static final String BUTTON_GET_LESSONS_LEFT = "Сколько осталось занятий";
    private static final String BUTTON_INFO = "О чем канал?";
    private static final String BUTTON_YES_LABEL = "Да";
    private static final String BUTTON_NO_LABEL = "Нет";
    private static final String BUTTON_BACK_TO_START = "Назад, в главное меню";

    public static InlineKeyboardButton getKeyBoardBackToStart() {
        return MakerInlineKeyboardMarkup.getKeyBoard(BUTTON_BACK_TO_START,
                COMMANDS.BUTTON_BACK_TO_START.getCommand());
    }

    public static InlineKeyboardButton getKeyBoardYes(){
        return MakerInlineKeyboardMarkup.getKeyBoard(BUTTON_YES_LABEL, COMMANDS.YES.getCommand());
    }

    public static InlineKeyboardButton getKeyBoardNo(){
        return MakerInlineKeyboardMarkup.getKeyBoard(BUTTON_NO_LABEL, COMMANDS.NO.getCommand());
    }

    public static InlineKeyboardButton getKeyBoardButtonInfo(){
        return MakerInlineKeyboardMarkup.getKeyBoard(BUTTON_INFO, COMMANDS.INFO.getCommand());
    }

    public static InlineKeyboardButton getKeyBoardButtonLessonLeft() {
        return MakerInlineKeyboardMarkup.getKeyBoard(BUTTON_GET_LESSONS_LEFT, COMMANDS.LESSONS_LEFT.getCommand());
    }

    public static InlineKeyboardMarkup getKeyBoardStartMenu(){
        return MakerInlineKeyboardMarkup.get2x2InlineKeyboardMarkup(getKeyBoardButtonInfo(),
               getKeyBoardYes(), getKeyBoardNo(), getKeyBoardButtonLessonLeft());
    }
}
