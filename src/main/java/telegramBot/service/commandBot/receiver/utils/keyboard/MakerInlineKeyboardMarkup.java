package telegramBot.service.commandBot.receiver.utils.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Вспомогательный класс для формирования клавиатуры типа InlineKeyboardMarkup
 */
public class MakerInlineKeyboardMarkup {
    /** Получаем клавиатуру на 1 ряд 1 строку в одну кнопку
     * @param keyboardButtonRow - 1 ряд
     * @return - клавиатура типа: |кнопка|
     */
    public static InlineKeyboardMarkup get1InlineKeyboardMarkup(InlineKeyboardButton keyboardButtonRow) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(makeListInlineKeyboardButton(keyboardButtonRow));
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    /** Получаем клавиатуру на 2 ряда, 1 строку
     * @param keyboardButtonRow1 - 1 ряд
     * @param keyboardButtonRow2 - 2 ряд
     * @return - клавиатуру типа: |кнопка| - |кнопка|
     */
    public static InlineKeyboardMarkup get2x1InlineKeyboardMarkup(InlineKeyboardButton keyboardButtonRow1,
                                                                  InlineKeyboardButton keyboardButtonRow2) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(makeListInlineKeyboardButton(keyboardButtonRow1, keyboardButtonRow2));
        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    /**
     * Получаем клавиатуру на 2 строки в 2 ряда на 4 кнопки
     * @param keyboardButtonRow1 - 1 (верхняя) кнопка 1 ряд
     * @param keyboardButtonRow2 - 1 (верхняя) кнопка 2 ряд
     * @return - клавиатура типа: |кнопка| - |кнопка|
     *                            |кнопка| - |кнопка|
     */
    public static InlineKeyboardMarkup get2x2InlineKeyboardMarkup(InlineKeyboardButton keyboardButtonRow1,
                                                                  InlineKeyboardButton keyboardButtonRow2,
                                                                  InlineKeyboardButton keyboardButtonRow3,
                                                                  InlineKeyboardButton keyboardButtonRow4) {
        return getInlineKeyboardMarkup(keyboardButtonRow1, keyboardButtonRow2,
                makeListInlineKeyboardButton(keyboardButtonRow3, keyboardButtonRow4));
    }

    /**
     * Клавиатура в 2 ряда 2 строки на 3 кнопки
     * @param keyboardButtonRow1 - кнопка 1 тип 1 ряд
     * @param keyboardButtonRow2 - кнопка 2 тип 2 ряд
     * @param keyboardButtonRow3 - кнопка 3 тип 1 ряд
     * @return - клавиатура типа: |кнопка| - |кнопка|
     *                            |кнопка|
     */
    public static InlineKeyboardMarkup get2x2x3InlineKeyboardMarkup(InlineKeyboardButton keyboardButtonRow1,
                                                                    InlineKeyboardButton keyboardButtonRow2,
                                                                    InlineKeyboardButton keyboardButtonRow3){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(makeListInlineKeyboardButton(keyboardButtonRow1, keyboardButtonRow2));
        keyboardButtons.add(makeListInlineKeyboardButton(keyboardButtonRow3));
        inlineKeyboardMarkup.setKeyboard(keyboardButtons);
        return inlineKeyboardMarkup;
    }

    /**
     * Получаем список кнопок в 2 ряда и меняющееся количество строк (количество строк зависит от размера
     * второго параметра)
     * @param keyboardButtonRow1 - ряд кнопок, от которого зависит количество строк
     * @param keyboardButtonRow2 - ряд кнопок, количество которых зависит от первого параметра
     * @return возвращается клавиатура типа: |кнопка| - |кнопка|
     *                                       |кнопка| - |кнопка| и т.д.
     */
    public static InlineKeyboardMarkup get2x2InlineKeyboardMarkup(InlineKeyboardButton keyboardButtonRow2,
                                                                  List<InlineKeyboardButton>
                                                                          keyboardButtonRow1){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtons2List = new ArrayList<>();
        for(int i = 0; i <= keyboardButtonRow1.size(); i++) {
            keyboardButtons2List.add(keyboardButtonRow2);
        }
        keyboardButtons.add(keyboardButtons2List);
        keyboardButtons.add(keyboardButtonRow1);
        inlineKeyboardMarkup.setKeyboard(keyboardButtons);
        return inlineKeyboardMarkup;
    }

