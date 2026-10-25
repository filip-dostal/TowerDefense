package io.github.filipdostal.towerdefense;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TowerTest extends Tower {
    public TowerTest(double x, double y) {
        super(TowerType.TOWERTEST, x, y, 40, 40);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        drawBody(gc);
    }
}
