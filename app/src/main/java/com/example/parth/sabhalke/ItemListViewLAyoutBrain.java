package com.example.parth.sabhalke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Parth on 3/20/2017.
 */

public class ItemListViewLAyoutBrain extends ArrayAdapter<Integer> {

    String str;
    int pid;
    public ItemListViewLAyoutBrain(Context context, ArrayList<Integer> item_id,String s,int id) {
        super(context, R.layout.itemlistviewlayout,item_id);
        str=s;
        pid=id;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater Parth_inflater =LayoutInflater.from(getContext());
        View view=Parth_inflater.inflate(R.layout.itemlistviewlayout,parent,false);
        TextView itemprice =(TextView)view.findViewById(R.id.price_item);
        TextView item_name=(TextView)view.findViewById(R.id.name_item);
        Item item=MainActivity.Database.get_Item(getItem(position),str+pid+"");
        itemprice.setText(item.rs+"");
        item_name.setText(item.Item_name+"");
        return view;
    }
}
