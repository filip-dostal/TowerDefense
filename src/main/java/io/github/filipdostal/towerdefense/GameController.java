package io.github.filipdostal.towerdefense;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class GameController {

    @FXML private BorderPane mainContainer;
    @FXML private Label moneyLabel;

    private Canvas canvas;
    private GraphicsContext gc;
    private Image mapImage;
    private GameEngine gameEngine;

    @FXML
    public void initialize() {
        gameEngine = new GameEngine(this);
        canvas = new Canvas(GameConfig.CANVAS_WIDTH, GameConfig.CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        mainContainer.setCenter(canvas);
        mapImage = new Image(getClass().getResourceAsStream(
                "/io/github/filipdostal/towerdefense/png/map.png"));

        canvas.setOnMouseClicked(event -> {
            if (gameEngine.placeTower(event.getX(), event.getY())) {
                updateMoneyLabel();
            }
            event.consume();
        });

        updateMoneyLabel();
        gameEngine.startGame();
    }

    protected void renderGame() {
        gc.drawImage(mapImage, 0, 0, GameConfig.CANVAS_WIDTH, GameConfig.CANVAS_HEIGHT);
        for (Tower tower : gameEngine.getPlacedTowers()) {
            tower.render(gc);
        }
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("Money: " + gameEngine.getPlayerMoney());
    }

    @FXML
    private void buyTower() {
        gameEngine.buyTower(TowerType.DART);
    }
}