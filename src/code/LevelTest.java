package code;

import code.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelTest {
    public static void main(String[] args) {

//        getStringsOfOnlyMarkup(getStringsOfLevel(1)).forEach(System.out::println);
//        setCoordForAllObjects(getStringsOfOnlyMarkup(getStringsOfLevel(1)));
    }

    //возвращает корректный номер уровня 1..60
    public static int getCorrectLevel(int level) {
        if (level < 1) {
            return 1;
        }

        return level > 60 ? level % 60 : level;
    }

    //получает Лист всех текстовых строк по номеру уровня
    public static List<String> getStringsOfLevel(int level) {
        Path path = Paths.get("./src/code/res/levels.txt");

        List<String> allStrings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
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

    //получает Лист только строк с обозначение разметки уровня
    public static List<String> getStringsOfOnlyMarkup(List<String> allStrings) {
        List<String> result = new ArrayList<>();

        for (int i = 7; i < allStrings.size(); i++) {
            result.add(allStrings.get(i));
        }

        return result;
    }

    //присваивает координаты объектам по Листу их разметки
    public static GameObjects setCoordForAllObjects(List<String> markupStrings) {
        int x = Model.FIELD_CELL_SIZE / 2;
        int y = Model.FIELD_CELL_SIZE / 2;

        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;

        for (String line : markupStrings) {
            for (char ch : line.toCharArray()) {
                System.out.print(ch + " ");

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

            System.out.println();

            x = Model.FIELD_CELL_SIZE / 2;
            y += Model.FIELD_CELL_SIZE;
        }

        return new GameObjects(walls, boxes, homes, player);
    }
}
