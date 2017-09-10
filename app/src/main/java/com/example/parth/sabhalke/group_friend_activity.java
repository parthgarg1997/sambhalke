package com.example.parth.sabhalke;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class group_friend_activity extends AppCompatActivity {
    ArrayList<Integer> friends_id;
    EditText rs_textView;
    EditText common_item;
    Button add;
    Button save;
    TextView shower;
    Spinner fspinner;
     ArrayList<ArrayList<Integer>> rs=new ArrayList<ArrayList<Integer>>();
     ArrayList<ArrayList<String>> item=new ArrayList<ArrayList<String>>();
     ArrayList<String> text_editor=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_friend_activity);
         rs_textView=(EditText) findViewById(R.id.rs_group);
         common_item=(EditText) findViewById(R.id.common_item);
         add=(Button)findViewById(R.id.Add_group);
         save=(Button)findViewById(R.id.save_group1);
         shower=(TextView)findViewById(R.id.list_shower);
        shower.setMovementMethod(new ScrollingMovementMethod());

        Bundle bundle= getIntent().getExtras();
        if(bundle==null)
            return;
        else
            friends_id =bundle.getIntegerArrayList("checked_count");
        final ArrayList<friend> fr=new ArrayList<friend>();
        friend you=new friend("You",0);
        fr.add(you);
        rs.add(new ArrayList<Integer>());
        item.add(new ArrayList<String>());
        text_editor.add("");
        for(int i=0;i<friends_id.size();i++)
        {
            fr.add(MainActivity.Database.getfriend(friends_id.get(i)));
            rs.add(new ArrayList<Integer>());
            item.add(new ArrayList<String>());
            text_editor.add("");
        }

        fspinner=(Spinner)findViewById(R.id.friend_list_spinner);
        ArrayAdapter<friend> adapter =new ArrayAdapter<friend>(this,android.R.layout.simple_spinner_item,fr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       fspinner.setAdapter(adapter);
        fspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                shower.setText(text_editor.get(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                shower.setText("Please select Person who is spending");
            }
        });
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int i=(fspinner.getSelectedItemPosition());

                Integer r=getRs(rs_textView);
                String c=common_item.getText().toString();
                rs.get(i).add(r);
                item.get(i).add(c);
                text_editor.set(i,text_editor.get(i)+(r+"  "+c)+"\n");
                shower.setText(text_editor.get(i));
                rs_textView.setText("");
                common_item.setText("");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item you_item=new Item();
                Item other_item=new Item();
                int difference=0;
                friend k=null;
                for(int j=1;j<=friends_id.size();j++) {
                    difference=0;
                    for (int i = 0; i < rs.get(0).size(); i++) {
                        int q=(rs.get(0).get(i))/(friends_id.size()+1);
                        you_item.rs =q ;
                        difference+=q;
                        you_item.Item_name = "G_"+item.get(0).get(i);
                         k = (friend) fspinner.getItemAtPosition(j);
                        you_item.Person_id = k.id;
                        MainActivity.Database.addItem(you_item, "you");
                    }
                    if(k!=null)
                    MainActivity.Database.difference(difference,k.id);
                    difference=0;
                    k=null;
                    for(int l=0;l<rs.get(j).size();l++)
                    {
                       int z=(rs.get(j).get(l))/(friends_id.size()+1);
                        other_item.rs=z;
                        difference+=z;
                        other_item.Item_name="G_"+item.get(j).get(l);
                        k=(friend)fspinner.getItemAtPosition(j);
                        other_item.Person_id=k.id;
                        MainActivity.Database.addItem(other_item,"other");
                    }
                    if(k!=null)
                        MainActivity.Database.difference((-difference),k.id);
                }
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
