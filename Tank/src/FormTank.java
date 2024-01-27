import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
public class FormTank extends JFrame {
    private int Width;
    private int Height;

    JPanel BottomPanel = new JPanel();
    JPanel CreatePanel = new JPanel();
    JPanel BottomAndCreatePanel = new JPanel();
    JPanel DimentionPanel = new JPanel();
    JPanel UpPanel = new JPanel();
    JPanel DownPanel = new JPanel();
    JPanel LRPanel = new JPanel();

    DrawingField field = new DrawingField(this);

    JButton ButtonCreate=new JButton("Создать");
    Icon iconUp = new ImageIcon("Resources/KeyUp.png");
    JButton ButtonUp=new JButton(iconUp);

    Icon iconDown = new ImageIcon("Resources/KeyDown.png");
    JButton ButtonDown=new JButton(iconDown);

    Icon iconRight = new ImageIcon("Resources/KeyRight.png");
    JButton ButtonRight=new JButton(iconRight);

    Icon iconLeft = new ImageIcon("Resources/KeyLeft.png");
    JButton ButtonLeft=new JButton(iconLeft);
    public FormTank() {
        super("FormTank");
        setSize(800,600);
        Width=getWidth();
        Height=getHeight();
        ShowWindow();
        RefreshWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void ShowWindow() {

        Dimension dimen=new Dimension(30,30);

        // Обработка нажатия кнопки
        ButtonUp.setPreferredSize(dimen);
        ButtonUp.addActionListener(e-> {
            field.UpButtonAction();
            repaint();
        });

        // Обработка нажатия кнопки
        ButtonDown.setPreferredSize(dimen);
        ButtonDown.addActionListener(e-> {
            field.DownButtonAction();
            repaint();
        });

        // Обработка нажатия кнопки
        ButtonRight.setPreferredSize(dimen);
        ButtonRight.addActionListener(e-> {
            field.RightButtonAction();
            repaint();
        });

        // Обработка нажатия кнопки
        ButtonLeft.setPreferredSize(dimen);
        ButtonLeft.addActionListener(e-> {
            field.LeftButtonAction();
            repaint();
        });

        // Добавление кнопок на панель (Левая и правая стрелки)
        LRPanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
        LRPanel.setBackground(new Color(0,0,0,0));
        LRPanel.add(ButtonLeft);
        LRPanel.add(ButtonRight);

        // Добавление кнопки (Стрелка вверх)
        UpPanel.setLayout(new FlowLayout());
        UpPanel.setBackground(new Color(0,0,0,0));
        UpPanel.add(ButtonUp);

        // Добавление кнопки (Стрелка вниз)
        DownPanel.setLayout(new FlowLayout());
        DownPanel.setBackground(new Color(0,0,0,0));
        DownPanel.add(ButtonDown);

        DimentionPanel.setLayout(new BoxLayout(DimentionPanel,BoxLayout.Y_AXIS));
        DimentionPanel.setBackground(new Color(0,0,0,0));
        DimentionPanel.add(UpPanel);
        DimentionPanel.add(LRPanel);
        DimentionPanel.add(DownPanel);
        add(DimentionPanel);

        // нажатие кнопки создания танка
        CreatePanel.setLayout(new FlowLayout());
        CreatePanel.setBackground(new Color(0,0,0,0));
        CreatePanel.add(ButtonCreate);
        ButtonCreate.addActionListener(e-> {
            field.CreateButtonAction();
            repaint();
        });

        BottomPanel.setLayout(new FlowLayout());
        BottomPanel.setBackground(new Color(0,0,0,0));

        BottomAndCreatePanel.setLayout(new BoxLayout(BottomAndCreatePanel,BoxLayout.Y_AXIS));
        BottomAndCreatePanel.setBackground(new Color(0,0,0,0));
        BottomAndCreatePanel.add(CreatePanel);
        BottomAndCreatePanel.add(BottomPanel);

        add(BottomAndCreatePanel);
        add(field);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Width=getWidth();
                Height=getHeight();

                field.ResizeField();
                repaint();
                RefreshWindow();
            }
        });
    }
    public void RefreshWindow() {
        field.setBounds(0,0,Width,Height);
        BottomAndCreatePanel.setBounds(-320,Height-110,Width,80);
        DimentionPanel.setBounds(Width-170,Height-170,190,140);
    }
}
