package io.github.filipdostal.towerdefense;

import javafx.animation.AnimationTimer;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameEngine {
    private final GameController controller;
    private final List<Tower> placedTowers = new ArrayList<>();
    private TowerType towerToPlace = null;
    private int playerMoney = GameConfig.STARTING_MONEY;
    private final List<Enemy> enemies = new ArrayList<>();
    private List<Point2D> waypoints = new ArrayList<>();
    char[][] grid;

    public GameEngine(GameController controller) {
        this.controller = controller;
        grid = createGrid();
        waypoints = loadWaypoints(grid);
    }

    public void startGame() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Enemy enemy : enemies) {
                    enemy.move();
                }

                controller.renderGame();
            }
        };



        Enemy zombie1 = new Zombie(waypoints, 1);
        enemies.add(zombie1);
        gameLoop.start();
    }

    public void buyTower(TowerType towerType) {
        if (playerMoney >= towerType.value) {
            towerToPlace = towerType;
        } else {
            System.out.println("not enough money");
        }
    }

    public boolean placeTower(double x, double y) {
        if (towerToPlace == null) {
            return false;
        }

        placedTowers.add(createTower(towerToPlace, x, y));
        playerMoney -= towerToPlace.value;
        towerToPlace = null;
        return true;
    }

    private Tower createTower(TowerType type, double x, double y) {
        return switch (type) {
            case TOWERTEST -> new TowerTest(x, y);
        };
    }

    public List<Tower> getPlacedTowers() {
        return List.copyOf(placedTowers);
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public List<Enemy> getEnemies() {
        return List.copyOf(enemies);
    }

    private char[][] createGrid() {
        char[][] grid = new char[24][32];

        try (BufferedReader br = new BufferedReader(new FileReader(GameConfig.MAP_GRID_PATH))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    grid[row][col] = line.charAt(col);
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return grid;
    }

    private List<Point2D> loadWaypoints(char[][] grid) {
        Point2D[] orderedPoints = new Point2D[6];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                char symbol = grid[row][col];

                int x = (int) ((col + 0.5) * GameConfig.TILE_WIDTH);
                int y = (int) ((row + 0.5) * GameConfig.TILE_HEIGHT);

                if (symbol == 's') {
                    orderedPoints[0] = new Point2D.Double(x, y);
                } else if (Character.isDigit(symbol) && symbol != '0' && symbol != '1') {
                    int index = Character.getNumericValue(symbol);
                    orderedPoints[index - 1] = new Point2D.Double(x, y);
                } else if (symbol == 'e') {
                    orderedPoints[5] =  new Point2D.Double(x, y);
                }
            }
        }

        return new ArrayList<>(Arrays.asList(orderedPoints));
    }
}
