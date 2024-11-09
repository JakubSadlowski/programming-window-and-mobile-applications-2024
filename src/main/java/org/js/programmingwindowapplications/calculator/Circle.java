package org.js.programmingwindowapplications.calculator;

public class Circle extends Figure{
    private final double radius;

    public Circle(double radius){
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius can't be zero or negative");
        }

        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void print() {
        System.out.println("Circle's radius: " + radius);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter() + "\n");
    }
}
