package code.view;

import code.controller.Controller;
import code.controller.EventListener;
import code.model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private Controller controller;
    private Field field;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        field = new Field(this);
        add(field);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);
    }

    public void setEventListener(EventListener eventListener) {
        field.setEventListener(eventListener);
    }

    public void update() {
        this.field.repaint();
    }

    public GameObjects getGameObjects() {
        return this.controller.getGameObjects();
    }

    public void completed(int level){
        update();
        JOptionPane.showMessageDialog(this, "Level #" + level + " completed!");
        controller.startNextLevel();
    }
}

