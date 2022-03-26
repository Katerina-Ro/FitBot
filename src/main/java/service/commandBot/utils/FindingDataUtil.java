package service.commandBot.utils;

/**
 * Вспомогательный класс для получения данных из сообщения типа String
 */
public class FindingDataUtil {
    /**
     * Получаем подстроку из сообщения от начала доп первого пробела
     * @param incomingMessage - пришедшее от бота сообщение
     * @return подстрока типа String
     */
    public static String findLineByIncomingMessage(String incomingMessage){
        int indexSpace = incomingMessage.indexOf(" ");
        if (indexSpace == -1){
            return "нет команды";
        }
        return incomingMessage.substring(0,(indexSpace));
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
