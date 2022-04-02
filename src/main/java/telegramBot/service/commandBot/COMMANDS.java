package telegramBot.service.commandBot;

/**
 * Список кнопок и команд
 */
public enum COMMANDS {
    START("/start"),
    INFO("Для чего бот?"),
    YES("Да"),
    NO("Нет"),
    BUTTON_BACK_TO_START ("Назад, в главное меню"),
    LESSONS_LEFT("Осталось занятий");

    private final String command;

    COMMANDS (String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}