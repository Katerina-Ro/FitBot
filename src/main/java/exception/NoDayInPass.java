package exception;

public class NoDayInPass extends  Exception{
    private static final String message = "Нет занятий в абонементе";

    public NoDayInPass(String message) {
        super(message);
    }
}
