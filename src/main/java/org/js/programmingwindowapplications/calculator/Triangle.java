package org.js.programmingwindowapplications.calculator;

public class Triangle extends Figure{
    private final double a, b, c;

    public Triangle(double a, double b, double c) {
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("The sum of two sides must be greater than the third.");
        }

        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    double calculateArea() {
        return 0.25 * Math.sqrt(4 * Math.pow(a, 2) * Math.pow(b, 2) - Math.pow((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)), 2));
    }

    @Override
    double calculatePerimeter() {
        return a + b + c;
    }

    @Override
    public void print() {
        System.out.println("Triangle's sides: a = " + a + ", b = " + b + ", c = " + c);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter() + "\n");
    }
}
