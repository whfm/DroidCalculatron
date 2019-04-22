package com.example.p24calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements Serializable, View.OnClickListener {

    Button goBack, clickedButton;
    ListView listView;

    public double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Displaying all results");
        int rightAnswers = 0;
        int wrongAnswers = 0;

        double totalElapsedTime = 0;
        double totalTime = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listList);

        goBack = findViewById(R.id.buttonGoBack);
        goBack.setOnClickListener(this);

        ArrayList<Operation> myList = (ArrayList<Operation>) getIntent().getSerializableExtra("result");

        List<String> dataList = new ArrayList<String>();

        if (myList.size() > 0) {
            for(Operation aOp : myList){
                if (aOp.getAnswer() == aOp.getRightAnswer()) { rightAnswers++; }
                else { wrongAnswers++; }
                dataList.add(aOp.toString());
                totalElapsedTime += aOp.getElapsedTime();
                totalTime += 10;
            }
            double velocity = totalElapsedTime / totalTime;
            double pRight = calculatePercentage(rightAnswers, wrongAnswers+rightAnswers);
            double pWrong = calculatePercentage(wrongAnswers, wrongAnswers+rightAnswers);

            dataList.add(
                "Total right answers: " + rightAnswers + " (" + pRight + "%)" +
                "\nTotal wrong answers: " + wrongAnswers + " (" + pWrong + "%)" +
                "\nTotal elapsed time: " + totalElapsedTime + "s" +
                "\nTotal duration: " + totalTime + "s" +
                "\nVelocity: " + velocity
            );

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
            listView.setAdapter(arrayAdapter);

        }
        else {
            dataList.add("There is no result to display!");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
            listView.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        clickedButton = (Button) v;

        switch (v.getId()) {
            case R.id.buttonGoBack:
                finish();
            break;
        }
    }
}
