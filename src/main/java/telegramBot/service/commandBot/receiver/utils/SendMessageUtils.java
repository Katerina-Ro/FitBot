package telegramBot.service.commandBot.receiver.utils;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * Вспомогательный класс для формирования ответов боту типа SendMessage и EditMessageText
*/
public class SendMessageUtils {
    public static Long getChatIdUser(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        } else {
            return update.getMessage().getChatId();
        }
    }

    public static SendMessage sendMessage(Update update, String sentMessage, boolean isForceReply){
        String chatIdUser = String.valueOf(getChatIdUser(update));
        if (isForceReply) {
            ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
            forceReplyKeyboard.setSelective(true);
            Integer messageId = getMessageID(update);
            return SendMessage
                    .builder()
                    .chatId(chatIdUser)
                    .text(sentMessage)
                    //.enableHtml(true)
                    .parseMode(ParseMode.HTML)
                    .replyToMessageId(messageId)
                    .replyMarkup(forceReplyKeyboard)
                    .build();
        }
        return SendMessage
                .builder()
                .chatId(chatIdUser)
                .text(sentMessage)
                //.enableHtml(true)
                .build();
    }

    public static EditMessageText sendEditMessage(Update update, String sentMessage, InlineKeyboardMarkup
            inlineKeyboardMarkup){
        Integer messageId = getMessageID(update);
        return EditMessageText
                .builder()
                .text(sentMessage)
                .messageId(messageId)
                .chatId(String.valueOf(getChatIdUser(update)))
                .parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    private static Integer getMessageID(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getMessageId();
        } else {
            return update.getMessage().getMessageId();
        }
    }
}
