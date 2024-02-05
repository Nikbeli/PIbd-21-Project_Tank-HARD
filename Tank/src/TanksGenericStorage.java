import com.sun.nio.sctp.InvalidStreamException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class TanksGenericStorage {
    // Разделитель для записи ключа и значения элемента словаря
    private static String _separatorForKeyValueWR = "|";
    private static String _separatorForKeyValue = "\\|";

    // Разделитель для записей коллекции данных в файл
    private String _separatorRecordsWR = ";";
    private String _separatorRecords = "\\;";

    // Разделитель для записи информации по объекту в файл
    private static String _separatorForObjectWR = ":";
    private static String _separatorForObject = "\\:";

    public void SaveDataSingle(String filename, String key) throws IOException {
        if(new File(filename).exists()) {
            new File(filename).delete();
        }

        StringBuilder data = new StringBuilder();
        data.append(key).append("\n");
        for (DrawingArmoVehicle elem: _tankStorages.get(key).getTanks(100)) {
            if(_tankStorages.get(key) == null)
                throw new InvalidPropertiesFormatException("Ключ не найден. Сохранение невозможно");

            if(_tankStorages.get(key) != null)
                data.append(elem != null ? ExtentionDrawingTank.GetDataForSave(elem, _separatorForObjectWR) + "\n" : "");
        }

        if(data.length() == 0)
            throw new InvalidStreamException("Нет данных для сохранения");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("TankStorageSingle" + System.lineSeparator() + data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadDataSingle(String filename) throws IOException {
        if(!new File(filename).exists()) {
            throw new FileNotFoundException("Файл не найден");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String s = reader.readLine();
            if(s == null || s.length() == 0)
                throw new NullPointerException("Нет данных для загрузки");

            if(!s.startsWith("TankStorageSingle"))
                throw new IllegalArgumentException("Неверный формат данных"); //если нет такой записи, то это не те данные

            String key = reader.readLine();
            if(key == null || key.length() == 0)
                throw new NullPointerException("Нет данных для загрузки");

            TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank> collections = new TanksGenericCollections<>(_pictureWidth, _pictureHeight);
            if (_tankStorages.containsKey(key)){
                collections = _tankStorages.get(key);
                collections.clear();
            }
            else
                collections = new TanksGenericCollections<>(_pictureWidth, _pictureHeight);

            List<String> tanksStrings = new ArrayList<String>();

            s = reader.readLine();
            while (s != null && s.length() != 0) {
                tanksStrings.add(s);
                s = reader.readLine();
            }

            Collections.reverse(tanksStrings);
            for (String elem : tanksStrings) {
                DrawingArmoVehicle vehicle = ExtentionDrawingTank.CreateDrawingTank(elem, _separatorForObject, _pictureWidth, _pictureHeight);
                if(vehicle == null || collections.Add(vehicle) == -1)
                    throw new NoSuchElementException("Объект не найден");
            }

            if(_tankStorages.containsKey(key))
                _tankStorages.remove(key);
            _tankStorages.put(key, collections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Сохранение информации по технике в хранилище в файл
    public void SaveData(String filename) throws IOException {
        if(new File(filename).exists()) {
            new File(filename).delete();
        }

        StringBuilder data = new StringBuilder();

        for (Map.Entry<String, TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank>> record : _tankStorages.entrySet()) {
            StringBuilder records = new StringBuilder();
            for (DrawingArmoVehicle elem : record.getValue().getTanks(100)) {
                records.append(elem != null ? ExtentionDrawingTank.GetDataForSave(elem, _separatorForObjectWR) + _separatorRecordsWR : "");
            }
            data.append(record.getKey()).append(_separatorForKeyValueWR).append(records).append("\n");
        }

        if (data.length() == 0)
            throw new InvalidStreamException("Нет данных для сохранения");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("TankStorage" + System.lineSeparator() + data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Загрузка информации по технике в хранилище из файла
    public void LoadData(String filename) throws IOException {
        if (!new File(filename).exists()) {
            throw new FileNotFoundException("Файл не найден");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String s = reader.readLine();
            if (s == null || s.length() == 0)
                throw new NullPointerException("Нет данных для загрузки");

            if (!s.startsWith("TankStorage"))
                throw new IllegalArgumentException("Неверный формат данных"); //если нет такой записи, то это не те данные

            _tankStorages.clear();

            s = reader.readLine();
            while (s != null && s.length() != 0) {
                String[] record = s.split(_separatorForKeyValue);
                s = reader.readLine();
                if (record.length != 2) {
                    continue;
                }
                TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank> collection = new TanksGenericCollections<>(_pictureWidth, _pictureHeight);
                String[] set = record[1].split(_separatorRecords);
                List<String> reversedSet = Arrays.asList(set);
                Collections.reverse(reversedSet);
                for (String elem : reversedSet) {
                    DrawingArmoVehicle vehicle = ExtentionDrawingTank.CreateDrawingTank(elem, _separatorForObject, _pictureWidth, _pictureHeight);
                    if (vehicle == null || collection.Add(vehicle) == -1)
                        throw new NoSuchElementException("Объект не найден");
                }
                _tankStorages.put(record[0], collection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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