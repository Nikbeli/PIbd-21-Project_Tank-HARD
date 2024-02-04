import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.Queue;

public class FormTankCollection {
    private class Canvas extends JComponent {
        public Canvas() {
        }

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

    FormTankCollection() {
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
                                        if (obj != null && obj.Add(form._vehicle) != -1) {
                                            JOptionPane.showMessageDialog(null, "Объект добавлен", "Информация", JOptionPane.INFORMATION_MESSAGE);
                                            Draw();
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Не удалось добавить объект", "Информация", JOptionPane.INFORMATION_MESSAGE);
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
                        var removed = obj.remove(pos);
                        if (removed != null) {
                            Queue.add(removed);
                            JOptionPane.showMessageDialog(null, "Объект удален", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            Draw();
                        } else {
                            JOptionPane.showMessageDialog(null, "Не удалось удалить объект", "Информация", JOptionPane.INFORMATION_MESSAGE);
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
                            return;
                        }
                        _storage.AddSet(textBoxSetName.getText());
                        ReloadObjects();
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
                            return;
                        }
                        if (JOptionPane.showConfirmDialog(null, "Удалить объект " + jListStorage.getSelectedValue() + "?", "Удаление", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                            return;
                        }
                        _storage.DelSet(jListStorage.getSelectedValue());
                        ReloadObjects();
                    }
                }
        );

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

        Frame.setVisible(true);
    }
}