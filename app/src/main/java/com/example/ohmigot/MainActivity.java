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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;
//
//        ListView listview1 = (ListView) findViewById(R.id.listview1) ;
//        listview1.setAdapter(adapter) ;
        Button[] btn = new Button[4];
        for(int i = 0; i < 4; i ++){
            btn[i] = findViewById(R.id.button1 + i);

            int I = i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn[I].setText("B 1");
                    show();
                }
            });
        }
    }

    void show()
    {
        String[] a = {"asd","qwe","zxc","ery","ouo"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Color");
        builder.setItems(a, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"졸리니까 여기까지만.",Toast.LENGTH_SHORT).show();
                
            }
        });


        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소됨.",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }



}