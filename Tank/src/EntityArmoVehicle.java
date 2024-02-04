import java.awt.*;

public class EntityArmoVehicle {
    public int Speed;
    public double Weight;
    public double getWeight() {
        return Weight;
    }
    public Color BodyColor;
    public void setBodyColor(Color color) { BodyColor = color; }
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