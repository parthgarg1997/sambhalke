package com.example.parth.sabhalke;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class solo_friend_activity extends AppCompatActivity {

    Item item_y=new Item();
    EditText rs_you;
    TextView friend_name_txv;
    EditText Item_you;
    Item item_o=new Item();
    EditText Item_other;
    EditText rs_other;
    int id;
    ListView myListview;
    ListView hisListView;
    TextView differnceTextView;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_friend_activity);
         friend_name_txv=(TextView)findViewById(R.id.friend_name);
        rs_you=(EditText)findViewById(R.id.Rs_you);
         rs_other=(EditText)findViewById(R.id.Rs_other);
         Item_you=(EditText)findViewById(R.id.ITem_you);
         Item_other=(EditText)findViewById(R.id.Item_other);
        Button button_you=(Button)findViewById(R.id.add_bouuton_y);
        Button button_other=(Button)findViewById(R.id.add_button_o);
        differnceTextView=(TextView)findViewById(R.id.Differnce);
        Button Settle_button=(Button)findViewById(R.id.Settle);


        Bundle bundle=getIntent().getExtras();
        if(bundle==null)
            return;
         id=bundle.getInt("id");
         name=MainActivity.Database.getfriend(id).name;
        friend_name_txv.setText(name);

        button_you.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_y.rs=getRs(rs_you);
                item_y.Item_name=Item_you.getText().toString();
                item_y.Person_id=id;
                MainActivity.Database.addItem(item_y,"you");
                rs_you.setText("");
                Item_you.setText("");
                MainActivity.Database.difference(item_y.rs,id);
                onPostResume();

            }
        });
        button_other.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                item_o.Item_name=Item_other.getText().toString();
                item_o.rs=getRs(rs_other);
                item_o.Person_id=id;
                MainActivity.Database.addItem(item_o,"other");
                rs_other.setText("");
                Item_other.setText("");
                MainActivity.Database.difference(-item_o.rs,id);
                onPostResume();

            }
        });
        myListview=(ListView)findViewById(R.id.MY_ITEMS);
        hisListView=(ListView)findViewById(R.id.HIS_ITEM);
        Settle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    builder=new AlertDialog.Builder(view.getContext(),android.R.style.Theme_Material_Dialog_Alert);
                }
                else
                    builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("SETTLE AND CLEAR").setMessage("Are you sure you want to SETTLE AND CLEAR THE ACCOUNT")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.Database.drop_friend(id);
                                solo_friend_activity.this.finish();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

            }
        });
     myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             Intent intent=new Intent(view.getContext(),Item_Detail.class);
             intent.putExtra("ItemID",(int)myListview.getItemAtPosition(i));
             intent.putExtra("person",("you"+id));
             intent.putExtra("personId",id);
             intent.putExtra("GiverNmae","you");
             intent.putExtra("Taker_name",name);
             startActivity(intent);

         }
     });

        hisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(view.getContext(),Item_Detail.class);
                intent.putExtra("ItemID",(int)hisListView.getItemAtPosition(i));
                intent.putExtra("person",("other"+id));
                intent.putExtra("GiverNmae",name);
                intent.putExtra("personId",id);
                intent.putExtra("Taker_name","you");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        int Differnce=0;

        ListAdapter mylistAdapter=new ItemListViewLAyoutBrain(this,MainActivity.Database.getTotalItem(id,"you"),"you",id);
        ListAdapter hisListAdapter=new ItemListViewLAyoutBrain(this,MainActivity.Database.getTotalItem(id,"other"),"other",id);
        myListview.setAdapter(mylistAdapter);
        hisListView.setAdapter(hisListAdapter);
        Differnce=MainActivity.Database.getfriend(id).balance;
        if(Differnce>=0)
        {
         differnceTextView.setText(MainActivity.Database.getfriend(id).name+" owes you ₹"+Differnce);
        }
        else
            differnceTextView.setText("you owes "+MainActivity.Database.getfriend(id).name+" ₹"+(-Differnce));
    }

    @Override
    protected void onStop() {
        super.onStop();
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
