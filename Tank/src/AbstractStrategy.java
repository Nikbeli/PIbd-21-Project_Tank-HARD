public abstract class AbstractStrategy {
    // Перемещаемый объект
    private IMoveableObject _moveableObject;

    // Статус перемещения
    private Status _state = Status.NotInit;

    // Ширина поля
    protected int FieldWidth;

    // Высота поля
    protected int FieldHeight;

    // Статус перемещения
    public Status GetStatus() {
        return _state;
    }

    // Установка данных
    public void SetData(IMoveableObject moveableObject, int width, int height) {
        if (moveableObject == null) {
            _state = Status.NotInit;
            return;
        }
        _state = Status.InProgress;
        _moveableObject = moveableObject;
        FieldWidth = width;
        FieldHeight = height;
    }

    // Шаг перемещения
    public void MakeStep() {
        if (_state != Status.InProgress) {
            return;
        }
        if (IsTargetDestination()) {
            _state = Status.Finish;
            return;
        }
        MoveToTarget();
    }

    // Перемещение влево
    protected boolean MoveLeft() {
        return MoveTo(Direction.Left);
    }

    // Перемещение вправо
    protected boolean MoveRight() {
        return MoveTo(Direction.Right);
    }

    // Перемещение вверх
    protected boolean MoveUp() {
        return MoveTo(Direction.Up);
    }

    // Перемещение вниз
    protected boolean MoveDown() {
        return MoveTo(Direction.Down);
    }

    // Параметры объекта
    protected ObjectParameters GetObjectParameters() {
        return _moveableObject.GetObjectPosition();
    }

    // Шаг объекта
    protected int GetStep() {
        if (_state != Status.InProgress) {
            return 0;
        }
        return _moveableObject.GetStep();
    }

    // Перемещение к цели
    protected abstract void MoveToTarget();

    // Достигнута ли цель
    protected abstract boolean IsTargetDestination();

    // Попытка перемещения в требуемом направлении
    private boolean MoveTo(Direction Direction) {
        if (_state != Status.InProgress) {
            return false;
        }
        if (_moveableObject.CheckCanMove(Direction)) {
            _moveableObject.MoveObject(Direction);
            return true;
        }
        return false;
    }
}