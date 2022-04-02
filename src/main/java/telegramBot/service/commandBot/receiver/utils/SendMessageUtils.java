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
    public static SendMessage sendMessage(Update update, String sentMessage, boolean isForceReply){
        String chatIdUser;
        if (update.hasCallbackQuery()) {
            chatIdUser = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        } else {
            chatIdUser = String.valueOf(update.getMessage().getChatId());
        }
        if (isForceReply) {
            ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
            forceReplyKeyboard.setSelective(true);
            Integer messageId;
            if (update.hasCallbackQuery()) {
                messageId = update.getCallbackQuery().getMessage().getMessageId();
            } else {
                messageId = update.getMessage().getMessageId();
            }
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
        return EditMessageText
                .builder()
                .text(sentMessage)
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .chatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()))
                .parseMode(ParseMode.HTML)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }
}
