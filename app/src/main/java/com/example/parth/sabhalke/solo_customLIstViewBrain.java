package com.example.parth.sabhalke;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class solo_customLIstViewBrain extends ArrayAdapter<Integer> {


    public solo_customLIstViewBrain(Context context, ArrayList<Integer> person_name){

        super(context,R.layout.solo_custom_list_view,person_name);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater Parth_inflate = LayoutInflater.from(getContext());
        View Custom_view=Parth_inflate.inflate(R.layout.solo_custom_list_view,parent,false);
        TextView Nametxt= (TextView)Custom_view.findViewById(R.id.name);
        TextView balancetxt=(TextView)Custom_view.findViewById(R.id.balance_view);
        friend fr=MainActivity.Database.getfriend(getItem(position));
        Nametxt.setText(fr.name);
        //balancetxt.setText(getItem(position)+"");
        balancetxt.setText(fr.balance+"");

        return Custom_view;
    }
}
