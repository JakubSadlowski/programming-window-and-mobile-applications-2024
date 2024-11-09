package org.js.programmingwindowapplications.calculator;

public class Rectangle extends Figure{
    private final double width, height;

    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Sides can't be zero or negative");
        }

        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    double calculateArea() {
        return width * height;
    }

    @Override
    double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void print() {
        System.out.println("Rectangle's width: " + width + ", height: " + height);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter() + "\n");
    }
}
