package com.jiraffe.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView numberField;
    TextView resultField;

    private String currentNumber = "";
    private double previousNumber;
    private double resultNumber;

    private String currentAction;
    private static final String DIVIDE = "/";
    private static final String MULTIPLY = "*";
    private static final String MINUS = "-";
    private static final String PLUS = "+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberField = (TextView) findViewById(R.id.numberField);
        resultField = (TextView) findViewById(R.id.resultField);

        // Получаем ссылки на цифровые кнопки
        Button buttonZero = (Button) findViewById(R.id.buttonZero);
        Button buttonOne = (Button) findViewById(R.id.buttonOne);
        Button buttonTwo = (Button) findViewById(R.id.buttonTwo);
        Button buttonThree = (Button) findViewById(R.id.buttonThree);
        Button buttonFour = (Button) findViewById(R.id.buttonFour);
        Button buttonFive = (Button) findViewById(R.id.buttonFive);
        Button buttonSix = (Button) findViewById(R.id.buttonSix);
        Button buttonSeven = (Button) findViewById(R.id.buttonSeven);
        Button buttonEight = (Button) findViewById(R.id.buttonEight);
        Button buttonNine = (Button) findViewById(R.id.buttonNine);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        // Получаем ссылки на арифметические операции
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonEqual = (Button) findViewById(R.id.buttonEqual);
        Button buttonClear = (Button) findViewById(R.id.buttonClear);

        // Назначаем обработчик событий цифровых кнопок
        buttonNumericListener(buttonOne);
        buttonNumericListener(buttonTwo);
        buttonNumericListener(buttonThree);
        buttonNumericListener(buttonFour);
        buttonNumericListener(buttonFive);
        buttonNumericListener(buttonSix);
        buttonNumericListener(buttonSeven);
        buttonNumericListener(buttonEight);
        buttonNumericListener(buttonNine);
        buttonNumericListener(buttonZero);
        buttonNumericListener(buttonDot);

        // Назначаем обработчик событий арифметических операций
        buttonArithmeticListener(buttonDivide);
        buttonArithmeticListener(buttonMultiply);
        buttonArithmeticListener(buttonMinus);
        buttonArithmeticListener(buttonPlus);
        buttonEqualListener(buttonEqual);
        buttonClearListener(buttonClear);

    } // eof onCreate()

    private void buttonClearListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultNumber = 0;
                currentNumber = "";
                currentAction = null;
                numberField.setText("");
                resultField.setText("0");
            }
        });
    }

    private void buttonNumericListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber += button.getText().toString();
                numberField.setText(numberField.getText().toString() + button.getText());

                if (currentAction == null) {
                    resultField.setText("= " + currentNumber);
                } else {
                    if (checkDivisionByZero()) {
                        resultField.setText("= Разделить на ноль нельзя");
                        currentNumber = String.valueOf(previousNumber);
                        previousNumber = 0;
                        numberField.setText(currentNumber);
                    } else {
                        calculate();
                        resultField.setText("= " + resultNumber);
                    }
                }
            }
        });
    }

    private void buttonArithmeticListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previousNumber == 0) {
                    previousNumber = Double.parseDouble(currentNumber);
                } else {
                    calculate();
                    previousNumber = resultNumber;
                }
                currentNumber = "";
                currentAction = button.getText().toString();
                numberField.setText(numberField.getText().toString() + button.getText());
            }
        });
    }

    private void buttonEqualListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentAction == null) {
                    resultNumber = Double.parseDouble(currentNumber);
                } else calculate();

                resultField.setText("= " + String.valueOf(resultNumber));
            }
        });
    }

    private void calculate() {
        switch (currentAction) {
            case DIVIDE:
                resultNumber = previousNumber / Double.parseDouble(currentNumber);
                break;
            case MULTIPLY:
                resultNumber = previousNumber * Double.parseDouble(currentNumber);
                break;
            case MINUS:
                resultNumber = previousNumber - Double.parseDouble(currentNumber);
                break;
            case PLUS:
                resultNumber = previousNumber + Double.parseDouble(currentNumber);
                break;
        }
    }

    private boolean checkDivisionByZero() {
        return Double.parseDouble(currentNumber) == 0;
    }

}