package service.commandBot.receiver.utils;

/**
 * Вспомогательный класс для проверки введенных пользователем строк на пустоту, null, цифры
 */
public class CheckingInputLinesUtil {
    /**
     * Проверка, введена ли строка и нет ли пробелов
     * @param line - строка, введенная пользователем
     * @return возвращаеся true, если введена строка и нет пробелов
     */
    public static boolean checkEmptyString(String line) {
        return line != null && !line.trim().isEmpty();
    }

    /**
     * Проверка, содержит ли строка только цифры
     * @param line - строка, введенная пользователем
     * @return возвращаеся true, если строка состоит только из цифр
     */
    public static boolean isNumbers(String line) {
        return line.trim().matches("[0-9]");
    }

    /**
     * Проверка длины введенного номера телефона
     * @param line строка, введенная пользователем
     * @return true, если количество введенных цифр соответствует количеству цифр номера телефона, включая 7
     * должно быть 11 цифр
     */
    public static boolean checkLengthLine(String line) {
        if (line.trim().length() == 11) {
            return true;
        }
        return false;
    }
}
