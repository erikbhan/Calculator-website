package edu.ntnu.bidata.fullstack.calculator.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "calculations")
public class Calculation {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private double firstNumber;

    @NotBlank
    private double secondNumber;
    
    @NotBlank
    private String operator;

    @NotBlank
    private double answer;

    public Calculation() {}

    public Calculation(double firstNumber, double secondNumber, String operator) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operator = operator;
        answer = Operations.calculate(firstNumber, secondNumber, operator);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
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

    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }
}
