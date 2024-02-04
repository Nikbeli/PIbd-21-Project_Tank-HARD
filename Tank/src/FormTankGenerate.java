import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormTankGenerate extends JFrame {
    static int pictureBoxWidth = 560;
    static int pictureBoxHeight = 560;
    public DrawingArmoVehicle _drawingTank;
    private class Canvas extends JComponent{
        public Canvas() {
        }
        public void paintComponent (Graphics g){
            if (_drawingTank == null){
                return;
            }
            super.paintComponents (g) ;
            Graphics2D g2d = (Graphics2D)g;
            _drawingTank.SetPosition(250, 250);
            _drawingTank.DrawTransport(g2d);
            super.repaint();
        }
    }
    DoubleParametrized<EntityArmoVehicle, IOrnamentForm> genericTankGenerate;
    public FormTankGenerate(){
        _drawingTank = null;
        Canvas canv = new Canvas();
        setSize (640, 640);
        setLayout(null);
        canv.setBounds(0,0,pictureBoxWidth, pictureBoxHeight);

        genericTankGenerate = new DoubleParametrized<>();
        genericTankGenerate.Add(new EntityArmoVehicle(100, 100, Color.BLUE, 2));
        genericTankGenerate.Add(new EntityArmoVehicle(100, 100, Color.RED, 3));
        genericTankGenerate.Add(new EntityArmoVehicle(100, 100, Color.GRAY, 4));
        genericTankGenerate.Add(new EntityTank(100, 100, Color.BLUE, 2, Color.ORANGE, true, true, true));
        genericTankGenerate.Add(new DrawingStarOrnament());
        genericTankGenerate.Add(new DrawingWheelsCombination());

        JButton creatButton = new JButton("Сгенерировать");
        creatButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    _drawingTank = genericTankGenerate.GenerateTank(pictureBoxWidth,pictureBoxHeight);
                    canv.repaint();
                }
            }
        );
        creatButton.setBounds(pictureBoxWidth/2 - 40, pictureBoxHeight-20, 180, 20);

        add(canv);
        add(creatButton);
        setVisible(true);
    }
}