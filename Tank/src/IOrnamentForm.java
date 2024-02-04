import java.awt.*;

public interface IOrnamentForm {
    public CountWheels getNumWheel();
    void setDigit(int number);
    void Draw(Graphics g, int _startPosX, int _startPosY);
}