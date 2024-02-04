import java.awt.*;
import java.util.Random;

public class EntityTank {
    private int Speed;
    public int getSpeed() {
        return Speed;
    }
    private float Weight;
    public float getWeight() {
        return Weight;
    }
    private Color BodyColor;
    public Color getBodyColor() {
        return BodyColor;
    }
    public float Step;

    public void Init(int speed, float weight, Color bodyColor) {
        Random rnd = new Random();
        Speed = speed <= 0 ? rnd.nextInt(50) + 10 : speed;
        Weight = weight <= 0 ? rnd.nextInt(100) + 500 : weight;
        BodyColor = bodyColor;
        Step = Speed * 600 / (int)Weight;
    }
}

