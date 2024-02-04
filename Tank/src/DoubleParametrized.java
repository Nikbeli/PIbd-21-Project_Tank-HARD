import java.util.ArrayList;
import java.util.Random;

public class DoubleParametrized<T extends EntityArmoVehicle, U extends IOrnamentForm> {
    ArrayList<T> Tanks;
    ArrayList<U> Wheels;

    public DoubleParametrized() {
        Tanks = new ArrayList<>();
        Wheels = new ArrayList<>();
    }

    public void Add(T tanks) {
        Tanks.add(tanks);
    }

    public void Add(U wheels) {
        Wheels.add(wheels);
    }

    public DrawingArmoVehicle GenerateTank(int pictureWidth, int pictureHeight) {
        Random rand = new Random();
        if(Tanks.isEmpty()) {
            return null;
        }

        T tanks = Tanks.get(rand.nextInt(Tanks.size()));
        U wheel = null;

        if(!Wheels.isEmpty()){
            wheel = Wheels.get(rand.nextInt(Wheels.size()));
        }

        DrawingArmoVehicle drawingArmoVehicle;
        if (tanks instanceof EntityTank) {
            drawingArmoVehicle = new DrawingTank((EntityTank) tanks, wheel, pictureWidth, pictureHeight);
        } else {
            drawingArmoVehicle = new DrawingArmoVehicle(tanks, wheel, pictureWidth, pictureHeight);
        }
        return drawingArmoVehicle;
    }
}