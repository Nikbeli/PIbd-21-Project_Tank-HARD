import java.awt.*;

public class DrawingTank {
    public EntityTank Tank;
    public EntityTank getTank() {
        return Tank;
    }
    public DrawingWheels Wheels;
    private int _startPosX;
    private int _startPosY;
    private Integer _pictureWidth = null;
    private Integer _pictureHeight = null;
    private final int _TankWidth = 175;
    private final int _TankHeight = 100;

    public void Init(int speed, float weight, Color bodyColor) {
        Tank = new EntityTank();
        Tank.Init(speed, weight, bodyColor);
        Wheels = new DrawingWheels();
        Wheels.SetCountWheels((int)(2 + Math.random() + Math.random()*2));
    }

    public void SetPosition(int x, int y, int width, int height) {
        if (x >= 0 && x+_TankWidth <= width && y >= 0 && y+_TankHeight <= height) {
            _startPosX = x;
            _startPosY = y;
            _pictureWidth = width;
            _pictureHeight = height;
        }
    }

    public void MoveTransport(Direction direction) {
        if (_pictureWidth == null || _pictureHeight == null) {
            return;
        }
        switch (direction) {
            case Right:
                if (_startPosX + _TankWidth + Tank.Step < _pictureWidth) {
                    _startPosX += Tank.Step;
                }
                break;

            case Left:
                if (_startPosX- Tank.Step >= 0) {
                    _startPosX -= Tank.Step;
                }
                break;

            case Up:
                if (_startPosY - Tank.Step >= 0) {
                    _startPosY -= Tank.Step;
                }
                break;

            case Down:
                if (_startPosY + _TankHeight + Tank.Step < _pictureHeight) {
                    _startPosY += Tank.Step;
                }
                break;
        }
    }

    public void DrawTransport(Graphics g) {
        if (_startPosX < 0 || _startPosY < 0 || _pictureHeight== null || _pictureWidth== null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        //Гусеница
        Color Gray = new Color(128, 128, 128);
        g2.setColor(Gray);
        g2.drawOval(_startPosX + 10, _startPosY + 30, 120, 30);

        // Ведущие колёса танка (в размере чуть меньше)
        g2.setColor(Color.BLACK);
        g2.drawOval(_startPosX + 113, _startPosY + 41, 11, 11);
        g2.fillOval(_startPosX + 113, _startPosY + 41, 11, 11);
        g2.drawOval(_startPosX + 13, _startPosY + 40, 11, 11);
        g2.fillOval(_startPosX + 13, _startPosY + 40, 11, 11);
        Wheels.DrawWheels(g2, _startPosX, _startPosY);

        // Корпус танка
        g2.setColor(Color.DARK_GRAY);
        int[] xPoints = {_startPosX + 5, _startPosX + 140, _startPosX + 130,_startPosX + 12};
        int[] yPoints = {_startPosY + 30, _startPosY + 30, _startPosY + 42, _startPosY + 42};
        int nPoints = 4;
        g2.drawPolygon(xPoints,yPoints,nPoints);
        g2.fillPolygon(xPoints,yPoints,nPoints);

        // Башня
        int[] xPointsBody = {_startPosX + 52, _startPosX + 52, _startPosX + 40, _startPosX + 15,_startPosX + 15, _startPosX + 60,_startPosX + 90,_startPosX + 120,_startPosX + 100,_startPosX + 95, _startPosX + 90};
        int[] yPointsBody = {_startPosY + 30, _startPosY + 27, _startPosY + 23, _startPosY + 18,_startPosY + 15, _startPosY + 11,_startPosY + 11,_startPosY + 20,_startPosY + 25,_startPosY + 27,_startPosY + 30};
        int nPointsBody = 11;

        g2.drawPolygon(xPointsBody,yPointsBody,nPointsBody);
        g2.fillPolygon(xPointsBody,yPointsBody,nPointsBody);

        // Орудие
        g2.drawRect(_startPosX + 112, _startPosY+17, 50, 5);
        g2.fillRect(_startPosX + 112, _startPosY+17, 50, 5);

        // Зенитное орудие
        int[] xPointsGun = {_startPosX + 45, _startPosX + 45, _startPosX + 41, _startPosX + 41, _startPosX + 42, _startPosX + 41, _startPosX + 44,_startPosX + 50 ,_startPosX + 52,_startPosX + 53, _startPosX + 58};
        int[] yPointsGun = {_startPosY + 12, _startPosY + 10, _startPosY + 8, _startPosY + 7, _startPosY + 5, _startPosY + 4,_startPosY + 3,_startPosY + 3,_startPosY + 5,_startPosY + 7,_startPosY + 10};
        int nPointsGun = 11;

        g2.fillRect(_startPosX + 50, _startPosY+5, 20, 2);
        g2.drawPolygon(xPointsGun,yPointsGun,nPointsGun);
        g2.fillPolygon(xPointsGun,yPointsGun,nPointsGun);
    }

    public void ChangeBorders(int width,int height) {
        _pictureWidth = width;
        _pictureHeight = height;
        if (_pictureWidth<=_TankWidth||_pictureHeight<=_TankHeight) {
            _pictureWidth = null;
            _pictureHeight = null;
            return;
        }
        if (_startPosX + _TankWidth > _pictureWidth) {
            _startPosX = _pictureWidth - _TankWidth;
        }
        if (_startPosY + _TankHeight > _pictureHeight) {
            _startPosY = _pictureHeight - _TankHeight;
        }
    }
}
