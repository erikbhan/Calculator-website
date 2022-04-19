package edu.ntnu.bidata.fullstack.calculator.model;

public class Operations {
    public static double calculate(double firstNumber, double secondNumber, String operator) {
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "-": 
                return firstNumber - secondNumber;
            case "*":
                return firstNumber * secondNumber;
            case "/":
                return firstNumber / secondNumber;
        }
        return secondNumber;
    }
}