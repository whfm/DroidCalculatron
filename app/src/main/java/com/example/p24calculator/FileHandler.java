package com.example.p24calculator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHandler {


    public static void writeToFile(ArrayList<Operation> myList) throws IOException {
        //write to binary file
        FileOutputStream   fos = new FileOutputStream("data/data/com.example.p24calculator/myProg.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(myList);
        oos.close();
    }

    public static void eraseFile() throws IOException {
        //erasesFile
        ArrayList<Operation> myList = new ArrayList<>();
        FileOutputStream   fos = new FileOutputStream("data/data/com.example.p24calculator/myProg.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(myList);
        oos.close();
    }


    @SuppressWarnings("unchecked")
    public static ArrayList<Operation> readFromFile(ArrayList<Operation> myProg) throws IOException, ClassNotFoundException  {
        //read from binary file
        FileInputStream fis = new FileInputStream("data/data/com.example.p24calculator/myProg.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);

        myProg = (ArrayList<Operation>) ois.readObject();
        ois.close();

        return myProg;
    }
}
