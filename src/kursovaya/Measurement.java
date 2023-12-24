package kursovaya;

import java.awt.Point;

public class Measurement {
    private Point startPoint;
    private Point endPoint;
    private double scalingFactor; // Коэффициент масштабирования (пиксели в миллиметры)

    public Measurement(Point startPoint, Point endPoint, double scalingFactor) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.scalingFactor = scalingFactor;
    }

    public double getLengthInPixels() {
        return startPoint.distance(endPoint);
    }

    public double getLengthInMillimeters() {
        return getLengthInPixels() * scalingFactor;
    }
}
