package com.example.ohmigot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.sip.SipAudioCall;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String clickedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 4-band buttons
        Button[] btn = new Button[4];

        for(int i = 0; i < 4; i ++){
            btn[i] = findViewById(R.id.button1 + i);

            // 처음으로 유용하게 쓴 final 변수
            final int I = i;
            final int LS_NUM;
            if (i == 3) LS_NUM = 2;
            else if (i == 2) LS_NUM = 1;
            else LS_NUM = 0;

            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show(LS_NUM, btn[I]);

                }
            });
        }
    }

    void show(int listNumber,Button btn)
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
                clickedItem = colorList[listNumber][which];
                btn.setText(clickedItem);

                // TODO !!!!!!!!!!!!!!!! valueList 값으로 계산 ㄱ
                String result = String.format("4-band : %sΩ (±%s%%)","ASD","ZXC");
                text4band.setText(result);
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

}