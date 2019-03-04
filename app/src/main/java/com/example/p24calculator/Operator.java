package com.example.p24calculator;

import java.util.Random;

public class Operator {

    public static int GenerateOperand() {
        Random r = new Random();
        int randomOperand = r.nextInt(3);
        return randomOperand;
    }

    public static int GenerateNum()
    {
        Random r = new Random();
        int randomNumber = 0;
        if (MainActivity.diff == "easy") {
            randomNumber = r.nextInt(10);
        }
        else if (MainActivity.diff == "medium") {
            randomNumber = r.nextInt(100);
        }
        else {
            randomNumber = r.nextInt(1000);
        }
        return randomNumber;
    }
}
