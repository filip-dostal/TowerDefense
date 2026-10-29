package io.github.filipdostal.towerdefense;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;
import java.util.List;

public class Zombie extends Enemy {

    public Zombie(List<Point2D> waypoints, double speed) {
        super(40, 40, EnemyType.Zombie, waypoints, speed);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        drawBody(gc);
    }
}
