package com.example.p24calculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    TextView editCalc, generated, txtTimer;
    Button clickedButton, backSpace, clear, quit, equal, generate, showAll, minus, difficulty;

    ArrayList<Operation> myArray;
    StringBuilder ab = new StringBuilder();

    boolean calcMade;

    int rightAnswers = 0;
    int wrongAnswers = 0;

    int elapsedTime = 0;
    int totalTime = 0;

    public static String diff = "easy";

    int num1 = 0;
    int num2 = 0;
    int operator = 0;

    Button[] listOfButtons = new Button[10];
    int listOfWidgets[] = { R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 };

    CountDownTimer myTimer;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private void addDrawerItems() {

        ArrayList<String> osArray = new ArrayList<String>();


        if (diff == "easy") {
            osArray.add("\u2713 Easy");
            osArray.add("Medium");
            osArray.add("Hard");
        }
        else if (diff == "medium") {
            osArray.add("Easy");
            osArray.add("\u2713 Medium");
            osArray.add("Hard");
        }
        else {
            osArray.add("Easy");
            osArray.add("Medium");
            osArray.add("\u2713 Hard");
        }

        osArray.add("-----------------");
        osArray.add("Save Progress");
        osArray.add("Load Progress");
        osArray.add("Erase Database");

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean change = false;
                if (position == 0) {
                    change = true;
                    diff = "easy";
                }
                else if (position == 1) {
                    change = true;
                    diff = "medium";
                }
                else if (position == 2) {
                    change = true;
                    diff = "hard";
                }
                else if (position == 4) {
                    AlertDialog show = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Save")
                            .setMessage("Do you really want to save your progress?\nThis will overwrite the file")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    try {
                                        FileHandler.writeToFile(myArray);
                                        Toast.makeText(MainActivity.this, "Progress saved!", Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        Toast.makeText(MainActivity.this, "There was an error: " + e, Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
                else if (position == 5) {

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Load")
                            .setMessage("Do you really want to load your progress?\nAny unsaved info will be lost!")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    try {
                                        myArray = FileHandler.readFromFile(myArray);
                                        Toast.makeText(MainActivity.this, "Progress loaded!", Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        Toast.makeText(MainActivity.this, "There was an error: " + e, Toast.LENGTH_LONG).show();
                                    } catch (ClassNotFoundException e) {
                                        Toast.makeText(MainActivity.this, "There was an error: " + e, Toast.LENGTH_LONG).show();
                                    }
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }

                else if (position == 6) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Erase Database")
                            .setMessage("Do you really want to erase the database?\nIt can not be undone!")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    try {
                                        FileHandler.eraseFile();
                                        Toast.makeText(MainActivity.this, "Progress erased!", Toast.LENGTH_LONG).show();
                                        myArray = new ArrayList<>();
                                    } catch (IOException e) {
                                        Toast.makeText(MainActivity.this, "There was an error: " + e, Toast.LENGTH_LONG).show();
                                    }
                                }})
                            .setNegativeButton(android.R.string.no, null).show();

                }
                if (change) {
                    Toast.makeText(MainActivity.this, "Difficult set to: " + diff, Toast.LENGTH_LONG).show();
                    calcMade = false;
                    editCalc.setText(null);
                    generated.setText("Click generate to generate a new math expression!");
                }
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

    }



    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                addDrawerItems();
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Options Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerClosed(View view) {
                addDrawerItems();
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Calculatron!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();


        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initialize();

        myTimer = new CountDownTimer(11000,1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                myTimer.cancel();
                elapsedTime = 10-Integer.parseInt(txtTimer.getText().toString());
                if (calcMade && !TextUtils.isEmpty(editCalc.getText())) {
                    txtTimer.setText("Timer stopped!");
                    switch (operator) {
                        case 0:
                            int result = num1 + num2;
                            if (result == Integer.parseInt(editCalc.getText().toString())) {
                                rightAnswers++;
                                Operation o1 = new Operation(ab, result, result, elapsedTime);
                                myArray.add(o1);
                                Toast.makeText(MainActivity.this, "You got it right!", Toast.LENGTH_LONG).show();
                                generated.setText("You got it right! Click generate to generate a new math expression!");
                                break;
                            } else {
                                wrongAnswers++;
                                Operation o1 = new Operation(ab, result, Integer.parseInt(editCalc.getText().toString()), elapsedTime);
                                myArray.add(o1);
                                Toast.makeText(MainActivity.this, "How unfortunately! Try again!", Toast.LENGTH_LONG).show();
                                generated.setText("How unfortunately! Try again! Click generate to generate a new math expression!");
                            }
                            break;
                        case 1:
                            result = num1 - num2;
                            if (result == Integer.parseInt(editCalc.getText().toString())) {
                                rightAnswers++;
                                Operation o1 = new Operation(ab, result, result, elapsedTime);
                                myArray.add(o1);
                                Toast.makeText(MainActivity.this, "You got it right!", Toast.LENGTH_LONG).show();
                                generated.setText("You got it right! Click generate to generate a new math expression!");
                                break;
                            } else {
                                wrongAnswers++;
                                Operation o1 = new Operation(ab, result, Integer.parseInt(editCalc.getText().toString()), elapsedTime);
                                myArray.add(o1);
                                Toast.makeText(MainActivity.this, "How unfortunately! Try again!", Toast.LENGTH_LONG).show();
                                generated.setText("How unfortunately! Try again! Click generate to generate a new math expression!");
                            }
                            break;
                        case 2:
                            result = num1 * num2;
                            if (result == Integer.parseInt(editCalc.getText().toString())) {
                                rightAnswers++;
                                Operation o1 = new Operation(ab, result, result, elapsedTime);
                                myArray.add(o1);
                                Toast.makeText(MainActivity.this, "You got it right!", Toast.LENGTH_LONG).show();
                                generated.setText("You got it right! Click generate to generate a new math expression!");
                                break;
                            } else {
                                wrongAnswers++;
                                Operation o1 = new Operation(ab, result, Integer.parseInt(editCalc.getText().toString()), elapsedTime);
                                myArray.add(o1);
                                Toast.makeText(MainActivity.this, "How unfortunately! Try again!", Toast.LENGTH_LONG).show();
                                generated.setText("How unfortunately! Try again! Click generate to generate a new math expression!");
                            }
                            break;
                        default:
                            break;
                    }
                }
                else {
                    switch (operator) {
                        case 0: {
                            int result = num1 + num2;
                            wrongAnswers++;
                            Operation o1 = new Operation(ab, result, 0, elapsedTime);
                            myArray.add(o1);
                            Toast.makeText(MainActivity.this, "Time up! How unfortunately! Try again!", Toast.LENGTH_LONG).show();
                            txtTimer.setText("How unfortunately! Try again! Click generate to generate a new math expression!");
                            break;
                        }
                        case 1: {
                            int result = num1 - num2;
                            wrongAnswers++;
                            Operation o1 = new Operation(ab, result, 0, elapsedTime);
                            myArray.add(o1);
                            Toast.makeText(MainActivity.this, "Time up! How unfortunately! Try again!", Toast.LENGTH_LONG).show();
                            txtTimer.setText("How unfortunately! Try again! Click generate to generate a new math expression!");
                            break;
                        }
                        case 2: {
                            int result = num1 * num2;
                            wrongAnswers++;
                            Operation o1 = new Operation(ab, result, 0, elapsedTime);
                            myArray.add(o1);
                            Toast.makeText(MainActivity.this, "Time up! How unfortunately! Try again!", Toast.LENGTH_LONG).show();
                            txtTimer.setText("How unfortunately! Try again! Click generate to generate a new math expression!");
                            }
                            break;
                        default:
                            break;
                    }
                }
                calcMade = false;
                editCalc.setText(null);
                generated.setText("Click generate to generate a new math expression!");

            }
        };
    }

    private void initialize() {

        myArray = new ArrayList<>();
        editCalc = findViewById(R.id.editCalc);
        generated = findViewById(R.id.txtGenerated);
        backSpace = findViewById(R.id.buttonBack); backSpace.setOnClickListener(this);
        clear = findViewById(R.id.buttonClear); clear.setOnClickListener(this);
        quit = findViewById(R.id.buttonQuit); quit.setOnClickListener(this);
        equal = findViewById(R.id.buttonEqual); equal.setOnClickListener(this);
        generate = findViewById(R.id.btnGenerate); generate.setOnClickListener(this);
        showAll = findViewById(R.id.buttonShowAll); showAll.setOnClickListener(this);
        minus = findViewById(R.id.buttonMinus); minus.setOnClickListener(this);
        difficulty = findViewById(R.id.btnDifficulty); difficulty.setOnClickListener(this);

        txtTimer = findViewById(R.id.txtTimer);

        generated.setText("Click on generate to begin!");
        for (int i=0;i<listOfWidgets.length;i++){
            listOfButtons[i]=findViewById(listOfWidgets[i]);
            listOfButtons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        clickedButton = (Button)v;

        //Toast.makeText(this, "This is my Toast message!", Toast.LENGTH_LONG).show();

        switch (v.getId()) {
            case R.id.button0:
                editCalc.setText(editCalc.getText() + "0");
                break;
            case R.id.button1:
                editCalc.setText(editCalc.getText() + "1");
                break;
            case R.id.button2:
                editCalc.setText(editCalc.getText() + "2");
                break;
            case R.id.button3:
                editCalc.setText(editCalc.getText() + "3");
                break;
            case R.id.button4:
                editCalc.setText(editCalc.getText() + "4");
                break;
            case R.id.button5:
                editCalc.setText(editCalc.getText() + "5");
                break;
            case R.id.button6:
                editCalc.setText(editCalc.getText() + "6");
                break;
            case R.id.button7:
                editCalc.setText(editCalc.getText() + "7");
                break;
            case R.id.button8:
                editCalc.setText(editCalc.getText() + "8");
                break;
            case R.id.button9:
                editCalc.setText(editCalc.getText() + "9");
                break;

            case R.id.buttonMinus:
                editCalc.setText(editCalc.getText() + "-");
                break;

            case R.id.buttonClear:
                editCalc.setText("");
                break;
            case R.id.buttonQuit:
                onBackPressed();
                break;

            case R.id.btnDifficulty:
                if (diff == "easy") {
                    diff = "medium";
                }
                else if (diff == "medium") {
                    diff = "hard";
                }
                else {
                    diff = "easy";
                }
                Toast.makeText(this,"Difficult set to: " + diff,Toast.LENGTH_LONG).show();
                calcMade = false;
                editCalc.setText(null);
                generated.setText("Click generate to generate a new math expression!");
                addDrawerItems();
                break;

            case R.id.btnGenerate:
                myTimer.start();
                calcMade = true;
                num1 = Operator.GenerateNum();
                num2 = Operator.GenerateNum();
                operator = Operator.GenerateOperand();

                ab = new StringBuilder();

                ab.append(num1);
                String operand = "";
                switch (operator)
                {
                    case 0:
                        operand = "+";
                        break;
                    case 1:
                        operand = "-";
                        break;
                    case 2:
                        operand = "*";
                        break;
                }
                ab.append(operand);

                ab.append(num2);

                generated.setText(ab.toString());
                break;

            case R.id.buttonBack:
                String txt = editCalc.getText().toString();
                txt = txt.length() > 0 ? txt.substring(0, txt.length() - 1) : "";
                editCalc.setText(txt);
                break;


            case R.id.buttonShowAll:

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("result", myArray);
                startActivity(intent);

                break;

            case R.id.buttonEqual:
                myTimer.onFinish();
                break;

            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
