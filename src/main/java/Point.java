package main.java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Point {
    private double x, y;
    private static GraphicsContext graphicsContext;

    static {
        graphicsContext = MainScene.getGraphicsContext();
    }

    Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void paint(Color color){
        graphicsContext.setFill(color);
        graphicsContext.fillOval(x,y,4,4);
    }

    public void delete(){
        graphicsContext.clearRect(x, y, 4, 4);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
