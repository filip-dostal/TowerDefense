package io.github.filipdostal.towerdefense;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final GameController controller;
    private final List<Tower> placedTowers = new ArrayList<>();
    private TowerType towerToPlace = null;
    private int playerMoney = GameConfig.STARTING_MONEY;

    public GameEngine(GameController controller) {
        this.controller = controller;
    }

    public void startGame() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.renderGame();
            }
        };

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
}