    /**
     * Получаем список кнопок в 2 ряда и меняющееся количество строк (количество строк зависит от размера
     * второго параметра)
     * @param  listObjects - список объектов, который необходимо перевсти в кнопки
     * @return возвращается клавиатура типа: |кнопка 1| - |кнопка 2 связана с кнопкой 1|
     *                                       |кнопка 3| - |кнопка 4 связана с кнопкой 3| и т.д.
     */
    public static InlineKeyboardMarkup get2xNInlineKeyboardMarkup(List<Object> listObjects){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        for(Object g: listObjects){
            List<InlineKeyboardButton> keyboardButtonList = new ArrayList<>(); // 1 строка
            keyboardButtonList.add(getKeyBoard(g.toString(),g.toString()));
            keyboardButtonList.add(getKeyBoard(g.toString(),g.toString()));
            keyboardButtons.add(keyboardButtonList);
        }
        inlineKeyboardMarkup.setKeyboard(keyboardButtons);
        return inlineKeyboardMarkup;
    }

    /**
     * Получаем список кнопок в 2 ряда и меняющееся количество строк (количество строк зависит от размера
     * второго параметра) + последняя строка - это дополнительная кнопка
     * @param  listObjects - список объектов, который необходимо перевсти в кнопки
     * @param inlineKeyboardButtonList - название последней кнопки (например, 'Назад') в формате
     *                                 List<InlineKeyboardButton>
     * @return возвращается клавиатура типа: |кнопка 1| - |кнопка 2 связана с кнопкой 1|
     *                                       |кнопка 3| - |кнопка 4 связана с кнопкой 3|
     *                                       и т.д.
     *                                       |кнопка|
     */
    public static InlineKeyboardMarkup get2xNx1InlineKeyboardMarkup(List<Object> listObjects,
                                                                    List<InlineKeyboardButton>
                                                                            inlineKeyboardButtonList){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonList = new ArrayList<>(); // 1 строка
        for(Object g: listObjects){
            keyboardButtonList.add(getKeyBoard(g.toString(),g.toString()));
            keyboardButtonList.add(getKeyBoard(g.toString(),g.toString()));
            keyboardButtons.add(keyboardButtonList);
        }
        keyboardButtons.add(inlineKeyboardButtonList);
        inlineKeyboardMarkup.setKeyboard(keyboardButtons);
        return inlineKeyboardMarkup;
    }

    /**
     * Клавиатура на кнопки 3х типов
     * @param keyboardButtonRow1 - список кнопкок 1 типа 1 ряд
     * @param keyboardButtonRow2 - список кнопок 2 типа 2 ряд
     * @param keyboardButtonRow3 - кнопка 3 тип 1 ряд
     * @return - клавиатура в 2 ряда, кнопки 3х типов
     */
    public static InlineKeyboardMarkup get2x2x3InlineKeyboardMarkup(Collection<InlineKeyboardButton>
                                                                            keyboardButtonRow1,
                                                                    Collection<InlineKeyboardButton> keyboardButtonRow2,
                                                                    InlineKeyboardButton keyboardButtonRow3) {
        InlineKeyboardMarkup inlineKeyboardMarkup5 = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        for(InlineKeyboardButton buttonRow1: keyboardButtonRow1){
            for(InlineKeyboardButton buttonRow2: keyboardButtonRow2) {
                keyboardButtons.add(makeListInlineKeyboardButton(buttonRow1, buttonRow2));
                keyboardButtons.add(makeListInlineKeyboardButton(keyboardButtonRow3));
            }
        }
        inlineKeyboardMarkup5.setKeyboard(keyboardButtons);
        return inlineKeyboardMarkup5;
    }

    /** Получаем клавиатуру типа ReplyKeyboardMarkup на 1 кнопку
     * @param nameButton - название кнопки
     * @return клавиатура типа ReplyKeyboardMarkup
     */
    public static ReplyKeyboardMarkup makeListReplyKeyboardButtonAfterInlineButton(String nameButton){
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(nameButton);
        keyboard.add(row);
        return makeListReplyKeyboardButton().setKeyboard(keyboard);
    }

