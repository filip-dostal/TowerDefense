package io.github.filipdostal.towerdefense;

import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Point2D;
import java.util.List;

public abstract class Enemy {
    private final EnemyType type;
    private double x;
    private double y;
    protected final double width, height;
    private final List<Point2D> waypoints;
    private int currentWaypointIndex = 0;
    private final double speed;
    private double velX = 0;
    private double velY = 0;

    public Enemy(double width, double height, EnemyType type,
                 List<Point2D> waypoints, double speed) {
        if (waypoints == null) {
            throw new IllegalArgumentException("waypoints must not be null");
        }
        if (speed < 0) {
            throw new IllegalArgumentException("speed must not be negative");
        }
        this.width = width;
        this.height = height;
        Point2D start = waypoints.getFirst();
        this.x = start.getX();
        this.y = start.getY();
        this.type = type;
        this.waypoints = List.copyOf(waypoints);
        this.speed = speed;

        calculateVelocity();
    }

    public abstract void render(GraphicsContext gc);

    protected final void drawBody(GraphicsContext gc) {
        double drawX = x - (width / 2);
        double drawY = y - (height / 2);
        gc.fillRect(drawX, drawY, width, height);
    }

    protected final void move() {
        if (currentWaypointIndex >= waypoints.size()) {
            return;
        }

        Point2D target = waypoints.get(currentWaypointIndex);

        double deltaX = target.getX() - x;
        double deltaY = target.getY() - y;

        if (Math.hypot(deltaX, deltaY) <= speed) {
            x = target.getX();
            y = target.getY();

            currentWaypointIndex++;
            calculateVelocity();

            return;
        }

        x += velX;
        y += velY;
    }

    protected final void calculateVelocity() {
        if (currentWaypointIndex >= waypoints.size()) {
            velX = 0;
            velY = 0;
            return;
        }

        Point2D target = waypoints.get(currentWaypointIndex);
        double deltaX = target.getX() - x;
        double deltaY = target.getY() - y;
        double distance = Math.hypot(deltaX, deltaY);

        if (distance > 0) {
            velX = deltaX / distance * speed;
            velY = deltaY / distance * speed;
        }
    }
}
