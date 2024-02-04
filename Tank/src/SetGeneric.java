import java.util.*;

public class SetGeneric <T extends Object> {
    // Массив объектов, которые храним
    private final ArrayList<T> _places;
    // Количество объектов в массиве
     public int Count() { return _places.size(); }
    // Максимальное количество объектов в списке
    private final int _maxCount;

    // Конструктор
    public SetGeneric(int count) {
        _maxCount = count;
        _places = new ArrayList<T>(count);
    }

    // Добавление объектов в набор
    public int Insert(T tank) {
        if(_places.size() >= _maxCount)
            return -1;
        _places.add(0,tank);
        return 0;
    }

    public boolean Insert(T tank, int position) {
        // Проверка позиции
        if (position < 0 || position > _places.size())
            return false;

        if (_places.size() >= _maxCount)
            return false;

        if (position == _places.size())
            _places.add(tank);
        else
            _places.add(position, tank);

        return true;
    }

    // Удаление объекта из набора с конкретной позиции
    public boolean Remove(int position) {
        // Проверка позиции
        if (position < 0 || position >= _places.size())
            return false;
        _places.remove(position);
        return true;
    }

    // Получение объекта из набора по позиции
    public T Get(int position) {
        // Проверка позиции
        if (position < 0 || position >= _places.size())
            return null;
        return _places.get(position);
    }

    // Проход по списку
    public Iterable<T> GetTanks(final Integer maxTanks) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private int currentIndex = 0;
                    private int count = 0;

                    @Override
                    public boolean hasNext() {
                        return currentIndex < _places.size() && (maxTanks == null || count < maxTanks);
                    }

                    @Override
                    public T next() {
                        if (hasNext()) {
                            count++;
                            return _places.get(currentIndex++);
                        }
                        throw new NoSuchElementException();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
    public void clear() { _places.clear(); }
}