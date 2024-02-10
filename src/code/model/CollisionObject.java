package code.model;

public abstract class CollisionObject extends GameObject {
    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        int tempX = this.getX();
        int tempY = this.getY();

        switch (direction) {
            case UP:
                tempY -= Model.FIELD_CELL_SIZE;
                break;
            case RIGHT:
                tempX += Model.FIELD_CELL_SIZE;
                break;
            case DOWN:
                tempY += Model.FIELD_CELL_SIZE;
                break;
            case LEFT:
                tempX -= Model.FIELD_CELL_SIZE;
                break;
        }

        return gameObject.getX() == tempX && gameObject.getY() == tempY;
    }
}
