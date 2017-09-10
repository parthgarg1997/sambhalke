package com.example.parth.sabhalke;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class Item_Detail extends AppCompatActivity {
    EditText I_date_txt;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__detail);
        Bundle bundle= getIntent().getExtras();
        if(bundle==null)
           return;
        final int item_id=bundle.getInt("ItemID");
        final int person_id=bundle.getInt("personId");
        final String personDetail=bundle.getString("person");
        String Giver_name=bundle.getString("GiverNmae");
        String Taker_name=bundle.getString("Taker_name");
        final TextView Giver_txt=(TextView)findViewById(R.id.giver);
        TextView Taker_txt=(TextView)findViewById(R.id.taker);
        final EditText I_Name_txt=(EditText)findViewById(R.id.I_Name);
        final EditText I_rs_txt=(EditText)findViewById(R.id.I_rs);
          I_date_txt=(EditText)findViewById(R.id.I_date);
        Button savebutton=(Button)findViewById(R.id.I_Save);
        Button deletebutton=(Button)findViewById(R.id.delte_button);
        Giver_txt.setText(Giver_name);
        Taker_txt.setText(Taker_name);
        Item item =MainActivity.Database.get_Item(item_id,personDetail);
        I_Name_txt.setText(item.Item_name);
        I_rs_txt.setText(item.rs+"");
        I_date_txt.setText(item.date+"");

        savebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int rs=getRs(I_rs_txt);
                MainActivity.Database.Alter_Item(I_Name_txt.getText().toString(),rs,I_date_txt.getText().toString(),item_id,personDetail,person_id);
                finish();
            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Database.delete_item(item_id,personDetail,person_id);
                finish();
            }
        });
    }


    public int getRs(EditText rs)
    {

        int l;
        if(TextUtils.isEmpty(rs.getText().toString()))
            l=0;
        else
            l=Integer.parseInt(rs.getText().toString());
        return l;
    }
}
