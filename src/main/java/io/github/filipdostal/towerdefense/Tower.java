package io.github.filipdostal.towerdefense;

import javafx.scene.canvas.GraphicsContext;

public abstract class Tower {
    protected final TowerType type;
    protected final double centerX;
    protected final double centerY;
    protected final double width;
    protected final double height;

    public Tower(TowerType type, double centerX, double centerY, double width, double height) {
        this.type = type;
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
    }

    public abstract void render(GraphicsContext gc);

    protected final void drawBody(GraphicsContext gc) {
        double drawX = centerX - (width / 2);
        double drawY = centerY - (height / 2);
        gc.fillRect(drawX, drawY, width, height);
    }
}
