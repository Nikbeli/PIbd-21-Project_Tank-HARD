import java.util.*;
import java.util.stream.Collectors;

public class TanksGenericStorage {
    // Словарь (как хранилище)
    HashMap<String, TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank>> _tankStorages;

    // Возвращение списка названий наборов
    public List<String> Keys() {
        return _tankStorages.keySet().stream().collect(Collectors.toList());
    }
    // Ширина и высота отрисовки
    private int _pictureWidth;
    private int _pictureHeight;

    // Конструктор
    public TanksGenericStorage(int pictureWidth, int pictureHeight) {
        _tankStorages = new HashMap<String, TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank>>();
        _pictureWidth = pictureWidth;
        _pictureHeight = pictureHeight;
    }

    // Добавление набора
    public void AddSet(String name) {
        if (_tankStorages.containsKey(name))
            return;
        _tankStorages.put(name, new TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank>(_pictureWidth, _pictureHeight));
    }

    // Удаление набора
    public void DelSet(String name) {
        if (!_tankStorages.containsKey(name))
            return;
        _tankStorages.remove(name);
    }

    // Доступ к набору
    public TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank> get(String ind) {
        if (_tankStorages.containsKey(ind))
            return _tankStorages.get(ind);
        return null;
    }

    public DrawingObjectTank get(String ind1, int ind2){
        if (!_tankStorages.containsKey(ind1))
            return null;
        return _tankStorages.get(ind1).GetU(ind2);
    }
}