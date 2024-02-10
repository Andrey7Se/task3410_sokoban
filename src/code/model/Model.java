package code.model;

import code.controller.EventListener;

import java.nio.file.Paths;
import java.util.Set;

public class Model {
    public static final int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private final LevelLoader levelLoader = new LevelLoader(Paths.get("./src/_code/res/levels.txt"));

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return this.gameObjects;
    }

    public void restartLevel(int level) {
        this.gameObjects = this.levelLoader.getLevel(level);
    }

    public void restart() {
        this.restartLevel(this.currentLevel);
    }

    public void startNextLevel() {
        this.currentLevel++;
        this.restartLevel(currentLevel);
    }

    public void move(Direction direction) {
        if (checkWallCollision(gameObjects.getPlayer(), direction)) {
            return;
        }
        if (checkBoxCollisionAndMoveIfAvailable(direction)) {
            return;
        }

        int tempX = 0;
        int tempY = 0;

        switch (direction) {
            case UP:
                tempY = -FIELD_CELL_SIZE;
                break;
            case RIGHT:
                tempX = FIELD_CELL_SIZE;
                break;
            case DOWN:
                tempY = FIELD_CELL_SIZE;
                break;
            case LEFT:
                tempX = -FIELD_CELL_SIZE;
                break;
        }

        gameObjects.getPlayer().move(tempX, tempY);

        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }

        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        Player player = gameObjects.getPlayer();
        Set<Box> boxes = gameObjects.getBoxes();

        for (Box box1 : boxes) {
            if (player.isCollision(box1, direction)) {
                for (Box box2 : boxes) {
                    if (box1.isCollision(box2, direction)) {
                        return true;
                    }
                }

                if (checkWallCollision(box1, direction)) {
                    return true;
                }

                int tempX = 0;
                int tempY = 0;

                switch (direction) {
                    case UP:
                        tempY = -FIELD_CELL_SIZE;
                        break;
                    case RIGHT:
                        tempX = FIELD_CELL_SIZE;
                        break;
                    case DOWN:
                        tempY = FIELD_CELL_SIZE;
                        break;
                    case LEFT:
                        tempX = -FIELD_CELL_SIZE;
                        break;
                }

                box1.move(tempX, tempY);
            }
        }

        return false;
    }

    public void checkCompletion() {
        int homeCount = gameObjects.getHomes().size();
        int collisionCont = 0;

        for (Home home : gameObjects.getHomes()) {
            for (Box box : gameObjects.getBoxes()) {
                if (home.getX() == box.getX() && home.getY() == box.getY()) {
                    collisionCont++;
                }
            }
        }

        if (homeCount == collisionCont) {
            eventListener.levelCompleted(currentLevel);
        }
    }
}
