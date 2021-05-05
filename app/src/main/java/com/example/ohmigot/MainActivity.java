package com.example.ohmigot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String clickedColor;

    double[] values4band = new double[4];
    double[] values5band = new double[5];
    double[] values6band = new double[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 4-band buttons
        Button[] btn4band = new Button[4];

        for(int i = 0; i < 4; i ++){
            btn4band[i] = findViewById(R.id.button4_1 + i);

            final int I = i;
            final int LS_NUM;
            if (i == 3) LS_NUM = 2;
            else if (i == 2) LS_NUM = 1;
            else LS_NUM = 0;

            btn4band[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(4, LS_NUM, btn4band, I);
                }
            });
        }

        // 5-band buttons
        Button[] btn5band = new Button[5];

        for(int i = 0; i < 5; i ++){
            btn5band[i] = findViewById(R.id.button5_1 + i);

            final int I = i;
            final int LS_NUM;
            if (i == 4) LS_NUM = 2;
            else if (i == 3) LS_NUM = 1;
            else LS_NUM = 0;

            btn5band[i].setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    show(5, LS_NUM, btn5band, I);
                }
            });
        }

        // 6-band buttons
        Button[] btn6band = new Button[6];

        for(int i = 0; i < 6; i ++){
            btn6band[i] = findViewById(R.id.button6_1 + i);

            final int I = i;
            final int LS_NUM;
            if (i == 5) LS_NUM = 3;
            else if (i == 4) LS_NUM = 2;
            else if (i == 3) LS_NUM = 1;
            else LS_NUM = 0;

            btn6band[i].setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    show(6, LS_NUM, btn6band, I);
                }
            });
        }

    }

    void show(int bandNum, int listNumber,Button[] btn, final int I)
    {
        TextView text4band = findViewById(R.id.text4band);
        TextView text5band = findViewById(R.id.text5band);
        TextView text6band = findViewById(R.id.text6band);

        // 숫자 0 ~ 9
        String[] colorList0 = {"black","brown","red","orange","yellow","green","blue","purple","gray","white"};
        // 단위 10^n
        String[] colorList1 = {"black","brown","red","orange","yellow","green","blue","purple","gray","white","gold","silver"};
        // 오차 ±n%
        String[] colorList2 = {"brown","red","green","blue","purple","gray","gold","silver","none"};
        // 열 계수 n ppm
        String[] colorList3 = {"brown","red","orange","yellow"};
        String[][] colorList = {colorList0,colorList1,colorList2,colorList3};

        double[] valueList0 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] valueList1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2};
        double[] valueList2 = {1, 2, 0.5, 0.25, 0.1, 0.05, 5, 10, 20};
        double[] valueList3 = {100, 50, 15, 25};
        double[][] valueList = {valueList0, valueList1, valueList2, valueList3};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (listNumber == 0) builder.setTitle("Select Color 0 ~ 9");
        else if (listNumber == 1) builder.setTitle("Select Color 10^n");
        else if (listNumber == 2) builder.setTitle("Select Color ±n%");
        else if (listNumber == 3) builder.setTitle("Select Color n ppm");

        builder.setItems(colorList[listNumber], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickedColor = colorList[listNumber][which];
                btn[I].setText(clickedColor);
                buttonColorChange(btn[I],clickedColor);

                double value = valueList[listNumber][which];

                if(bandNum == 4){
                    setValues4band(I, value);
                    String text = String.format("4-band : %sΩ ┃ ±%s%%", (Object[]) get4bandValues());
                    text4band.setText(text);
                }
                else if(bandNum == 5){
                    setValues5band(I, value);
                    String text = String.format("5-band : %sΩ ┃ ±%s%%", (Object[]) get5bandValues());
                    text5band.setText(text);
                }
                else if(bandNum == 6){
                    setValues6band(I, value);
                    String text = String.format("6-band : %sΩ ┃ ±%s%% ┃ %s ppm", (Object[]) get6bandValues());
                    text6band.setText(text);
                }

            }
        });

        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소 (아무것도 안함)
                    }
                });

        builder.show();

    }

    String[] get4bandValues() {
        double valueNumber = values4band[0] * 10 + values4band[1];
        double valueSquared = values4band[2];
        double valueRange = values4band[3];

        String calcString = null;
        double calcValue = valueNumber * Math.pow(10,valueSquared);

        if(calcValue >= 1000000000) {
            calcString = String.format("%.1fB",calcValue / 1000000000);
        }
        else if(calcValue >= 1000000) {
            calcString = String.format("%.1fM",calcValue / 1000000);
        }
        else if(calcValue >= 1000) {
            calcString = String.format("%.1fK",calcValue / 1000);
        }
        else {
            // double 형 소수점 계산 오차 때문에 반올림 처리
            calcValue = (double)Math.round(calcValue*100)/100;
            calcString = Double.toString(calcValue);
        }

        String valueRangeS = Double.toString(valueRange);
        valueRangeS = valueRangeS.replaceAll("\\.?0+$", "");

        String[] result = {calcString, valueRangeS};
        return result;
    }

    String[] get5bandValues() {
        double valueNumber = values5band[0] * 100 + values5band[1] * 10 + values5band[2];
        double valueSquared = values5band[3];
        double valueRange = values5band[4];

        String calcString = null;
        double calcValue = valueNumber * Math.pow(10,valueSquared);

        if(calcValue >= 1000000000) {
            calcString = String.format("%.1fB",calcValue / 1000000000);
        }
        else if(calcValue >= 1000000) {
            calcString = String.format("%.1fM",calcValue / 1000000);
        }
        else if(calcValue >= 1000) {
            calcString = String.format("%.1fK",calcValue / 1000);
        }
        else {
            // double 형 소수점 계산 오차 때문에 반올림 처리
            calcValue = (double)Math.round(calcValue*100)/100;
            calcString = Double.toString(calcValue);
        }

        String valueRangeS = Double.toString(valueRange);
        valueRangeS = valueRangeS.replaceAll("\\.?0+$", "");

        String[] result = {calcString, valueRangeS};
        return result;
    }

    String[] get6bandValues() {
        double valueNumber = values6band[0] * 100 + values6band[1] * 10 + values6band[2];
        double valueSquared = values6band[3];
        double valueRange = values6band[4];
        double valuePpm = values6band[5];

        String calcString = null;
        double calcValue = valueNumber * Math.pow(10,valueSquared);

        if(calcValue >= 1000000000) {
            calcString = String.format("%.1fB",calcValue / 1000000000);
        }
        else if(calcValue >= 1000000) {
            calcString = String.format("%.1fM",calcValue / 1000000);
        }
        else if(calcValue >= 1000) {
            calcString = String.format("%.1fK",calcValue / 1000);
        }
        else {
            // double 형 소수점 계산 오차 때문에 반올림 처리
            calcValue = (double)Math.round(calcValue*100)/100;
            calcString = Double.toString(calcValue);
        }

        String valueRangeS = Double.toString(valueRange);
        valueRangeS = valueRangeS.replaceAll("\\.?0+$", "");

        String valuePpmS = Double.toString(valuePpm);

        String[] result = {calcString, valueRangeS, valuePpmS};
        return result;
    }

    void buttonColorChange(Button btn, String color){
        btn.setTextColor(Color.WHITE);
        if (color == "black") btn.setBackgroundColor(Color.parseColor("#000000"));
        else if(color == "brown") btn.setBackgroundColor(Color.parseColor("#8B4513"));
        else if(color == "red") btn.setBackgroundColor(Color.parseColor("#FF0000"));
        else if(color == "orange") btn.setBackgroundColor(Color.parseColor("#FFA500"));
        else if(color == "green") btn.setBackgroundColor(Color.parseColor("#00BB00"));
        else if(color == "blue") btn.setBackgroundColor(Color.parseColor("#0000BB"));
        else if(color == "purple") btn.setBackgroundColor(Color.parseColor("#8282FF"));
        else if(color == "gray") btn.setBackgroundColor(Color.parseColor("#787878"));
        else if(color == "gold") btn.setBackgroundColor(Color.parseColor("#FFD700"));
        else if(color == "silver") btn.setBackgroundColor(Color.parseColor("#C0C0C0"));
        else if(color == "yellow"){
            btn.setBackgroundColor(Color.parseColor("#FFF000"));
            btn.setTextColor(Color.BLACK);
        }
        else{ // none and white
            btn.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btn.setTextColor(Color.BLACK);
        }
    }

    void setValues4band(int i, double value){
        values4band[i] = value;
    }

    void setValues5band(int i, double value){
        values5band[i] = value;
    }

    void setValues6band(int i, double value){
        values6band[i] = value;
    }



}