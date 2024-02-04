import java.awt.*;

public class DrawingStarOrnament implements IOrnamentForm {
    private CountWheels wheels;
    private Color StarColor;
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
            case 5:
                wheels = CountWheels.Five;
                break;
            default:
                wheels = CountWheels.Two;
                break;
        }
    }

    public void CaterpillarStar(Graphics g, int _startPosX, int _startPosY) {
        StarColor = Color.RED;
        g.setColor(StarColor);
        int xPontsStar[] = {_startPosX + 15, _startPosX + 18, _startPosX + 21, _startPosX + 18, _startPosX + 19, _startPosX + 16, _startPosX + 12, _startPosX + 12, _startPosX + 11, _startPosX + 15, _startPosX + 16};
        int yPontsStar[] = {_startPosY + 35, _startPosY + 38, _startPosY + 38, _startPosY + 42, _startPosY + 45, _startPosY + 42, _startPosY + 45, _startPosY + 41, _startPosY + 38, _startPosY + 38, _startPosY + 35};
        g.drawPolygon(xPontsStar, yPontsStar, xPontsStar.length);
        g.fillPolygon(xPontsStar, yPontsStar, xPontsStar.length);
    }

    public void DrawWheels(Graphics g, int _startPosX, int _startPosY){
        g.setColor(Color.BLACK);
        g.fillOval(10 + _startPosX, 42 + _startPosY, 20, 20);
    }
    public void Draw(Graphics g, int _startPosX, int _startPosY) {
        DrawWheels(g,_startPosX, _startPosY);
        CaterpillarStar(g,_startPosX + 5, _startPosY + 12);

        if (wheels == CountWheels.Two){
            DrawWheels(g,_startPosX, _startPosY);
            DrawWheels(g,_startPosX + 100, _startPosY);

            CaterpillarStar(g,_startPosX + 5, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 105, _startPosY + 12);
        }

        if (wheels == CountWheels.Three) {
            DrawWheels(g,_startPosX, _startPosY);
            DrawWheels(g,_startPosX + 50, _startPosY);
            DrawWheels(g,_startPosX + 100, _startPosY);

            CaterpillarStar(g,_startPosX + 5, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 55, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 105, _startPosY + 12);
        }

        if (wheels == CountWheels.Four) {
            DrawWheels(g,_startPosX, _startPosY);
            DrawWheels(g,_startPosX + 25, _startPosY);
            DrawWheels(g,_startPosX + 50, _startPosY);
            DrawWheels(g,_startPosX + 100, _startPosY);

            CaterpillarStar(g,_startPosX + 5, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 30, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 55, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 105, _startPosY + 12);
        }

        if(wheels == CountWheels.Five) {
            DrawWheels(g,_startPosX, _startPosY);
            DrawWheels(g,_startPosX + 25, _startPosY);
            DrawWheels(g,_startPosX + 50, _startPosY);
            DrawWheels(g,_startPosX + 75, _startPosY);
            DrawWheels(g,_startPosX + 100, _startPosY);

            CaterpillarStar(g,_startPosX + 5, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 30, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 55, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 80, _startPosY + 12);
            CaterpillarStar(g,_startPosX + 105, _startPosY + 12);
        }
    }
}