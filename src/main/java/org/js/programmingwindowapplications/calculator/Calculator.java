package org.js.programmingwindowapplications.calculator;

import java.util.Scanner;

public class Calculator {
    private final Scanner scanner = new Scanner(System.in);

    public void startCalculator() {
        boolean running = true;

        while (running) {
            System.out.println("Choose figure or click 5 to exit calculator:");
            System.out.println("1. Triangle");
            System.out.println("2. Rectangle");
            System.out.println("3. Circle");
            System.out.println("4. Pyramid");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleTriangle();
                    break;
                case 2:
                    handleRectangle();
                    break;
                case 3:
                    handleCircle();
                    break;
                case 4:
                    handlePyramid();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Wrong input, choose number between 1 and 5.");
            }
        }
    }

    private void handleTriangle() {
        System.out.println("Type triangle's sides:");
        System.out.print("a = ");
        double a = scanner.nextDouble();
        System.out.print("b = ");
        double b = scanner.nextDouble();
        System.out.print("c = ");
        double c = scanner.nextDouble();

        try {
            Triangle triangle = new Triangle(a, b, c);
            triangle.print();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleRectangle() {
        System.out.println("Type rectangle's sides:");
        System.out.print("width = ");
        double width = scanner.nextDouble();
        System.out.print("height = ");
        double height = scanner.nextDouble();

        try {
            Rectangle rectangle = new Rectangle(width, height);
            rectangle.print();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleCircle() {
        System.out.print("Type circle's radius = ");
        double radius = scanner.nextDouble();

        try {
            Circle circle = new Circle(radius);
            circle.print();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handlePyramid() {
        System.out.println("Choose pyramid's base:");
        System.out.println("1. Triangle");
        System.out.println("2. Rectangle");
        System.out.println("3. Circle");

        Figure base = null;
        int baseChoice = scanner.nextInt();

        switch (baseChoice) {
            case 1:
                System.out.println("Type triangle's sides:");
                System.out.print("a = ");
                double a = scanner.nextDouble();
                System.out.print("b = ");
                double b = scanner.nextDouble();
                System.out.print("c = ");
                double c = scanner.nextDouble();
                base = new Triangle(a, b, c);
                break;
            case 2:
                System.out.println("Type rectangle's sides:");
                System.out.print("width = ");
                double width = scanner.nextDouble();
                System.out.print("height = ");
                double height = scanner.nextDouble();
                base = new Rectangle(width, height);
                break;
            case 3:
                System.out.print("Type circle's radius = ");
                double radius = scanner.nextDouble();
                base = new Circle(radius);
                break;
            default:
                System.out.println("Wrong base choice.");
                return;
        }

        System.out.print("Type pyramid's height = ");
        double height = scanner.nextDouble();

        try {
            Pyramid pyramid = new Pyramid(base, height);
            pyramid.print();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
