package com.example.parth.sabhalke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_friend extends AppCompatActivity {
    EditText name;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
         button =(Button)findViewById(R.id.Add_f_button);
         name=(EditText)findViewById(R.id.editText_name);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=name.getText().toString();
                if (s.compareTo("")==0)
                {
                    Toast.makeText(add_friend.this, "PLease Enter valid String", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    friend fr=new friend();
                    fr.name=name.getText().toString();
                    MainActivity.Database.addfriend(fr);
                    finish();
                }
            }
        });
        DisplayMetrics db =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(db);
        int width=db.widthPixels;
        int height=db.heightPixels;
        getWindow().setLayout((int)(width*0.80),(int)(height*0.80));
    }
}
