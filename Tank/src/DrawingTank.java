import java.awt.*;

public class DrawingTank extends DrawingArmoVehicle {
    protected IOrnamentForm OrnamentsForm;
    private boolean OrnamentAdd;

    // Конструктор (Инициализация характеристик)
    public DrawingTank(int speed, double weight, Color bodyColor, int _numWheel, Color additionalColor, boolean bodyKit, boolean caterpillar, boolean tower, int width, int height, boolean ornamentAdd) {
        super(speed, weight, bodyColor, _numWheel, width, height);
        ArmoVehicle = new EntityTank(speed, weight, bodyColor, _numWheel, additionalColor, bodyKit, caterpillar, tower);
        _TankWidth = ((EntityTank)ArmoVehicle).BodyKit ? 169 : 83;

        this.OrnamentAdd = ornamentAdd;
    }

    // Ещё один конструктор
    public DrawingTank(EntityTank tank, IOrnamentForm _wheelDrawing, int width, int height) {
        super(tank, _wheelDrawing, width, height);
        if (height < _pictureHeight || width < _pictureWidth)
            return;
    }

    // Установка позиции
    public void SetPosition(int x, int y) {
        _startPosX = Math.min(x, _pictureWidth-_TankWidth);
        _startPosY = Math.min(y, _pictureHeight-_TankHeight);
    }

    private boolean setOrnamentAdd() {
        return OrnamentAdd;
    }

    // Прорисовка объекта
    public void DrawTransport(Graphics2D g) {
        if (ArmoVehicle == null) {
            return;
        }
        super.DrawTransport(g);

        g.setColor(((EntityTank) ArmoVehicle).AdditionalColor);
        if (((EntityTank) ArmoVehicle).BodyKit) {
            int[] xPointsBody = {_startPosX + 52, _startPosX + 52, _startPosX + 40, _startPosX + 15, _startPosX + 15, _startPosX + 60, _startPosX + 90, _startPosX + 120, _startPosX + 100, _startPosX + 95, _startPosX + 90};
            int[] yPointsBody = {_startPosY + 30, _startPosY + 27, _startPosY + 23, _startPosY + 18, _startPosY + 15, _startPosY + 11, _startPosY + 11, _startPosY + 20, _startPosY + 25, _startPosY + 27, _startPosY + 30};
            int nPointsBody = 11;

            g.drawPolygon(xPointsBody, yPointsBody, nPointsBody);
            g.fillPolygon(xPointsBody, yPointsBody, nPointsBody);
        }

        if (((EntityTank) ArmoVehicle).Caterpillar) {
            // Гусеница
            g.drawOval(_startPosX + 10, _startPosY + 30, 120, 30);
        }

        if (((EntityTank) ArmoVehicle).Tower) {
            // Орудие
            g.drawRect(_startPosX + 112, _startPosY + 17, 60, 4);
            g.fillRect(_startPosX + 112, _startPosY + 17, 60, 4);

            // Зенитное орудие
            int[] xPointsGun = {_startPosX + 45, _startPosX + 45, _startPosX + 41, _startPosX + 41, _startPosX + 42, _startPosX + 41, _startPosX + 44, _startPosX + 50, _startPosX + 52, _startPosX + 53, _startPosX + 58};
            int[] yPointsGun = {_startPosY + 12, _startPosY + 10, _startPosY + 8, _startPosY + 7, _startPosY + 5, _startPosY + 4, _startPosY + 3, _startPosY + 3, _startPosY + 5, _startPosY + 7, _startPosY + 10};
            int nPointsGun = 11;

            g.fillRect(_startPosX + 50, _startPosY + 5, 20, 2);
            g.drawPolygon(xPointsGun, yPointsGun, nPointsGun);
            g.fillPolygon(xPointsGun, yPointsGun, nPointsGun);
        }
    }
}