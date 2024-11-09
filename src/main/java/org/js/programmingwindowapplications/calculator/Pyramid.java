package org.js.programmingwindowapplications.calculator;

public class Pyramid implements Printable{

    private final Figure base;
    private final double height;

    public Pyramid(Figure base, double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height can't be zero or negative");
        }

        this.base = base;
        this.height = height;
    }

    double calculateSurfaceArea() {
        if(base instanceof Rectangle){
            double slantHeightA = Math.sqrt((((Rectangle) base).getHeight()/2) * (((Rectangle) base).getHeight()/2) + (height * height));
            double slantHeightB = Math.sqrt((((Rectangle) base).getWidth()/2) * (((Rectangle) base).getWidth()/2) + (height * height));
            return base.calculateArea() + ((Rectangle) base).getHeight() * slantHeightA + ((Rectangle) base).getWidth() * slantHeightB;

        }else if(base instanceof Circle){
            double radius = ((Circle) base).getRadius();
            double l = Math.sqrt(Math.pow(radius, 2) + Math.pow(height, 2));
            return Math.PI * radius * (radius + l);
        }else if(base instanceof Triangle){
            return 0;
        }
        return -1;
    }

    double calculateVolume() {
        return (1.0 / 3.0) * base.calculateArea() * height;
    }

    @Override
    public void print() {
        System.out.println("Pyramid's Height: " + height);
        System.out.println("\nPyramid's base");
        base.print();
        System.out.println("Pyramid's Volume: " + calculateVolume());
        System.out.println("Pyramid's Surface Area: " + calculateSurfaceArea() + "\n");
    }
}
