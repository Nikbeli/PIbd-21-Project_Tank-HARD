public class SetGeneric<T extends DrawingArmoVehicle> {
    // Массив объектов, которые храним
    private Object[] _places;

    // Количество объектов в массиве
    public int Count;

    // Конструктор
    public SetGeneric(int count) {
        _places = new Object[count];
        Count = _places.length;
    }

    // Добавление объекта в набор
    public int Insert(T tank) {
        int i = 0;
        for (; i < _places.length; i++) {
            if (_places[i] == null)
                break;
        }
        if (i == _places.length)
            return -1;
        for (; i > 0; i--) {
            _places[i] = _places[i - 1];
        }
        _places[i] = tank;
        return i;
    }

    // Добавление объекта в набор на конкретную позицию
    public boolean Insert(T tank, int position) {
        if (position < 0 || position >= _places.length)
            return false;
        for (; position < _places.length; position++) {
            if (_places[position] == null)
                break;
        }
        if (position == _places.length)
            return false;
        for (; position > 0; position--) {
            _places[position] = _places[position - 1];
        }
        _places[position] = tank;
        return true;
    }

    // Удаление объекта из набора с конкретной позиции
    public boolean Remove(int position) {
        if (position < 0 || position >= _places.length)
            return false;
        _places[position] = null;
        return true;
    }

    // Получение объекта из набора по позиции
    public T Get(int position) {
        if (position < 0 || position >= _places.length)
            return null;
        return (T)_places[position];
    }
}