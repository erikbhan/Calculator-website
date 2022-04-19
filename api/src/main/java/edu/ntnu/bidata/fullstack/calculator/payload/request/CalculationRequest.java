package edu.ntnu.bidata.fullstack.calculator.payload.request;

import javax.validation.constraints.NotBlank;

public class CalculationRequest {

    @NotBlank
    private double firstNumber;

    @NotBlank
    private double secondNumber;

    @NotBlank
    private String operator;

    public double getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(double firstNumber) {
        this.firstNumber = firstNumber;
    }

    public double getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(double secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
