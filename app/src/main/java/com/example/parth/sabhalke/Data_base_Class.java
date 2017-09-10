package com.example.parth.sabhalke;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Parth on 3/5/2017.
 */

public class Data_base_Class extends SQLiteOpenHelper {
    public static final int Database_version=1;
    public static final String Database_name="bhukad_database.db" +
            "";
    public static final String person_info_table="person_info";
    public static final String NAME="person_NAME";
    public static final String Difference="Difference";
    public static final String ID="Id";
    public static final String ItemIdColumn="ItemId_you";
    public static final String Item_name="Item_name";
    public static final String Item_price="Item_price";
    public static final String Item_date="date";
    public Data_base_Class(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, Database_name, factory, Database_version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="CREATE TABLE "+person_info_table+
                " ( "+ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NAME+" TEXT, "
                +Difference +" INTEGER);";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+person_info_table);
        onCreate(sqLiteDatabase);
    }
    public void addfriend(friend addfriend)
    {

        ContentValues values =new ContentValues();
        SQLiteDatabase db= getWritableDatabase();
        values.put(NAME,addfriend.name);
        values.put(Difference, 0);
        db.insert(person_info_table,null,values);
        db.close();
        addTable();
    }
    public void addTable()
    {
        SQLiteDatabase db=getWritableDatabase();
        String str="SELECT "+ID+" FROM "+person_info_table+" ORDER BY "+ID+" DESC LIMIT 1";
        Cursor c=db.rawQuery(str,null);
        c.moveToFirst();
        int x=c.getInt(c.getColumnIndex(ID));
        String add_Y ="CREATE TABLE you"+x+" ( "+
                ItemIdColumn +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Item_name +" TEXT, "+
                Item_price+" INTEGER, "+
                Item_date+" DATETIME DEFAULT CURRENT_DATE);";
        String add_o="CREATE TABLE other"+x+" ( "+
                ItemIdColumn +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Item_name +" TEXT, "+
                Item_price+" INTEGER, "+
                Item_date+" DATETIME DEFAULT CURRENT_DATE);";
        db.execSQL(add_Y);
        db.execSQL(add_o);
        db.close();
    }
    public void addItem(Item item,String str)
    {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Item_name,item.Item_name);
        values.put(Item_price,item.rs);
        db.insert(str+item.Person_id,null,values);
        db.close();
    }
    public ArrayList<Integer> getTotalfriendid()
    {
        ArrayList<Integer> friendList=new ArrayList<Integer>();
        SQLiteDatabase db=getWritableDatabase();

        String query ="SELECT "+ID+" FROM "+person_info_table+" WHERE 1";
        Cursor c= db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            if(c.getInt(c.getColumnIndex(ID))!=0)
            {
               friendList.add(c.getInt(c.getColumnIndex(ID)));
            }
            c.moveToNext();
        }
        db.close();
        return friendList;
    }
    public friend getfriend(int id)
    {
        String str="SELECT "+NAME+" , "+Difference+" FROM "+person_info_table+" WHERE "+ID+" =="+id;
        SQLiteDatabase db=getWritableDatabase();
        Cursor c=db.rawQuery(str,null);
        c.moveToFirst();
        friend fr=new friend();
        fr.name=c.getString(c.getColumnIndex(NAME));
        fr.balance=c.getInt(c.getColumnIndex(Difference));
        fr.id=id;
        db.close();
        return fr;
    }
    public ArrayList<Integer> getTotalItem(int PErson_id,String str)
    {SQLiteDatabase db=getWritableDatabase();
        String qwery="Select "+ItemIdColumn+" from "+str+PErson_id+" WHERE 1";
        Cursor c=db.rawQuery(qwery,null);
        c.moveToFirst();
        ArrayList<Integer> total_item=new ArrayList<>();

        while(!c.isAfterLast())
        {
            if(c.getInt(c.getColumnIndex(ItemIdColumn))!=0)
            {
                total_item.add(c.getInt(c.getColumnIndex(ItemIdColumn)));
            }
            c.moveToNext();
        }
        db.close();
        return total_item;
    }
    public void difference(int differrece,int personid)
    {
        SQLiteDatabase db=getWritableDatabase();
        String query="UPDATE "+person_info_table+" SET "+Difference+" ="+Difference+" + "+differrece+" WHERE "+ID+" == "+personid;
        db.execSQL(query);
    }
    public Item get_Item(int Item_id,String Table_name)
    {
        Item item=new Item();
        SQLiteDatabase db=getWritableDatabase();
        String qwery="Select * from "+Table_name+" where "+ItemIdColumn+" == "+Item_id;
        Cursor c=db.rawQuery(qwery,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            item.Item_name=c.getString(c.getColumnIndex(Item_name));
            if(c.getInt(c.getColumnIndex(Item_price))!=0)
            {
                item.rs=c.getInt(c.getColumnIndex(Item_price));
            }
            item.date= c.getString(c.getColumnIndex(Item_date));
            c.moveToNext();
        }
        return item;
    }
    public int[] grandtotal()
    {
        int sumdifference[]=new int[2];
        String query="SELECT "+Difference+" FROM "+person_info_table+" WHERE 1";
        SQLiteDatabase db=getWritableDatabase();
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            if(c.getInt(c.getColumnIndex(Difference))<0)
            {
                sumdifference[0]+=-1*c.getInt(c.getColumnIndex(Difference));
            }
            else
            {
                sumdifference[1]+=c.getInt(c.getColumnIndex(Difference));
            }
            c.moveToNext();
        }
        return sumdifference;
    }
    public void drop_friend(int id)
    {
        String s1="DROP TABLE you"+id;
        String s2="DROP TABLE other"+id;
        String s3="DELETE FROM "+person_info_table+" WHERE "+ID+" = "+id;
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(s1);
        db.execSQL(s2);
        db.execSQL(s3);
    }
    public void Alter_Item(String name,int rs,String date,int ItemId,String PersonDetail,int PersonId)
    {

        String sql="UPDATE "+PersonDetail+" SET "+Item_name+" = '"+name+"' , "+Item_price+" = "+rs+" , "+Item_date+" = '"+date+"' WHERE "+
                ItemIdColumn+" = "+ItemId;
        int difference=MainActivity.Database.get_Item(ItemId,PersonDetail).rs-rs;

        if(PersonDetail.contains("you"))
        {
            difference((-1*difference),PersonId);
        }
        else
            difference(difference,PersonId);
        SQLiteDatabase db=getWritableDatabase();
                db.execSQL(sql);


    }
    public void delete_item(int ItemId,String PersonDetail,int PersonId)
    {

        String sql="DELETE FROM "+PersonDetail+" WHERE "+ItemIdColumn+" = "+ItemId;
        int difference=MainActivity.Database.get_Item(ItemId,PersonDetail).rs;
        if(PersonDetail.contains("you"))
        {
            difference((-1*difference),PersonId);
        }
        else
            difference(difference,PersonId);
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(sql);
    }

}
