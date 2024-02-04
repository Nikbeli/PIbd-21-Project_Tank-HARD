import java.awt.*;

public class EntityTank extends EntityArmoVehicle {
    public Color AdditionalColor;
    public boolean BodyKit;
    public boolean Caterpillar;
    public boolean Tower;

    public EntityTank(int speed, double weight, Color bodyColor, int _numWheel, Color additionalColor, boolean bodyKit, boolean caterpillar, boolean tower) {
        super(speed, weight, bodyColor, _numWheel);
        AdditionalColor = additionalColor;
        BodyKit = bodyKit;
        Caterpillar = caterpillar;
        Tower = tower;
    }
}