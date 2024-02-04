public class ObjectParameters {
    private int _x;
    private int _y;
    private int _width;
    private int _height;

    // Левая граница
    public int LeftBorder() {
        return _x;
    }

    // Верхняя граница
    public int TopBorder() {
        return _y;
    }

    // Правая граница
    public int RightBorder() {
        return _x + _width;
    }

    // Нижняя граница
    public int DownBorder() {
        return _y + _height;
    }

    // Середина объекта
    public int ObjectMiddleHorizontal() {
        return _x + _width / 2;
    }

    // Середина объекта
    public int ObjectMiddleVertical() {
        return _y + _height / 2;
    }

    // Конструктор
    public ObjectParameters(int x, int y, int width, int height) {
        _x = x;
        _y = y;
        _width = width;
        _height = height;
    }
}