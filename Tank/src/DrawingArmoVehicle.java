import java.awt.*;
import java.util.Random;

public class DrawingArmoVehicle {
    protected IOrnamentForm OrnamentsForm;
    public EntityArmoVehicle ArmoVehicle;
    protected int _pictureWidth;
    protected int _pictureHeight;
    protected int _startPosX;
    protected int _startPosY;
    protected int _TankWidth = 160;
    protected int _TankHeight = 55;

    public DrawingArmoVehicle(int speed, double weight, Color bodyColor, int _numWheel, int width, int height) {
        _pictureWidth = width;
        _pictureHeight = height;
        if (_pictureHeight < _TankHeight || _pictureWidth < _TankWidth)
            return;
        ArmoVehicle = new EntityArmoVehicle(speed, weight, bodyColor, _numWheel);

        Random random = new Random();
        switch(random.nextInt(0, 3)) {
            case 0:
                OrnamentsForm = new DrawingWheelsCombination();
                break;
            case 1:
                OrnamentsForm = new DrawingStarOrnament();
                break;
            case 2:
                OrnamentsForm = new DrawingSuspensionOrnament();
                break;
            default:
                OrnamentsForm = new DrawingWheelsCombination();
                break;
        }
        OrnamentsForm.setDigit(_numWheel);
    }

    public void SetPosition(int x, int y) {
        _startPosX = Math.min(x, _pictureWidth - _TankWidth);
        _startPosY = Math.min(y, _pictureHeight - _TankHeight);
    }

    public int GetPosX () { return  _startPosX; }
    public int GetPosY () { return _startPosY; }
    public int GetWidth () { return  _TankWidth; }
    public int GetHeight () { return  _TankHeight; }

    public boolean CanMove(Direction direction) {
        if (ArmoVehicle == null) {
            return false;
        }
        switch (direction) {
            case Left:
                return _startPosX - ArmoVehicle.Step > 0;
            case Right:
                return _startPosX + _TankWidth + ArmoVehicle.Step < _pictureWidth;
            case Up:
                return _startPosY - ArmoVehicle.Step > 0;
            case Down:
                return _startPosY + _TankHeight + ArmoVehicle.Step < _pictureHeight;
            default:
                return false;
        }
    }

    public void MoveTransport(Direction direction) {
        if (!CanMove(direction) || ArmoVehicle == null) {
            return;
        }
        switch (direction) {
            //влево
            case Left:
                _startPosX -= (int)ArmoVehicle.Step;
                break;
            //вверх
            case Up:
                _startPosY -= (int)ArmoVehicle.Step;
                break;
            // вправо
            case Right:
                _startPosX += (int)ArmoVehicle.Step;
                break;
            //вниз
            case Down:
                _startPosY += (int)ArmoVehicle.Step;
                break;
        }
    }

    public void DrawTransport(Graphics2D g) {
        if (ArmoVehicle == null) {
            return;
        }

        // body
        g.setColor(ArmoVehicle.BodyColor);
        int[] xPoints = {_startPosX + 5, _startPosX + 140, _startPosX + 130,_startPosX + 12};
        int[] yPoints = {_startPosY + 30, _startPosY + 30, _startPosY + 42, _startPosY + 42};
        int nPoints = xPoints.length;
        g.drawPolygon(xPoints,yPoints,nPoints);
        g.fillPolygon(xPoints,yPoints,nPoints);

        //wheels
        OrnamentsForm.Draw(g, _startPosX, _startPosY);
    }
}