    /**
     * Вспомогательный метод для формирвоания кнопки типа ReplyKeyboardButton
     * @return кнопку типа ReplyKeyboardButton
     */
    public static ReplyKeyboardMarkup makeListReplyKeyboardButton() {
        return new ReplyKeyboardMarkup()
                /* selective Этот параметр нужен, чтобы показывать клавиатуру только определённым пользователям.
                Цели: 1) пользователи, которые были @упомянуты в поле text объекта Message; 2) если сообщения бота
                является ответом (содержит поле reply_to_message_id), авторы этого сообщения.
                Пример: Пользователь отправляет запрос на смену языка бота. Бот отправляет клавиатуру со списком
                языков, видимую только этому пользователю.
                 */
                .setSelective(true)
                /* one_time_keyboard Указывает клиенту скрыть клавиатуру после использования (после нажатия на кнопку).
               Её по-прежнему можно будет открыть через иконку в поле ввода сообщения. По умолчанию False.
               */
                .setOneTimeKeyboard(true)
                /* resize_keyboard Указывает клиенту подогнать высоту клавиатуры под количество кнопок (сделать её
               меньше, если кнопок мало). По умолчанию False, то есть клавиатура всегда такого же размера, как и
               стандартная клавиатура устройства.
               */
                .setResizeKeyboard(true);
    }

    /**
     * Получаем кнопку типа InlineKeyboardButton
     * @param text - текст кнопки
     * @param callbackData - callback (например, команда в строковом значении из перечисления)
     * @return - кнопку типа InlineKeyboardButton
     */
    public static InlineKeyboardButton getKeyBoard(String text, String callbackData){
        InlineKeyboardButton inlineKeyboardButtonDeleteWish = new InlineKeyboardButton();
        inlineKeyboardButtonDeleteWish.setText(text);
        inlineKeyboardButtonDeleteWish.setCallbackData(callbackData);
        return inlineKeyboardButtonDeleteWish;
    }

    /**
     * Получаем список List кнопок типа InlineKeyboardButton с одним элементом
     * @param keyboardButton - кнопка 1
     * @return - список кнопок с 1 элементом
     */
    public static List<InlineKeyboardButton> makeListInlineKeyboardButton (InlineKeyboardButton keyboardButton){
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        inlineKeyboardButtons.add(keyboardButton);
        return  inlineKeyboardButtons;
    }

    /**
     * Получаем список List кнопок типа InlineKeyboardButton с 2 элементами
     * @param keyboardButton1 - кнопка 1
     * @param keyboardButton2 - кнопка 2
     * @return список кнопок с 2 элементами
     */
    private static List<InlineKeyboardButton> makeListInlineKeyboardButton (InlineKeyboardButton keyboardButton1,
                                                                            InlineKeyboardButton keyboardButton2){
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        inlineKeyboardButtons.add(keyboardButton1);
        inlineKeyboardButtons.add(keyboardButton2);
        return  inlineKeyboardButtons;
    }

    /** Получаем клавиатуру типа InlineKeyboardMarkup из кнопок разного типа: InlineKeyboardButton и
     * List<InlineKeyboardButton>
     * @param keyboardButtonRow1 - InlineKeyboardButton кнопка 1
     * @param keyboardButtonRow2 - InlineKeyboardButton кнопка 2
     * @return - клавиатуру InlineKeyboardMarkup
     */
    private static InlineKeyboardMarkup getInlineKeyboardMarkup(InlineKeyboardButton keyboardButtonRow1,
                                                                InlineKeyboardButton keyboardButtonRow2,
                                                                List<InlineKeyboardButton>
                                                                        inlineKeyboardButtons) {
        InlineKeyboardMarkup inlineKeyboardMarkup1 = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(makeListInlineKeyboardButton(keyboardButtonRow1, keyboardButtonRow2));
        keyboardButtons.add(inlineKeyboardButtons);
        inlineKeyboardMarkup1.setKeyboard(keyboardButtons);
        return inlineKeyboardMarkup1;
    }
}
