public class DrawingObjectTank implements IMoveableObject {
    private DrawingArmoVehicle _drawingArmoVehicle = null;
    public DrawingObjectTank(DrawingArmoVehicle drawingTank)
    {
        _drawingArmoVehicle = drawingTank;
    }
    public ObjectParameters GetObjectPosition() {
        if (_drawingArmoVehicle == null || _drawingArmoVehicle.ArmoVehicle == null) {
            return null;
        }
        return new ObjectParameters(_drawingArmoVehicle.GetPosX(),_drawingArmoVehicle.GetPosY(),
                _drawingArmoVehicle.GetWidth(), _drawingArmoVehicle.GetHeight());
    }
    public int GetStep(){ return (int)_drawingArmoVehicle.ArmoVehicle.Step; }
    public boolean CheckCanMove(Direction direction) { return _drawingArmoVehicle.CanMove(direction);}
    public void MoveObject(Direction direction) { _drawingArmoVehicle.MoveTransport(direction); }
}