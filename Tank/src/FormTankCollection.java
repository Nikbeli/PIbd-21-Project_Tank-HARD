import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.Logger;

public class FormTankCollection {
    private class Canvas extends JComponent {
        public Canvas() { }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (jListStorage.getSelectedIndex() == -1) {
                return;
            }
            var obj = _storage.get(jListStorage.getSelectedValue());
            if (obj == null) {
                return;
            }
            if (obj.ShowTanks() != null) {
                g.drawImage(obj.ShowTanks(), 0, 0, this);
            }
            super.repaint();
        }
    }

    Canvas canv;
    static int pictureBoxWidth = 700;
    static int pictureBoxHeight = 480;
    private TanksGenericStorage _storage;

    public void Draw() {
        canv.repaint();
    }

    private Queue<DrawingArmoVehicle> Queue;
    private JList<String> jListStorage;
    private DefaultListModel<String> listModel;

    private void ReloadObjects() {
        int index = jListStorage.getSelectedIndex();
        listModel.clear();
        for (String key : _storage.Keys()) {
            listModel.addElement(key);
        }
        if (listModel.size() > 0 && (index == -1 || index >= listModel.size())) {
            jListStorage.setSelectedIndex(0);
        } else if (listModel.size() > 0 && index > -1 && index < listModel.size()) {
            jListStorage.setSelectedIndex(index);
        }
    }

    // Логгер
    private Logger logger;

    FormTankCollection(Logger logger) {
        System.setProperty("log4j.configutationFile", "src//log4j2.xml");
        this.logger = logger;

        listModel = new DefaultListModel<String>();
        jListStorage = new JList<String>(listModel);
        canv = new Canvas();
        JFrame Frame = new JFrame("TankCollecltion");
        _storage = new TanksGenericStorage(pictureBoxWidth, pictureBoxHeight);

        JButton ButtonAddVehicle = new JButton("Добавить технику");
        ButtonAddVehicle.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (jListStorage.getSelectedIndex() == -1) {
                            logger.warn("Добавление объекта в несуществующий набор");
                            return;
                        }
                        var obj = _storage.get(jListStorage.getSelectedValue());
                        if (obj == null) {
                            return;
                        }

                        FormTankConfig form = new FormTankConfig();
                        form.buttonAdd.addActionListener(
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        try {
                                            if (obj != null && obj.Add(form._vehicle) != -1) {
                                                logger.info("Добавлен новый объект");
                                                JOptionPane.showMessageDialog(null, "Объект добавлен", "Информация", JOptionPane.INFORMATION_MESSAGE);
                                                Draw();
                                            }
                                        } catch (TankStorageOverflowException ex) {
                                            logger.warn("Коллекция переполнена: "+ex.getMessage());
                                            JOptionPane.showMessageDialog(null, ex.getMessage());
                                            JOptionPane.showMessageDialog(null, "Не удалось добавить объект", "Информация", JOptionPane.INFORMATION_MESSAGE);

                                        } catch (Exception ex){
                                            logger.fatal("Неизвестная ошибка: "+ex.getMessage());
                                            JOptionPane.showMessageDialog(null, "Ошибка. Неизвестная ошибка: "+ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                                        }
                                        form.Frame.dispose();
                                    }
                                }
                        );
                    }
                }
        );

        Queue = new LinkedList<DrawingArmoVehicle>();

        JTextField TextBoxNumber = new JTextField();
        JButton ButtonRemoveTank = new JButton("Удалить технику");
        ButtonRemoveTank.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (jListStorage.getSelectedIndex() == -1) {
                            logger.warn("Удаление объекта из несуществующего набора");
                            return;
                        }
                        var obj = _storage.get(jListStorage.getSelectedValue());
                        if (obj == null) {
                            return;
                        }

                        if (JOptionPane.showConfirmDialog(null, "Удалить объект?", "Удаление", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                            return;
                        }
                        for (char it : TextBoxNumber.getText().toCharArray())
                            if (it < '0' || it > '9') {
                                JOptionPane.showMessageDialog(null, "Не удалось удалить объект", "Информация", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                        if (TextBoxNumber.getText().length() == 0) {
                            JOptionPane.showMessageDialog(null, "Не удалось удалить объект", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        int pos = Integer.parseInt(TextBoxNumber.getText());
                        try {
                            var removed = obj.remove(pos);
                            if (removed != null) {
                                JOptionPane.showMessageDialog(null, "Объект удален", "Информация", JOptionPane.INFORMATION_MESSAGE);
                                Queue.add(removed);
                                Draw();
                                logger.info("Объект с позиции " + pos + "удалён");
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Не удалось удалить объект", "Информация", JOptionPane.INFORMATION_MESSAGE);
                                logger.info("Не удалось удалить объект с позиции " + pos);
                            }
                        } catch (TankNotFoundException ex) {
                            logger.warn("Не удалось удалить объект с позиции"+ pos);
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        } catch (Exception ex) {
                            logger.fatal("Неизвестная ошибка: " +ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Неизвестная ошибка: "+ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        );

        JButton buttonGetRemoved = new JButton("Извлечь");
        buttonGetRemoved.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (Queue.size() == 0) {
                            JOptionPane.showMessageDialog(null, "Нет удалённых", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            logger.info("Нет удалённых объектов");
                            return;
                        }
                        FormTank form = new FormTank();
                        form._drawingVehicle = Queue.peek();
                        Queue.remove();
                        form.Draw();
                    }
                }
        );

        JButton ButtonRefreshCollection = new JButton("Обновить коллекцию");
        ButtonRefreshCollection.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        Draw();
                    }
                }
        );

        JButton toFormTankGenerate = new JButton("Генерировать технику");
        toFormTankGenerate.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        FormTankGenerate formTankGenerate = new FormTankGenerate();
                    }
                }
        );

        JTextField textBoxSetName = new JTextField();
        JButton buttonAddSet = new JButton("Добавить набор");
        buttonAddSet.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (textBoxSetName.getText().length() == 0) {
                            JOptionPane.showMessageDialog(null, "Не все данные заполнены", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            logger.warn("Не все данные заполнены");
                            return;
                        }
                        _storage.AddSet(textBoxSetName.getText());
                        ReloadObjects();
                        logger.info("Набор добавлен: "+textBoxSetName.getText());
                    }
                }
        );

        jListStorage.addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        Draw();
                    }
                }
        );

        JButton buttonRemoveSet = new JButton("Удалить набор");
        buttonRemoveSet.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (jListStorage.getSelectedIndex() == -1) {
                            logger.warn("Удаление несуществующего набора");
                            return;
                        }
                        if (JOptionPane.showConfirmDialog(null, "Удалить объект " + jListStorage.getSelectedValue() + "?", "Удаление", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                            return;
                        }
                        _storage.DelSet(jListStorage.getSelectedValue());
                        logger.info("Гараж "+jListStorage.getSelectedValue()+" Удалён");
                        ReloadObjects();
                    }
                }
        );

        // Создаём панель меню
        JMenuBar menuBar = new JMenuBar();

        // Создаём пункты меню
        JMenu fileMenu = new JMenu("File");

        // Создаём пункты меню
        JMenuItem openItem = new JMenuItem("Открыть");
        openItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();

                        fileChooser.setDialogTitle("Выберите файл для загрузки данных");

                        // Установка фильтра для файлов с определённым расширеним (например, txt)
                        fileChooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt"));

                        fileChooser.setDialogTitle("Выберите файл для загрузки данных");
                        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();

                            try {
                                _storage.LoadData(fileChooser.getSelectedFile().getAbsolutePath());
                                ReloadObjects();
                                logger.info("Загрузка всех объектов из файла: " + fileChooser.getSelectedFile().getAbsolutePath());
                                JOptionPane.showMessageDialog(null, "Загрузка всех объектов прошла успешно", "Результат", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                logger.error("\n" + "Ошибка при загрузке всех объектов из файла: " + ex.getMessage());
                                JOptionPane.showMessageDialog(null,"Ошибка при загрузке всех объектов" + ex.getMessage(), "Результат", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        ReloadObjects();
                    }
                });

        JMenuItem saveItem = new JMenuItem("Сохранить");
        saveItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Выберите файл для сохранения данных");

                        // Установка фильтра для файлов с определённым расширением (например, txt)
                        fileChooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt"));

                        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();

                            try {
                                _storage.SaveData(fileChooser.getSelectedFile().getAbsolutePath());
                                logger.info("Сохранение всех объектов в файл: " + fileChooser.getSelectedFile().getAbsolutePath());
                                JOptionPane.showMessageDialog(null,"Сохранение объектов прошло успешно", "Результат", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                logger.error("Error saving all maps to file: " +ex.getMessage());
                                JOptionPane.showMessageDialog(null, "Ошибка при сохранении объектов: "+ex.getMessage(), "Результат", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
        );

        JMenuItem openItemSingle = new JMenuItem("Открыть одиночный");
        openItemSingle.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();

                        fileChooser.setDialogTitle("Выберите файл для загрузки данных");

                        //Установка фильтра для файлов с определённым расширением (например, txt)
                        fileChooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt"));

                        fileChooser.setDialogTitle("Выберите файл для загрузки данных");
                        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();

                            try {
                                _storage.LoadDataSingle(fileChooser.getSelectedFile().getAbsolutePath());
                                ReloadObjects();
                                logger.info("Загрузка всех объектов из файла: " + fileChooser.getSelectedFile().getAbsolutePath());
                                JOptionPane.showMessageDialog(null, "Загрузка прошла успешно", "Результат", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                logger.error("\n" + "Ошибка при загрузке всех объектов из файла: " + ex.getMessage());
                                JOptionPane.showMessageDialog(null, "Не загрузилось" + ex.getMessage(), "Результат", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        ReloadObjects();
                    }
                });

        JMenuItem saveItemSingle = new JMenuItem("Сохранение одиночного");
        saveItemSingle.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if (jListStorage.getSelectedValue() == null){
                            JOptionPane.showMessageDialog(null, "Не выбран гараж", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Выберите файл для сохранения данных");

                        // Установка фильтра для файлов с определенным расширением (например, .txt)
                        fileChooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы (*.txt)", "txt"));

                        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();

                            try {
                                _storage.SaveDataSingle(fileChooser.getSelectedFile().getAbsolutePath(), jListStorage.getSelectedValue());
                                logger.info("Сохранение всех объектов в файл: " + fileChooser.getSelectedFile().getAbsolutePath());
                                JOptionPane.showMessageDialog(null,"Сохранение прошло успешно", "Результат", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                logger.error("Error saving all maps to file: " +ex.getMessage());
                                JOptionPane.showMessageDialog(null, "Не сохранилось: "+ex.getMessage(), "Результат", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
        );

        // Добавляем пункты в меню
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(openItemSingle);
        fileMenu.add(saveItemSingle);

        // Добавляем меню в панель меню
        menuBar.add(fileMenu);

        Frame.setSize(880, 520);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setLayout(null);
        canv.setBounds(0, 0, pictureBoxWidth, pictureBoxHeight);
        ButtonAddVehicle.setBounds(pictureBoxWidth - 10, 10, 170, 30);
        TextBoxNumber.setBounds(pictureBoxWidth - 10, 50, 170, 30);
        ButtonRemoveTank.setBounds(pictureBoxWidth - 10, 90, 170, 30);
        ButtonRefreshCollection.setBounds(pictureBoxWidth - 10, 130, 170, 30);
        toFormTankGenerate.setBounds(pictureBoxWidth - 10, 170, 170, 30);

        buttonAddSet.setBounds(pictureBoxWidth - 10, 210, 170, 20);
        textBoxSetName.setBounds(pictureBoxWidth - 10, 240, 170, 20);
        jListStorage.setBounds(pictureBoxWidth - 10, 270, 170, 80);
        buttonRemoveSet.setBounds(pictureBoxWidth - 10, 360, 170, 20);

        buttonGetRemoved.setBounds(pictureBoxWidth - 10, 390, 170, 20);

        menuBar.setBounds(pictureBoxWidth - 10, 420, 170, 20);

        Frame.add(canv);
        Frame.add(ButtonAddVehicle);
        Frame.add(ButtonRemoveTank);
        Frame.add(ButtonRefreshCollection);
        Frame.add(TextBoxNumber);
        Frame.add(toFormTankGenerate);

        Frame.add(buttonAddSet);
        Frame.add(textBoxSetName);
        Frame.add(jListStorage);
        Frame.add(buttonRemoveSet);

        Frame.add(buttonGetRemoved);
        Frame.add(menuBar);

        Frame.setVisible(true);
    }
}