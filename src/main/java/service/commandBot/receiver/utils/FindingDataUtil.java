package service.commandBot.receiver.utils;

/**
 * Вспомогательный класс для получения данных из сообщения типа String
 */
public class FindingDataUtil {
    /**
     * Проверка, первый символ - это цифра 7
     * @param incomingMessage - пришедшее от бота сообщение
     * @return подстрока типа String
     */
    public static boolean firstSevenByIncomingMessage(String incomingMessage){
        String firstSymbol = incomingMessage.trim().substring(0, 1);
        return "7".equals(firstSymbol);
    }

    public static boolean isPhoneNumber(String incomingMessage) {
        if (firstSevenByIncomingMessage(incomingMessage)) {
            if (CheckingInputLinesUtil.checkLengthLine(incomingMessage)) {
                return CheckingInputLinesUtil.isNumbers(incomingMessage);
            };
           return false;
        }
        return false;
    }

    /**
     * Проверяем, содержит ли сообщение "\n"
     * @param incomingMessage - пришедшее от бота сообщение
     * @return true, если содержит
     */
    public static boolean containLineBreak(String incomingMessage){
        boolean containLineBreak = false;
        if (incomingMessage.contains("\n")){
            containLineBreak = true;
        }
        return containLineBreak;
    }

    /**
     * Получаем значение строку без знаков "\n"
     * @param incomingMessage - пришедшее от бота сообщение
     * @return подстрока от начала до "\n"
     */
    public static String findLineByIncomingMessageByN(String incomingMessage){
        return incomingMessage.substring(0,(incomingMessage.indexOf("\n")));
    }

    /**
     * Получаем значение типа int из сообщения (подстрока от пробела)
     * @param incomingMessage - пришедшее от бота сообщение
     * @return значение типа int
     */
    public static int findIdByIncomingMessage(String incomingMessage){
        return Integer.parseInt(incomingMessage.substring((incomingMessage.indexOf(" "))+1));
    }
}
