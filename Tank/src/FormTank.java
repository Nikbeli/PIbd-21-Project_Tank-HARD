import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FormTank {
    private class Canvas extends JComponent {
        public Canvas() {
        }

        public void paintComponent(Graphics g) {
            if (_drawingVehicle == null) {
                return;
            }
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D) g;
            _drawingVehicle.DrawTransport(g2d);
            super.repaint();
        }
    }

    public DrawingArmoVehicle SelectedVehicle;
    public boolean DialogResult = false;
    public DrawingArmoVehicle _drawingVehicle;
    private AbstractStrategy abstractStrategy;
    Canvas canv;
    static int pictureBoxWidth = 980;
    static int pictureBoxHeight = 560;
    public JButton buttonSelectTank;
    public JFrame Frame;

    public void Draw() {
        canv.repaint();
    }

    public FormTank() {
        SelectedVehicle = null;
        Frame = new JFrame("Tank");
        JButton buttonCreateArmoVehicle = new JButton("Добавить бронетехнику");
        JButton buttonCreateTank = new JButton("Добавить танк");
        JButton buttonStrategysStep = new JButton("Шаг");
        buttonSelectTank = new JButton("Добавить");

        JComboBox<String> ComboBoxStrategy = new JComboBox<String>(
                new String[]{
                        "к центру",
                        "к краю",
                });
        JButton up = new JButton();
        up.setName("up");
        ImageIcon iconUp = new ImageIcon("Resources//KeyUp.png");
        up.setIcon(iconUp);

        JButton down = new JButton();
        down.setName("down");
        ImageIcon iconDown = new ImageIcon("Resources//KeyDown.png");
        down.setIcon(iconDown);

        JButton left = new JButton();
        left.setName("left");
        ImageIcon iconLeft = new ImageIcon("Resources//KeyLeft.png");
        left.setIcon(iconLeft);

        JButton right = new JButton();
        right.setName("right");
        ImageIcon iconRight = new ImageIcon("Resources//KeyRight.png");
        right.setIcon(iconRight);

        buttonStrategysStep.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (_drawingVehicle == null) {
                            return;
                        }
                        if (ComboBoxStrategy.isEnabled()) {
                            switch (ComboBoxStrategy.getSelectedIndex()) {
                                case 0:
                                    abstractStrategy = new MoveToCenter();
                                    break;
                                case 1:
                                    abstractStrategy = new MoveToBorder();
                                    break;
                                default:
                                    abstractStrategy = null;
                                    break;
                            }

                            if (abstractStrategy == null) {
                                return;
                            }
                            abstractStrategy.SetData(new DrawingObjectTank(_drawingVehicle), pictureBoxWidth, pictureBoxHeight);
                            ComboBoxStrategy.setEnabled(false);
                        }
                        if (abstractStrategy == null) {
                            return;
                        }
                        abstractStrategy.MakeStep();
                        Draw();
                        if (abstractStrategy.GetStatus() == Status.Finish) {
                            ComboBoxStrategy.setEnabled(true);
                            abstractStrategy = null;
                        }
                    }
                }
        );

        buttonCreateTank.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Random random = new Random();
                        Color color = new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256));
                        // Вызываем диалоговое окно выбора цвета
                        Color selectedColor = JColorChooser.showDialog(Frame, "Выберите цвет", Color.WHITE);
                        if (selectedColor != null) {
                            color = selectedColor;
                        }
                        Color coloradditional = new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256));
                        // Вызываем диалоговое окно выбора цвета
                        selectedColor = JColorChooser.showDialog(Frame, "Выберите цвет", Color.WHITE);
                        if (selectedColor != null) {
                            coloradditional = selectedColor;
                        }
                        _drawingVehicle = new DrawingTank(
                                random.nextInt(100, 300),
                                random.nextInt(1000, 3000),
                                color,
                                random.nextInt(2, 5),
                                coloradditional,
                                true,
                                true,
                                true,
                                pictureBoxWidth,
                                pictureBoxHeight,
                                true
                        );
                        _drawingVehicle.SetPosition(random.nextInt(10, 100), random.nextInt(10, 100));
                        Draw();
                    }
                }
        );

        buttonCreateArmoVehicle.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Random random = new Random();
                        Color color = new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256));
                        // Вызываем диалоговое окно выбора цвета
                        Color selectedColor = JColorChooser.showDialog(Frame, "Выберите цвет", Color.WHITE);
                        if (selectedColor != null) {
                            color = selectedColor;
                        }
                        _drawingVehicle = new DrawingArmoVehicle(
                                random.nextInt(100, 300),
                                random.nextInt(1000, 3000),
                                color,
                                random.nextInt(2, 5),
                                pictureBoxWidth,
                                pictureBoxHeight
                        );

                        _drawingVehicle.SetPosition(random.nextInt(10, 100), random.nextInt(10, 100));
                        Draw();
                    }
                }
        );

        ActionListener actioListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_drawingVehicle == null) {
                    return;
                }
                switch (((JButton) (e.getSource())).getName()) {
                    case "up":
                        _drawingVehicle.MoveTransport(Direction.Up);
                        break;
                    case "down":
                        _drawingVehicle.MoveTransport(Direction.Down);
                        break;
                    case "left":
                        _drawingVehicle.MoveTransport(Direction.Left);
                        break;
                    case "right":
                        _drawingVehicle.MoveTransport(Direction.Right);
                        break;
                }
                Draw();
            }
        };

        up.addActionListener(actioListener);
        down.addActionListener(actioListener);
        left.addActionListener(actioListener);
        right.addActionListener(actioListener);

        Frame.setSize(1000, 620);
        Frame.setLayout(null);
        canv = new Canvas();
        canv.setBounds(0, 0, pictureBoxWidth, pictureBoxHeight);
        buttonCreateArmoVehicle.setBounds(2, 520, 180, 40);
        buttonCreateTank.setBounds(185, 520, 150, 40);
        up.setBounds(900, 480, 40, 40);
        down.setBounds(900, 520, 40, 40);
        left.setBounds(860, 520, 40, 40);
        right.setBounds(940, 520, 40, 40);
        ComboBoxStrategy.setBounds(pictureBoxWidth - 150, 20, 150, 20);
        buttonStrategysStep.setBounds(pictureBoxWidth - 150, 45, 150, 20);
        buttonSelectTank.setBounds(pictureBoxWidth / 2, 530, 150, 30);
        Frame.add(buttonSelectTank);
        Frame.add(canv);
        Frame.add(buttonCreateArmoVehicle);
        Frame.add(buttonCreateTank);
        Frame.add(up);
        Frame.add(down);
        Frame.add(left);
        Frame.add(right);
        Frame.add(ComboBoxStrategy);
        Frame.add(buttonStrategysStep);
        Frame.setVisible(true);
    }
}