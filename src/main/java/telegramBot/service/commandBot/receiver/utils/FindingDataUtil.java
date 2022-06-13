package telegramBot.service.commandBot.receiver.utils;

import java.time.LocalTime;

/**
 * Вспомогательный класс для получения данных из сообщения типа String
 */
public class FindingDataUtil {
    private final static LocalTime eventTime = LocalTime.of(9, 0, 0);

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
            }
            return false;
        }
        return false;
    }

    public static boolean isEventTime(LocalTime currentTime) {
        return eventTime.compareTo(currentTime) == 0;
    }

    public long getDelay(LocalTime currentTime) {
        long currentTimeNano = currentTime.getNano();
        long eventTimeNano = eventTime.getNano();
        long difference = currentTimeNano - eventTimeNano;

        long period = 86400000;
        System.out.println("currentTime = " + currentTime + " currentTimeNano " + currentTimeNano
                + " eventTime " +eventTime + " eventTimeNano ="
        + eventTimeNano + " difference " + difference + " period - difference = " + (period - difference));
        return period - difference;
    }
}
