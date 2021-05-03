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
                    show(4,LS_NUM, btn4band, I);
                }
            });
        }

        // TODO make 5-band buttons !


    }

    void show(int bandNum, int listNumber,Button[] btn, final int I)
    {
        TextView text4band = findViewById(R.id.text4band);

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
                setValues4band(I, value);

                String text = String.format("4-band : %sΩ ┃ ±%s%%", (Object[]) get4bandValues());
                text4band.setText(text);
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
            calcString = Double.toString(calcValue);
        }

        String valueRangeS = Double.toString(valueRange);
        valueRangeS = valueRangeS.replaceAll("\\.?0+$", "");

        String[] result = {calcString, valueRangeS};
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

}