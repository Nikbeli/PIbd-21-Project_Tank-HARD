import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FormTank {
    private DrawingArmoVehicle _drawingArmoVehicle;
    private AbstractStrategy abstractStrategy;
    Canvas canv;
    static int pictureBoxWidth = 980;
    static int pictureBoxHeight = 560;

    public void Draw() {
        canv.repaint();
    }

    public FormTank() {
        JFrame Frame = new JFrame("Form Tank");
        JButton buttonCreateArmoVehicle = new JButton("Создать Бронемашину");
        JButton buttonCreateTank = new JButton("Создать Танк");
        JButton buttonStrategysStep = new JButton("Шаг");
        JComboBox ComboBoxStrategy = new JComboBox(new String[]{"к центру", "к краю формочки"});

        Icon iconUp = new ImageIcon("Tank//Resources//KeyUp.png");
        JButton up = new JButton(iconUp);
        up.setName("up");

        Icon iconDown = new ImageIcon("Tank//Resources//KeyDown.png");
        JButton down = new JButton(iconDown);
        down.setName("down");

        Icon iconRight = new ImageIcon("Tank//Resources//KeyRight.png");
        JButton right = new JButton(iconRight);
        right.setName("right");

        Icon iconLeft = new ImageIcon("Tank//Resources//KeyLeft.png");
        JButton left = new JButton(iconLeft);
        left.setName("left");

        buttonStrategysStep.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (_drawingArmoVehicle == null) {
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

                            abstractStrategy.SetData(new
                                    DrawingObjectTank(_drawingArmoVehicle), pictureBoxWidth, pictureBoxHeight);
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
                        _drawingArmoVehicle = new DrawingTank(
                                random.nextInt(100, 300),
                                random.nextInt(1000, 3000),
                                new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256)),
                                random.nextInt(2, 6),
                                new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256)),
                                true,
                                true,
                                true,
                                pictureBoxWidth,
                                pictureBoxHeight,
                                true
                        );
                        _drawingArmoVehicle.SetPosition(random.nextInt(10, 100), random.nextInt(10, 100));
                        canv._drawingArmoVehicle = _drawingArmoVehicle;
                        Draw();
                    }
                }
        );

        buttonCreateArmoVehicle.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Random random = new Random();
                        _drawingArmoVehicle = new DrawingArmoVehicle(
                                random.nextInt(100, 300),
                                random.nextInt(1000, 3000),
                                new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256)),
                                random.nextInt(2, 5),
                                1000,
                                560);
                        _drawingArmoVehicle.SetPosition(random.nextInt(10, 100), random.nextInt(10, 100));
                        canv._drawingArmoVehicle = _drawingArmoVehicle;
                        Draw();
                    }
                }
        );

        ActionListener actioListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_drawingArmoVehicle == null) {
                    return;
                }
                switch (((JButton) (e.getSource())).getName()) {
                    case "up":
                        _drawingArmoVehicle.MoveTransport(Direction.Up);
                        break;
                    case "down":
                        _drawingArmoVehicle.MoveTransport(Direction.Down);
                        break;
                    case "left":
                        _drawingArmoVehicle.MoveTransport(Direction.Left);
                        break;
                    case "right":
                        _drawingArmoVehicle.MoveTransport(Direction.Right);
                        break;
                }
                Draw();
            }
        };

        up.addActionListener(actioListener);
        down.addActionListener(actioListener);
        left.addActionListener(actioListener);
        right.addActionListener(actioListener);

        Frame.setSize(1000, 600);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setLayout(null);
        canv = new Canvas();
        canv.setBounds(0, 0, pictureBoxWidth, pictureBoxHeight);

        buttonCreateArmoVehicle.setBounds(5, 500, 170, 40);
        buttonCreateTank.setBounds(185, 500, 170, 40);

        up.setBounds(900, 480, 40, 40);
        down.setBounds(900, 520, 40, 40);
        left.setBounds(860, 520, 40, 40);
        right.setBounds(940, 520, 40, 40);

        ComboBoxStrategy.setBounds(pictureBoxWidth - 150, 20, 150, 20);
        buttonStrategysStep.setBounds(pictureBoxWidth - 150, 45, 150, 20);

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

class Canvas extends JComponent {
    public DrawingArmoVehicle _drawingArmoVehicle;

    public Canvas() {
    }

    public void paintComponent(Graphics g) {
        if (_drawingArmoVehicle == null) {
            return;
        }
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        _drawingArmoVehicle.DrawTransport(g2d);
        super.repaint();
    }
}