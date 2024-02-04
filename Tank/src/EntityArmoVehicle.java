import java.awt.*;

public class EntityArmoVehicle {
    public int Speed;
    public double Weight;
    public Color BodyColor;
    public double Step;
    public int numWheel;

    public EntityArmoVehicle(int speed, double weight, Color bodyColor, int _numWheel) {
        Speed = speed;
        Weight = weight;
        BodyColor = bodyColor;
        numWheel = _numWheel;
        Step = (double) Speed * 200 / Weight;
    }
}