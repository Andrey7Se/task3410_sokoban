package code.model;

import java.util.HashSet;
import java.util.Set;

public class GameObjects {
    private final Set<Wall> walls;
    private final Set<Box> boxes;
    private final Set<Home> homes;
    private final Player player;

    public GameObjects(Set<Wall> walls, Set<Box> boxes, Set<Home> homes, Player player) {
        this.walls = walls;
        this.boxes = boxes;
        this.homes = homes;
        this.player = player;
    }

    public Set<Wall> getWalls() {
        return walls;
    }

    public Set<Box> getBoxes() {
        return boxes;
    }

    public Set<Home> getHomes() {
        return homes;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<GameObject> getAll() {
        Set<GameObject> allObj = new HashSet<>();
        allObj.addAll(walls);
        allObj.addAll(boxes);
        allObj.addAll(homes);
        allObj.add(player);

        return allObj;
    }
}
