package main.java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Line {
    private Point firstPoint;
    private Point secondPoint;
    private double length;
    private static GraphicsContext graphicsContext;

    static {
        graphicsContext = MainScene.getGraphicsContext();
    }

    Line(Point firstPoint, Point secondPoint){
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.length = Math.sqrt(Math.pow(secondPoint.getX() - firstPoint.getX(), 2) + Math.pow(secondPoint.getY() - firstPoint.getY(), 2));
    }

    public Point pointByT(double t){
        double xt = firstPoint.getX() + ((secondPoint.getX() - firstPoint.getX()) * t);
        double yt = firstPoint.getY() + ((secondPoint.getY() - firstPoint.getY()) * t);
        return new Point(xt,yt);
    }

    public void paint(Color color){
        graphicsContext.setStroke(color);
        graphicsContext.strokeLine(firstPoint.getX(),firstPoint.getY(),secondPoint.getX(),secondPoint.getY());
    }

    public void paint(Color color, Boolean isBlur){
        if (isBlur){
            BoxBlur blur = new BoxBlur();
            blur.setWidth(2);
            blur.setHeight(2);
            blur.setIterations(5);
            graphicsContext.setEffect(blur);
        }
        graphicsContext.setStroke(color);
        graphicsContext.strokeLine(firstPoint.getX(),firstPoint.getY(),secondPoint.getX(),secondPoint.getY());
        graphicsContext.setEffect(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.length, length) == 0 &&
                Objects.equals(firstPoint, line.firstPoint) &&
                Objects.equals(secondPoint, line.secondPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPoint, secondPoint, length);
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public double getLength() {
        return length;
    }
}
