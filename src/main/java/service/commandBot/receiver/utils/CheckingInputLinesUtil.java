package service.commandBot.receiver.utils;

/**
 * Вспомогательный класс для проверки введенных пользователем строк на пустоту, null, символы
 */
public class CheckingInputLinesUtil {
    /**
     * Проверяем, введена ли строка и нет ли пробелов
     * @param string - строка, введенная пользователем
     * @return возвращаеся true, если введена строка и нет пробелов
     */
    public static boolean checkEmptyString(String string) {
        return string !=null && !string.trim().isEmpty();
    }

    /**
     * Проверяем, не содержит ли строка символы
     * @param line - строка, введенная пользователем
     * @return возвращаеся true, если строка состоит из букв английского или русского алфавита
     */
    public static boolean isLetters(String line) {
        return line.matches("[а-яА-Я]+") ||
                line.matches("[a-zA-Z]+");
    }
}
