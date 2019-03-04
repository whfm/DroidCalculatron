package com.example.p24calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements Serializable, View.OnClickListener {
    TextView textView;
    Button goBack, clickedButton;

    public double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Displaying all results");
        int rightAnswers = 0;
        int wrongAnswers = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        textView = findViewById(R.id.txtResult);
        textView.setMovementMethod(new ScrollingMovementMethod());

        goBack = findViewById(R.id.buttonGoBack);
        goBack.setOnClickListener(this);

        ArrayList<Operation> myList = (ArrayList<Operation>) getIntent().getSerializableExtra("result");

        if (myList.size() > 0) {
            for(Operation aOp : myList){
                if (aOp.getAnswer() == aOp.getRightAnswer()) { rightAnswers++; }
                else { wrongAnswers++; }
                textView.append(aOp.toString());
                textView.append("\n\n");
            }

            double pRight = calculatePercentage(rightAnswers, wrongAnswers+rightAnswers);
            double pWrong = calculatePercentage(wrongAnswers, wrongAnswers+rightAnswers);
            textView.append("\nTotal right answers: " + rightAnswers + " (" + pRight + "%)");
            textView.append("\nTotal wrong answers: " + wrongAnswers + " (" + pWrong + "%)");

        }
        else {
            textView.setText("There is no result to display!");
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
