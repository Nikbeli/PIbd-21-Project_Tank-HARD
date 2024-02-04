import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormTankGenerate extends JFrame {
    static int pictureBoxWidth = 560;
    static int pictureBoxHeight = 560;
    public DrawingArmoVehicle _drawingVehicle;
    private class Canvas extends JComponent{
        public Canvas() {
        }
        public void paintComponent (Graphics g){
            if (_drawingVehicle == null){
                return;
            }
            super.paintComponents (g) ;
            Graphics2D g2d = (Graphics2D)g;
            _drawingVehicle.SetPosition(250, 250);
            _drawingVehicle.DrawTransport(g2d);
            super.repaint();
        }
    }
    DoubleParametrized<EntityArmoVehicle, IOrnamentForm> genericTankGenerate;
    public FormTankGenerate(){
        _drawingVehicle = null;
        Canvas canv = new Canvas();
        setSize(640, 640);
        setLayout(null);
        canv.setBounds(0,0, pictureBoxWidth, pictureBoxHeight);

        genericTankGenerate = new DoubleParametrized<>();
        genericTankGenerate.Add(new EntityArmoVehicle(100, 100, Color.BLUE, 2));
        genericTankGenerate.Add(new EntityArmoVehicle(100, 100, Color.RED, 3));
        genericTankGenerate.Add(new EntityArmoVehicle(100, 100, Color.GRAY, 4));
        genericTankGenerate.Add(new EntityTank(100, 100, Color.BLUE, 2, Color.ORANGE, true, true, true));
        genericTankGenerate.Add(new DrawingStarOrnament());
        genericTankGenerate.Add(new DrawingWheelsCombination());

        JButton createButton = new JButton("Сгенерировать");
        createButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    _drawingVehicle = genericTankGenerate.GenerateTank(pictureBoxWidth,pictureBoxHeight);
                    canv.repaint();
                }
            }
        );
        createButton.setBounds(pictureBoxWidth/2 - 40, pictureBoxHeight-20, 180, 20);

        add(canv);
        add(createButton);
        setVisible(true);
    }
}