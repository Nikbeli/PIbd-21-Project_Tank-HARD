public class MoveToCenter extends AbstractStrategy {
    protected boolean IsTargetDestination() {
        var objParams = GetObjectParameters();
        if (objParams == null) {
            return false;
        }
        return
                Math.abs(objParams.ObjectMiddleHorizontal() - FieldWidth / 2) <= GetStep()
                &&
                Math.abs(objParams.ObjectMiddleVertical() - FieldHeight / 2) <= GetStep();
    }

    protected void MoveToTarget() {
        var objParams = GetObjectParameters();
        if (objParams == null) {
            return;
        }
        var diffX = objParams.ObjectMiddleHorizontal() - FieldWidth / 2;
        if (Math.abs(diffX) > GetStep()) {
            if (diffX > 0) {
                MoveLeft();
            } else {
                MoveRight();
            }
        }
        var diffY = objParams.ObjectMiddleVertical() - FieldHeight / 2;
        if (Math.abs(diffY) > GetStep()) {
            if (diffY > 0) {
                MoveUp();
            } else {
                MoveDown();
            }
        }
    }
}