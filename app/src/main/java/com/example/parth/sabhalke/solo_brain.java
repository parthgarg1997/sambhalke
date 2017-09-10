package com.example.parth.sabhalke;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Parth on 2/6/2017.
 */

public class solo_brain extends Fragment {
    ListView friend_list;
    TextView ToTake;
    TextView ToGive;
    TextView Balance;
    View solo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         solo =inflater.inflate(R.layout.solo,container,false);
        ToTake =(TextView)solo.findViewById(R.id.textView21);
        ToGive=(TextView)solo.findViewById(R.id.To_give_num);
        Balance=(TextView)solo.findViewById(R.id.Balance_num);
                friend_list=(ListView)solo.findViewById(R.id.friendList);
        ArrayList<Integer> friend_id=MainActivity.Database.getTotalfriendid();
        ListAdapter adapter =new solo_customLIstViewBrain(getContext(),friend_id);
        friend_list.setAdapter(adapter);
        friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id =(int)friend_list.getItemAtPosition(i);
                Intent intent_solo =new Intent(getActivity(),solo_friend_activity.class);
                intent_solo.putExtra("id",id);
                startActivity(intent_solo);
            }
        });
        return solo;
    }

    @Override
    public void onResume() {
        int sumDiffer[]=MainActivity.Database.grandtotal();
        ToTake.setText(sumDiffer[1]+"");
        ToGive.setText(sumDiffer[0]+"");
        Balance.setText((sumDiffer[1]-sumDiffer[0])+"");
        super.onResume();
        ArrayList<Integer> friend_id=MainActivity.Database.getTotalfriendid();
        ListAdapter adapter =new solo_customLIstViewBrain(getContext(),friend_id);
        friend_list.setAdapter(adapter);

    }
}
