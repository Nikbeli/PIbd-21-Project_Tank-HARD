public interface IMoveableObject {
    // Получение координаты X объекта
    ObjectParameters GetObjectPosition();

    // Шаг объекта
    int GetStep();

    // Проверка, можно ли переместиться по нужному направлению
    boolean CheckCanMove(Direction direction);

    /// Изменение направления пермещения объекта
    void MoveObject(Direction direction);
}