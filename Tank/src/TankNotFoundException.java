public class TankNotFoundException extends RuntimeException {
    public TankNotFoundException() { }
    public TankNotFoundException(String message) {
        super(message);
    }
    public TankNotFoundException(String message, Throwable exception) {
        super(message, exception);
    }
    public TankNotFoundException(Throwable exception) {
        super(exception);
    }
    protected TankNotFoundException(int pos) {
        super("Не найден объект по позиции "+pos);
    }
}