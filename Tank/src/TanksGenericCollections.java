import java.awt.*;
import java.awt.image.BufferedImage;

public class TanksGenericCollections<T extends DrawingArmoVehicle, U extends IMoveableObject> {
    // Высота и Ширина окна прорисовки
    private int _pictureWidth;
    private int _pictureHeight;

    // Размер занимаемого объектом места (ширина и высота)
    private int _placeSizeWidth = 180;
    private int _placeSizeHeight = 90;

    // Набор объектов
    private SetGeneric<T> _collection;

    // Конструктор
    public TanksGenericCollections(int pictureWidth, int pictureHeight) {
        int width = pictureWidth / _placeSizeWidth;
        int height = pictureHeight / _placeSizeHeight;
        _pictureWidth = pictureWidth;
        _pictureHeight = pictureHeight;
        _collection = new SetGeneric<T>(width * height);
    }

    // Перегрузка оператора сложения
    public int Add(T obj) {
        if (obj == null) {
            return -1;
        }
        return _collection.Insert(obj);
    }

    // Перегрузка оператора вычитания
    public T remove(int pos) {
        T obj = _collection.Get(pos);
        if (obj != null) {
            _collection.Remove(pos);
        }
        return obj;
    }

    // Получение объекта IMoveableObject
    public U GetU(int pos)
    {
        return (U)_collection.Get(pos).GetMoveableObject();
    }

    // Вывод всего набора объектов
    public BufferedImage ShowTanks() {
        BufferedImage bitmap = new BufferedImage(_pictureWidth, _pictureHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bitmap.createGraphics();
        DrawBackground(g);
        DrawObjects(g);
        g.dispose();
        return bitmap;
    }

    // Метод отрисовки фона
    private void DrawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < _pictureWidth / _placeSizeWidth; i++) {
            for (int j = 0; j < _pictureHeight / _placeSizeHeight + 1; ++j) {
                // Линия разметки места
                g.drawLine(i * _placeSizeWidth, j * _placeSizeHeight, i * _placeSizeWidth + _placeSizeWidth / 2, j * _placeSizeHeight);
            }
            g.drawLine(i * _placeSizeWidth, 0, i * _placeSizeWidth, _pictureHeight / _placeSizeHeight * _placeSizeHeight);
        }
    }

    private void DrawObjects(Graphics g) {
        for (int i = 0; i < _collection.Count; i++) {
            T t = _collection.Get(i);
            if (t != null) {
                t.SetPosition((i % (_pictureWidth / _placeSizeWidth)) * _placeSizeWidth, (i / (_pictureWidth / _placeSizeWidth)) * _placeSizeHeight);
                if (t instanceof DrawingTank)
                    ((DrawingTank) t).DrawTransport((Graphics2D)g);
                else
                    t.DrawTransport((Graphics2D)g);
            }
        }
    }
}