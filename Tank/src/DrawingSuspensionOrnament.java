import java.awt.*;

public class DrawingSuspensionOrnament implements IOrnamentForm {
    private CountWheels wheels;
    private Color SuspensionColor;
    public CountWheels getNumWheel() {
        return wheels;
    }

    public void setDigit(int number) {
        switch(number){
            case 2:
                wheels = CountWheels.Two;
                break;
            case 3:
                wheels = CountWheels.Three;
                break;
            case 4:
                wheels = CountWheels.Four;
                break;
            default:
                wheels = CountWheels.Two;
                break;
        }
    }

    public void DrawWheels(Graphics g, int _startPosX, int _startPosY) {
        g.setColor(Color.BLACK);
        g.fillOval(10 + _startPosX, 42 + _startPosY, 20, 20);
    }

    public void DrawSuspension(Graphics g, int _startPosX, int _startPosY) {
        SuspensionColor = Color.BLUE;
        g.setColor(SuspensionColor);
        g.fillRect(_startPosX + 27, _startPosY + 50, 10, 3);
    }

    public void Draw(Graphics g, int _startPosX, int _startPosY) {
        if (wheels == CountWheels.Two) {
            DrawWheels(g,_startPosX, _startPosY);
            DrawWheels(g,_startPosX + 100, _startPosY);

            DrawSuspension(g, _startPosX, _startPosY);
            DrawSuspension(g,_startPosX + 73, _startPosY);
        }

        if (wheels == CountWheels.Three) {
            DrawWheels(g,_startPosX, _startPosY);
            DrawWheels(g,_startPosX + 50, _startPosY);
            DrawWheels(g,_startPosX + 100, _startPosY);

            DrawSuspension(g, _startPosX, _startPosY);
            DrawSuspension(g,_startPosX + 24, _startPosY);
            DrawSuspension(g,_startPosX + 73, _startPosY);
        }

        if (wheels == CountWheels.Four) {
            DrawWheels(g,_startPosX, _startPosY);
            DrawWheels(g,_startPosX + 25, _startPosY);
            DrawWheels(g,_startPosX + 50, _startPosY);
            DrawWheels(g,_startPosX + 75, _startPosY);
            DrawWheels(g,_startPosX + 100, _startPosY);

            DrawSuspension(g, _startPosX, _startPosY);
            DrawSuspension(g,_startPosX + 24, _startPosY);
            DrawSuspension(g,_startPosX + 53, _startPosY);
            DrawSuspension(g,_startPosX + 73, _startPosY);
        }
    }
}