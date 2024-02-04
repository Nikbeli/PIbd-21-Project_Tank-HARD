import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;
import java.io.IOException;

public class FormTankConfig {
    private class Canvas extends JComponent {
        public Canvas() { }

        public void paintComponent (Graphics g) {
            if(_vehicle == null) {
                return;
            }
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D)g;
            _vehicle.DrawTransport(g2d);
            super.repaint();
        }
    }

    //Класс для перетаскивания объекта на формочку
    private class LabelTransferHandler extends TransferHandler {
        @Override
        public int getSourceActions(JComponent c) { return TransferHandler.COPY; }

        @Override
        protected Transferable createTransferable (JComponent c) { return  new StringSelection(((JLabel)c).getText()); }
    }

    // Класс для возможности передвижения цвета
    private class ColorTransferable implements Transferable {
        private Color color;
        private static final DataFlavor colorDataFlavor = new DataFlavor(Color.class, "Color");
        public ColorTransferable(Color color) { this.color = color; }

        @Override
        public DataFlavor[] getTransferDataFlavors() { return new DataFlavor[]{ colorDataFlavor }; }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) { return colorDataFlavor.equals(flavor); }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if(isDataFlavorSupported(flavor)) {
                return color;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }

    // Для перетаскивания цвета к объекту
    private class PanelTransferHandler extends TransferHandler {
        @Override
        public int getSourceActions(JComponent c) { return TransferHandler.COPY; }

        @Override
        protected Transferable createTransferable(JComponent c) {
            return new ColorTransferable(((JPanel)c).getBackground());
        }
    }

    // Для обработки нажатий
    private class LabelMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            ((JLabel)e.getComponent()).getTransferHandler().exportAsDrag(((JLabel)e.getComponent()), e, TransferHandler.COPY);
        }
    }

    private class PanelMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            ((JPanel)e.getComponent()).getTransferHandler().exportAsDrag(((JPanel)e.getComponent()), e, TransferHandler.COPY);
        }
    }

    // Для передвижения wheels по формочке
    private class WheelTransferable implements Transferable {
        private  IOrnamentForm wheelDrawing;
        private static final DataFlavor wheelDrawingDataFlavor = new DataFlavor(IOrnamentForm.class, "Wheel Drawing");
        public WheelTransferable(IOrnamentForm wheelDrawing) { this.wheelDrawing = wheelDrawing; }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) { return flavor.equals(wheelDrawingDataFlavor); }

        @Override
        public DataFlavor[] getTransferDataFlavors() { return new DataFlavor[] { wheelDrawingDataFlavor }; }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if(isDataFlavorSupported(flavor)) {
                return wheelDrawing;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }

    // Для отрисовки wheels, которые можно брать и перетаскивать на объект
    private class ComponentWheel extends JComponent {
        public IOrnamentForm wheelDrawing;

        // Конструктор
        public ComponentWheel(IOrnamentForm _wheelDrawing) {
            wheelDrawing = _wheelDrawing;
            this.addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            ((ComponentWheel) e.getComponent()).getTransferHandler().exportAsDrag(((ComponentWheel) e.getComponent()), e, TransferHandler.COPY);
                        }
                    });
            this.setTransferHandler(
                    new TransferHandler() {
                        @Override
                        public int getSourceActions(JComponent c) {
                            return TransferHandler.COPY;
                        }

                        @Override
                        protected Transferable createTransferable(JComponent c) {
                            return new WheelTransferable(((ComponentWheel)c).wheelDrawing);
                        }
                    }
            );
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            wheelDrawing.Draw(g2d,-10, -42);
            super.repaint();
        }
    }

    final int WindowHeight = 700;
    final int WindowWidth = 1000;
    final int CanvasHeight = 600;
    final int CanvasWidth = 800;
    public DrawingArmoVehicle _vehicle = null;
    public JButton buttonAdd;
    public JFrame Frame;
    public Canvas canvas;

    public FormTankConfig() {
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        JLabel labelSpeed = new JLabel("Speed");
        JLabel labelWeight = new JLabel("Weight");
        JLabel labelWheelNum = new JLabel("Wheel");
        SpinnerModel spinnerModel = new SpinnerNumberModel(100, 100, 1000, 1);
        JSpinner numericSpeed = new JSpinner(spinnerModel);
        SpinnerModel spinnerModel2 = new SpinnerNumberModel(100, 100, 1000, 1);
        JSpinner numericWeight = new JSpinner(spinnerModel2);
        SpinnerModel spinnerModel3 = new SpinnerNumberModel(2, 2, 5, 1);
        JSpinner numericWheelNum = new JSpinner(spinnerModel3);
        JCheckBox checkBoxBodyKit = new JCheckBox("BodyKit");
        JCheckBox checkBoxCaterpillar = new JCheckBox("Caterpillar");
        JCheckBox checkBoxTower = new JCheckBox("Tower");
        JPanel[] colorPanels = {
                new JPanel(),new JPanel(),new JPanel(),new JPanel(),
                new JPanel(),new JPanel(),new JPanel(),new JPanel(),
        };
        colorPanels[0].setBackground(Color.BLACK);
        colorPanels[1].setBackground(Color.BLUE);
        colorPanels[2].setBackground(Color.GRAY);
        colorPanels[3].setBackground(Color.YELLOW);
        colorPanels[4].setBackground(Color.RED);
        colorPanels[5].setBackground(Color.GREEN);
        colorPanels[6].setBackground(Color.ORANGE);
        colorPanels[7].setBackground(Color.WHITE);
        for (var it : colorPanels){
            it.setTransferHandler(new PanelTransferHandler());
            it.addMouseListener(new PanelMouseAdapter());
        }

        JLabel labelArmoVehicle = new JLabel("ArmoVehicle");
        labelArmoVehicle.setTransferHandler(new LabelTransferHandler());
        labelArmoVehicle.addMouseListener(new LabelMouseAdapter());
        labelArmoVehicle.setBorder(border);
        labelArmoVehicle.setHorizontalAlignment(SwingConstants.CENTER);
        labelArmoVehicle.setVerticalAlignment(SwingConstants.CENTER);

        JLabel labelTank = new JLabel("Tank");
        labelTank.setTransferHandler(new LabelTransferHandler());
        labelTank.addMouseListener(new LabelMouseAdapter());
        labelTank.setBorder(border);
        labelTank.setHorizontalAlignment(SwingConstants.CENTER);
        labelTank.setVerticalAlignment(SwingConstants.CENTER);

        JLabel labelColor = new JLabel("Color");
        labelColor.setBorder(border);
        labelColor.setHorizontalAlignment(SwingConstants.CENTER);
        labelColor.setVerticalAlignment(SwingConstants.CENTER);
        labelColor.setTransferHandler(
                new TransferHandler(){
                    @Override
                    public boolean canImport(TransferSupport support) {
                        return support.isDataFlavorSupported(ColorTransferable.colorDataFlavor);
                    }

                    @Override
                    public boolean importData(TransferSupport support) {
                        if (canImport(support)) {
                            try {
                                Color color = (Color) support.getTransferable().getTransferData(ColorTransferable.colorDataFlavor);
                                if (_vehicle == null)
                                    return false;
                                _vehicle.ArmoVehicle.setBodyColor(color);
                                canvas.repaint();
                                return true;
                            } catch (UnsupportedFlavorException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                }
        );

        JLabel labelAdditionalColor = new JLabel("Additional color");
        labelAdditionalColor.setBorder(border);
        labelAdditionalColor.setHorizontalAlignment(SwingConstants.CENTER);
        labelAdditionalColor.setVerticalAlignment(SwingConstants.CENTER);
        labelAdditionalColor.setTransferHandler(
                new TransferHandler(){
                    @Override
                    public boolean canImport(TransferHandler.TransferSupport support) {
                        return support.isDataFlavorSupported(ColorTransferable.colorDataFlavor);
                    }

                    @Override
                    public boolean importData(TransferHandler.TransferSupport support) {
                        if (canImport(support)) {
                            try {
                                Color color = (Color) support.getTransferable().getTransferData(ColorTransferable.colorDataFlavor);
                                if (_vehicle == null)
                                    return false;
                                if (!(_vehicle instanceof DrawingTank))
                                    return false;
                                ((EntityTank)_vehicle.ArmoVehicle).setAdditionalColor(color);
                                return true;
                            } catch (UnsupportedFlavorException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                }
        );

        JLabel labelWheel = new JLabel("Wheel");
        labelWheel.setBorder(border);
        labelWheel.setHorizontalAlignment(SwingConstants.CENTER);
        labelWheel.setVerticalAlignment(SwingConstants.CENTER);
        labelWheel.setTransferHandler(
                new TransferHandler(){
                    @Override
                    public boolean canImport(TransferHandler.TransferSupport support) {
                        return support.isDataFlavorSupported(WheelTransferable.wheelDrawingDataFlavor);
                    }

                    @Override
                    public boolean importData(TransferHandler.TransferSupport support) {
                        if (canImport(support)) {
                            try {
                                IOrnamentForm wheelDrawing = (IOrnamentForm) support.getTransferable().getTransferData(WheelTransferable.wheelDrawingDataFlavor);
                                if (_vehicle == null)
                                    return false;
                                wheelDrawing.setDigit(_vehicle.ArmoVehicle.numWheel);
                                _vehicle.OrnamentsForm = wheelDrawing;
                                canvas.repaint();
                            } catch (UnsupportedFlavorException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                }
        );

        canvas = new Canvas();
        canvas.setTransferHandler(
                new TransferHandler(){
                    @Override
                    public boolean canImport(TransferHandler.TransferSupport support) {
                        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
                    }

                    @Override
                    public boolean importData(TransferHandler.TransferSupport support) {
                        if (canImport(support)) {
                            try {
                                String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                                switch (data) {
                                    case "ArmoVehicle":
                                        _vehicle = new DrawingArmoVehicle((int)numericSpeed.getValue(), (int)numericWeight.getValue(), Color.WHITE, (int)numericWheelNum.getValue(), CanvasWidth,CanvasHeight);
                                        break;
                                    case "Tank":
                                        _vehicle = new DrawingTank((int)numericSpeed.getValue(), (int)numericWeight.getValue(), Color.WHITE, (int)numericWheelNum.getValue(), Color.BLACK, checkBoxBodyKit.isSelected(), checkBoxCaterpillar.isSelected(), checkBoxTower.isSelected(),  CanvasWidth, CanvasHeight);
                                        break;
                                }
                                canvas.repaint();
                                return true;
                            } catch (UnsupportedFlavorException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                }
        );

        buttonAdd = new JButton("Add");
        JButton buttonCancel = new JButton("Cancel");

        ComponentWheel componentSuspensionOrnament = new ComponentWheel(new DrawingSuspensionOrnament());
        ComponentWheel componentAsteriskOrnament = new ComponentWheel(new DrawingStarOrnament());
        ComponentWheel componentWheelCombination = new ComponentWheel(new DrawingWheelsCombination());

        labelSpeed.setBounds(10,10,40,20);
        labelWeight.setBounds(10,40,40,20);
        labelWheelNum.setBounds(10,70,40,20);
        numericSpeed.setBounds(55,10,80,20);
        numericWeight.setBounds(55,40,80,20);
        numericWheelNum.setBounds(55,70,80,20);
        checkBoxBodyKit.setBounds(10,100,120,20);
        checkBoxCaterpillar.setBounds(10,130,120,20);
        checkBoxTower.setBounds(10,160,120,20);
        for (int i = 0; i < colorPanels.length; i+=2) {
            colorPanels[i].setBounds(10,200+i/2*60,50,50);
            colorPanels[i+1].setBounds(70,200+i/2*60,50,50);
        }
        componentSuspensionOrnament.setBounds(10,470,25,20);
        componentAsteriskOrnament.setBounds(50,470,20,20);
        componentWheelCombination.setBounds(90,470,25,20);
        labelArmoVehicle.setBounds(10,600 ,80,30);
        labelTank.setBounds(100,600 ,80,30);

        labelColor.setBounds(WindowWidth-CanvasWidth, 10, CanvasWidth/3, 30);
        labelAdditionalColor.setBounds(WindowWidth-CanvasWidth + CanvasWidth/3, 10, CanvasWidth/3, 30);
        labelWheel.setBounds(WindowWidth-CanvasWidth + CanvasWidth*2/3, 10, CanvasWidth/3, 30);
        canvas.setBounds(WindowWidth-CanvasWidth, 50, CanvasWidth, CanvasHeight);
        buttonAdd.setBounds(WindowWidth-CanvasWidth, CanvasHeight+60, CanvasWidth/3, 30);
        buttonCancel.setBounds(WindowWidth-CanvasWidth + CanvasWidth*2/3, CanvasHeight+60, CanvasWidth/3, 30);

        Frame = new JFrame();
        Frame.setSize (WindowWidth+20, WindowHeight+40);
        Frame.setLayout(null);
        Frame.add(labelSpeed);
        Frame.add(labelWeight);
        Frame.add(labelWheelNum);
        Frame.add(numericSpeed);
        Frame.add(numericWeight);
        Frame.add(numericWheelNum);
        Frame.add(checkBoxBodyKit);
        Frame.add(checkBoxCaterpillar);
        Frame.add(checkBoxTower);
        for (var it : colorPanels)
            Frame.add(it);
        Frame.add(labelArmoVehicle);
        Frame.add(labelTank);
        Frame.add(labelColor);
        Frame.add(labelAdditionalColor);
        Frame.add(labelWheel);
        Frame.add(canvas);
        Frame.add(buttonAdd);
        Frame.add(buttonCancel);
        Frame.add(componentSuspensionOrnament);
        Frame.add(componentAsteriskOrnament);
        Frame.add(componentWheelCombination);

        Frame.setVisible(true);

        buttonCancel.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        Frame.dispose();
                    }
                }
        );
    }
}