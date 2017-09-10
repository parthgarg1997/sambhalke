package com.example.parth.sabhalke;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Parth on 2/6/2017.
 */

public class group_brain extends Fragment {

    ListView group_list;
    Button procede_button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View group = inflater.inflate(R.layout.group,container,false);
        group_list=(ListView)group.findViewById(R.id.GROUP_lISTVIEW);
        procede_button=(Button)group.findViewById(R.id.procedde_group);
        procede_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    SparseBooleanArray checked = group_list.getCheckedItemPositions();


                    ArrayList c_i_count = new ArrayList<Intent>();
                    for (int i = 0; i < group_list.getAdapter().getCount(); i++) {
                        if (checked.get(i)) {
                            c_i_count.add(group_list.getItemAtPosition(i));
                        }

                    }
                if(c_i_count.size()<1)
                {
                    Toast.makeText(getContext(),"Select at least one friend", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), group_friend_activity.class);
                    intent.putExtra("checked_count", c_i_count);

                    startActivity(intent);
                }
                }



        });


        return group;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<Integer> friend_list=MainActivity.Database.getTotalfriendid();
        final ListAdapter adapter=new solo_customLIstViewBrain(getContext(),friend_list);
        group_list.setAdapter(adapter);
        group_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(group_list.isItemChecked(i))
                {
                    view.setBackgroundColor(Color.GRAY);
                }
                else
                    view.setBackgroundColor(Color.WHITE);
            }
        });


    }
}
