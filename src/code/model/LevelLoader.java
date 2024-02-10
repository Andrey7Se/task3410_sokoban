package code.model;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelLoader {
    private final Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        int correctLevel = getCorrectLevel(level);
        List<String> stringsOfLevel = getStringsOfLevel(correctLevel);
        List<String> stringsOfOnlyMarkup = getStringsOfOnlyMarkup(stringsOfLevel);

        return setCoordForAllObjects(stringsOfOnlyMarkup);
    }

    public int getCorrectLevel(int level) {
        if (level < 1) {
            return 1;
        }

        return level > 60 ? level % 60 : level;
    }

    public List<String> getStringsOfLevel(int level) {
        List<String> allStrings = new ArrayList<>();

        //загрузка уровней из mock-класса Levels из строки
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(Levels.text.getBytes())))) {
            boolean isPrint = false;
            String inputLine = "";

            while ((inputLine = reader.readLine()) != null) {
                if (inputLine.matches("Maze: " + level)) {
                    isPrint = true;
                }
                if (inputLine.contains("********")) {
                    isPrint = false;
                }
                if (isPrint) {
                    allStrings.add(inputLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        allStrings.remove(allStrings.size() - 1);

        return allStrings;
    }

    public static List<String> getStringsOfOnlyMarkup(List<String> allStrings) {
        List<String> result = new ArrayList<>();
        for (int i = 7; i < allStrings.size(); i++) {
            result.add(allStrings.get(i));
        }

        return result;
    }

    public static GameObjects setCoordForAllObjects(List<String> markupStrings) {
        int x = 0;// Model.FIELD_CELL_SIZE / 2;
        int y = 0;// Model.FIELD_CELL_SIZE / 2;

        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = new Player(0, 0);

        for (String line : markupStrings) {
            for (char ch : line.toCharArray()) {
                switch (ch) {
                    case 'X':
                        walls.add(new Wall(x, y));
                        break;
                    case '*':
                        boxes.add(new Box(x, y));
                        break;
                    case '.':
                        homes.add(new Home(x, y));
                        break;
                    case '&':
                        boxes.add(new Box(x, y));
                        homes.add(new Home(x, y));
                        break;
                    case '@':
                        player = new Player(x, y);
                        break;
                }

                x += Model.FIELD_CELL_SIZE;
            }

            x = 0;// Model.FIELD_CELL_SIZE / 2;
            y += Model.FIELD_CELL_SIZE;
        }

        return new GameObjects(walls, boxes, homes, player);
    }
}
