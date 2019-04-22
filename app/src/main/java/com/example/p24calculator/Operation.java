package com.example.p24calculator;

import java.io.Serializable;

public class Operation implements Serializable {

    StringBuilder operation;
    int rightAnswer;
    int answer;
    String howHard;
    int elapsedTime;

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public StringBuilder getOperation() {
        return operation;
    }

    public void setOperation(StringBuilder operation) {
        this.operation = operation;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public Operation(StringBuilder operation, int rightAnswer, int answer, int elapsedTime) {
        this.operation = operation;
        this.rightAnswer = rightAnswer;
        this.answer = answer;
        this.howHard = MainActivity.diff;
        this.elapsedTime = elapsedTime;
    }

    @Override
    public String toString() {
        String Operation = "Operation: " + operation + "=" + answer;
        if (rightAnswer == answer) {
            Operation += "\nYou have the right answer!";
        }
        else {
            Operation += "\nYou had the wrong answer! The right answer is: " + rightAnswer;
        }
        if (elapsedTime == 10 && rightAnswer != answer) {
            Operation += "\nYou did not answer the question during the time frame.";
        }
        else {
            Operation += "\nIt took you: " + elapsedTime + "s to provide an answer to this question.";
        }
        Operation += "\nIt was a question level: " + howHard + ".";
        return Operation;
    }
}
