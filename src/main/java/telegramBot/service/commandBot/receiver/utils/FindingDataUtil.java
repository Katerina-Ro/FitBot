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

    public long getDelay(LocalTime currentTime) {
        long currentHourInMillis = getHourInMillis(currentTime.getHour());
        long currentMinutesInMillis = getMinutesInMillis(currentTime.getMinute());
        long currentSecondInMillis = getSecondInMillis(currentTime.getSecond());

        long currentTimeInMillis = currentHourInMillis + currentMinutesInMillis + currentSecondInMillis;

        long eventHourInMillis = getHourInMillis(eventTime.getHour());
        long eventMinutesInMillis = getMinutesInMillis(eventTime.getMinute());
        long eventSecondsInMillis = getSecondInMillis(eventTime.getSecond());

        long eventTimeInMillis = eventHourInMillis + eventMinutesInMillis + eventSecondsInMillis;

        long period = 86400000L;

        if (eventTime.compareTo(currentTime) < 0) {
            return period - (currentTimeInMillis - eventTimeInMillis);
        } else if (eventTime.compareTo(currentTime) > 0) {
            return eventTimeInMillis - currentTimeInMillis;
        } else {
            return period - (currentTimeInMillis - eventTimeInMillis);
        }
    }

    private long getHourInMillis(int hour) {
        return (long) hour * 60 * 60 * 1000;
    }

    private long getMinutesInMillis(int minutes) {
        return (long) minutes * 60 * 1000;
    }

    private long getSecondInMillis(int seconds) {
        return (long) seconds * 1000;
    }
}
