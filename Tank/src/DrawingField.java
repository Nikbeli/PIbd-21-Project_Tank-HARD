import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DrawingField  extends JPanel {
    private final FormTank field;
    DrawingTank _Tank;
    public DrawingField(FormTank field) {
        this.field = field;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D)g;
        if (_Tank!=null)
            _Tank.DrawTransport(g2);
        else return;
    }
    public void UpButtonAction() {
        if (_Tank!=null)
            _Tank.MoveTransport(Direction.Up);
        else
            return;
    }
    public void DownButtonAction() {
        if (_Tank!=null)
            _Tank.MoveTransport(Direction.Down);
        else
            return;
    }
    public void RightButtonAction() {
        if (_Tank!=null)
            _Tank.MoveTransport(Direction.Right);
        else
            return;
    }
    public void LeftButtonAction(){
        if (_Tank!=null)
            _Tank.MoveTransport(Direction.Left);
        else
            return;
    }
    public void CreateButtonAction() {
        Random rnd=new Random();
        _Tank=new DrawingTank();
        _Tank.Init(rnd.nextInt(50)+10,rnd.nextInt(100)+500,new Color(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256)));
        _Tank.SetPosition(rnd.nextInt(100)+10,rnd.nextInt(100)+10,getWidth(),getHeight());
    }
    public void ResizeField() {
        if (_Tank!=null)
            _Tank.ChangeBorders(getWidth(),getHeight());
        else return;
    }
}
