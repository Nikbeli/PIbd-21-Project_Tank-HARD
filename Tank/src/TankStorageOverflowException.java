public class TankStorageOverflowException extends RuntimeException{
    public TankStorageOverflowException() { }
    public TankStorageOverflowException(String message){
        super(message);
    }
    public TankStorageOverflowException(String message, Throwable exception){
        super(message, exception);
    }
    protected TankStorageOverflowException(Throwable exception){
        super(exception);
    }
    public TankStorageOverflowException(int count){
        super("В наборе превышено допустимое количество: "+count);
    }
}