package code.controller; //com.javarush.task.task34.task3410.

import code.model.Direction;
import code.model.GameObjects;
import code.model.Model;
import code.view.View;

public class Controller implements EventListener {
    private final Model model;
    private final View view;

    public Controller() {
        this.model = new Model();
        this.view = new View(this);
        view.init();
        model.restart();
        model.setEventListener(this);
        view.setEventListener(this);
    }

    @Override
    public void move(Direction direction) {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart() {
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel() {
        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level) {
        view.completed(level);
    }

    public GameObjects getGameObjects() {
        return this.model.getGameObjects();
    }
}
