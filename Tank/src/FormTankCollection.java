import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormTankCollection {
    private class Canvas extends JComponent {
        public TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank> _tank;
        public Canvas() {
        }
        public void paintComponent (Graphics g) {
            super.paintComponent(g);
            if (_tank.ShowTanks() != null) {
                g.drawImage(_tank.ShowTanks(), 0, 10, this);
            }
            super.repaint();
        }
    }
    Canvas canv;
    static int pictureBoxWidth = 700;
    static int pictureBoxHeight = 480;
    private TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank> _tank;
    public void Draw(){
        canv.repaint();
    }
    FormTankCollection() {
        canv = new Canvas();
        JFrame Frame = new JFrame ("TanksCollecltion");
        _tank = new TanksGenericCollections<DrawingArmoVehicle, DrawingObjectTank>(pictureBoxWidth, pictureBoxHeight);
        canv._tank = _tank;

        JButton ButtonAddTank = new JButton("Добавить технику");

        ButtonAddTank.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        FormTank form = new FormTank();
                        form.buttonSelectTank.addActionListener(
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        if (_tank.Add(form._drawingVehicle) != -1) {
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

        JTextField TextBoxNumber = new JTextField();
        JButton ButtonRemoveTank = new JButton("Удалить технику");
        ButtonRemoveTank.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
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
                        if (_tank.remove(pos) != null) {
                            JOptionPane.showMessageDialog(null, "Объект удален", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            Draw();
                        } else {
                            JOptionPane.showMessageDialog(null, "Не удалось удалить объект", "Информация", JOptionPane.INFORMATION_MESSAGE);
                        }
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
                        FormTankGenerate formTankGenerate  = new FormTankGenerate();
                    }
                }
        );

        Frame.setSize (880, 520);
        Frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        Frame.setLayout(null);
        canv.setBounds(0, 0, pictureBoxWidth, pictureBoxHeight);
        ButtonAddTank.setBounds(pictureBoxWidth - 50, 10, 170, 30);
        TextBoxNumber.setBounds(pictureBoxWidth - 50, 50, 170, 20);
        ButtonRemoveTank.setBounds(pictureBoxWidth - 50, 80, 170, 30);
        ButtonRefreshCollection.setBounds(pictureBoxWidth - 50, 120, 170, 30);
        toFormTankGenerate.setBounds(pictureBoxWidth - 50, 160, 170, 30);
        Frame.add(canv);
        Frame.add(ButtonAddTank);
        Frame.add(ButtonRemoveTank);
        Frame.add(ButtonRefreshCollection);
        Frame.add(TextBoxNumber);
        Frame.add(toFormTankGenerate);
        Frame.setVisible(true);
    }
}