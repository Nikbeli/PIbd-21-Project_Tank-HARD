import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Vector;

public class ExtentionDrawingTank {
    // Создание объекта из строки
    public static DrawingArmoVehicle CreateDrawingTank(String info, String separatorForObject, int width, int height) {
        String[] strs = info.split(separatorForObject);
        if (strs.length == 5)
        {
            String[] colorValues = strs[2].split(",");

            DrawingArmoVehicle drawingVehicle = new DrawingArmoVehicle(Integer.parseInt(strs[0]), (double)Integer.parseInt(strs[1]),
                    new Color(Integer.parseInt(colorValues[0].replaceAll("\\D", "")),
                    Integer.parseInt(colorValues[1].replaceAll("\\D", "")),
                    Integer.parseInt(colorValues[2].replaceAll("\\D", ""))),
                    Integer.parseInt(strs[3]),
                    width, height
            );

            try{
                drawingVehicle.OrnamentsForm = (IOrnamentForm) Class.forName(strs[4]).getDeclaredConstructor().newInstance();
                drawingVehicle.OrnamentsForm.setDigit(drawingVehicle.ArmoVehicle.numWheel);
            } catch (Exception e) {
                return null;
            }
            return drawingVehicle;
        }

        if(strs.length == 9) {
            String[] colorValues = strs[2].split(",");
            String[] colorValues2 = strs[5].split(",");
            DrawingTank drawingTank = new DrawingTank(
                    Integer.parseInt(strs[0]),
                    (float)Integer.parseInt(strs[1]),
                    new Color(
                    Integer.parseInt(colorValues[0].replaceAll("\\D", "")),
                    Integer.parseInt(colorValues[1].replaceAll("\\D", "")),
                    Integer.parseInt(colorValues[2].replaceAll("\\D", ""))
            ),
            Integer.parseInt(strs[3]),
                    new Color(
                            Integer.parseInt(colorValues2[0].replaceAll("\\D", "")),
                            Integer.parseInt(colorValues2[1].replaceAll("\\D", "")),
                            Integer.parseInt(colorValues2[2].replaceAll("\\D", ""))
                    ),
                    strs[6].equals("true"),
                    strs[7].equals("true"),
                    strs[8].equals("true"),
                    width, height
            );
            try {
                drawingTank.OrnamentsForm = (IOrnamentForm)Class.forName(strs[4]).getDeclaredConstructor().newInstance();
                drawingTank.OrnamentsForm.setDigit(drawingTank.ArmoVehicle.numWheel);
            } catch (Exception e) {
                return null;
            }
            return drawingTank;
        }
        return null;
    }

    // Получение данных для сохранения в файл
    public static String GetDataForSave(DrawingArmoVehicle drawingArmoVehicle, String separatorForObject) {
        EntityArmoVehicle vehicle = drawingArmoVehicle.ArmoVehicle;
        if (vehicle == null) {
            return null;
        }

        String str = "" + vehicle.Speed + separatorForObject + (int)vehicle.Weight + separatorForObject + vehicle.BodyColor + separatorForObject + vehicle.numWheel + separatorForObject + drawingArmoVehicle.OrnamentsForm.getClass().getName();
        if (!(vehicle instanceof EntityTank)) {
            return str;
        }
        return str+separatorForObject+((EntityTank)vehicle).AdditionalColor.toString()+separatorForObject+((EntityTank)vehicle).BodyKit+separatorForObject+((EntityTank)vehicle).Caterpillar+separatorForObject+((EntityTank)vehicle).Tower;
    }
}