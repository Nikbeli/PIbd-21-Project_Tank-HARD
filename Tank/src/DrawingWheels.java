import java.awt.*;

public class DrawingWheels {
    private CountWheels _wheels;

    public void SetCountWheels(int Count){
        for (CountWheels temp: CountWheels.values())
            if (temp.getCountWheels() == Count) {
                _wheels=temp;
                return;
            }
    }

    public void DrawWheels(Graphics2D g,int _startPosX, int _startPosY) {
        g.setColor(Color.BLACK);
        switch (_wheels.getCountWheels()) {
            case 2:
                // Отрисовка 2 катков
                g.drawOval(_startPosX + 30, _startPosY + 42, 15, 15);
                g.fillOval(_startPosX + 30, _startPosY + 42, 15, 15);
                g.drawOval(_startPosX + 50, _startPosY + 45, 15, 15);
                g.fillOval(_startPosX + 50, _startPosY + 45, 15, 15);
                break;
            case 3:
                // Отрисовка 3 катков
                g.drawOval(_startPosX + 30, _startPosY + 42, 15, 15);
                g.fillOval(_startPosX + 30, _startPosY + 42, 15, 15);
                g.drawOval(_startPosX + 50, _startPosY + 45, 15, 15);
                g.fillOval(_startPosX + 50, _startPosY + 45, 15, 15);
                g.drawOval(_startPosX + 75, _startPosY + 45, 15, 15);
                g.fillOval(_startPosX + 75, _startPosY + 45, 15, 15);
                break;
            case 4:
                // Отрисовка 4 катков
                g.setColor(Color.BLACK);
                g.drawOval(_startPosX + 30, _startPosY + 42, 15, 15);
                g.fillOval(_startPosX + 30, _startPosY + 42, 15, 15);
                g.drawOval(_startPosX + 50, _startPosY + 45, 15, 15);
                g.fillOval(_startPosX + 50, _startPosY + 45, 15, 15);
                g.drawOval(_startPosX + 75, _startPosY + 45, 15, 15);
                g.fillOval(_startPosX + 75, _startPosY + 45, 15, 15);
                g.drawOval(_startPosX + 95, _startPosY + 42, 15, 15);
                g.fillOval(_startPosX + 95, _startPosY + 42, 15, 15);
                break;
        }
    }
}
