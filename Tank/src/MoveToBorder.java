public class MoveToBorder extends AbstractStrategy {
    protected boolean IsTargetDestination() {
        var objParams = GetObjectParameters();
        if (objParams == null) {
            return false;
        }
        return objParams.RightBorder() <= FieldWidth &&
            objParams.RightBorder() + GetStep() >= FieldWidth &&
            objParams.DownBorder() <= FieldHeight &&
            objParams.DownBorder() + GetStep() >= FieldHeight;
    }
    protected void MoveToTarget() {
        var objParams = GetObjectParameters();
        if (objParams == null) {
            return;
        }
        var diffX = objParams.RightBorder() - FieldWidth;
        if (Math.abs(diffX) > GetStep()) {
            if (diffX > 0) {
                MoveLeft();
            }
            else {
                MoveRight();
            }
        }
        var diffY = objParams.DownBorder() - FieldHeight;
        if (Math.abs(diffY) > GetStep()) {
            if (diffY > 0) {
                MoveUp();
            }
            else {
                MoveDown();
            }
        }
    }
}