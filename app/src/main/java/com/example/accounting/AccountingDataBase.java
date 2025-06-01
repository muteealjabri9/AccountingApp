package com.example.accounting;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountingDataBase extends SQLiteOpenHelper {

    public static final String DATA_BASE_NAME="account.db";
    public static final int DATABASE_VERSION=3;
    private UserSession userSession;

    public AccountingDataBase(Context context){
        super(context,DATA_BASE_NAME,null,DATABASE_VERSION);
        userSession=new UserSession(context);
    }







    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserTable);
        sqLiteDatabase.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE users");
        sqLiteDatabase.execSQL("DROP TABLE booking");
        onCreate(sqLiteDatabase);
    }
    String createTable = "CREATE TABLE account " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "details TEXT, " +
            "price double, " +
            "userId TEXT)";
    public long insertAccount(AccountData accountData){
        ContentValues contentValues=new ContentValues();
        contentValues.put("details",accountData.getDetails());
        contentValues.put("price",accountData.getPrice());
        contentValues.put("userId",accountData.getUserId());
        return getWritableDatabase().insert("account",null,contentValues);
    }

    public long updateAccount(AccountData accountData){
        ContentValues contentValues=new ContentValues();
        contentValues.put("details",accountData.getDetails());
        contentValues.put("price",accountData.getPrice());
        return getWritableDatabase().update("account",contentValues,"id=?",new String[]{String.valueOf(accountData.getId())});
    }





    @SuppressLint("Range")
    public ArrayList<AccountData> getAllAccount(){
        ArrayList<AccountData> data=new ArrayList<>();
        Cursor cursor=getReadableDatabase().query("account",null,"userId=?",new String[]{String.valueOf(userSession.getUserId())},null,null,null,null);
        if(cursor!=null &&cursor.getCount()>0 ){
            while (cursor.moveToNext()){
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String details=cursor.getString(cursor.getColumnIndex("details"));
                double price=cursor.getDouble(cursor.getColumnIndex("price"));
                int userId=cursor.getInt(cursor.getColumnIndex("userId"));


                data.add(new AccountData(id,userId,details,price));
            }
        }

        return data;
    }

    String UserTable = "CREATE TABLE users " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "firstname TEXT, " +
            "lastname TEXT, " +
            "phonenumber TEXT, " +
            " password TEXT ) ";

    public long register(String firstname,String lastname,String phonenumber,String password ){
        ContentValues contentValues=new ContentValues();
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("phonenumber",phonenumber);
        contentValues.put("password",password);
         long id= getWritableDatabase().insert("users",null,contentValues);
         if(id==-1){
             return -1;
         }else{
             userSession.saveUserId((int) id);
            return id;
         }

    }

    public void delete(int id){
        getWritableDatabase().delete("account","id=?",new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public int login(String username, String password){
        Cursor cursor=getReadableDatabase().query("users",new String[]{"id","firstname","lastname","phonenumber","password"},
                "phonenumber=? and password=? ",new String[]{username,password},null,null,null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            userSession.saveUserId(cursor.getInt(cursor.getColumnIndex("id")));
            return cursor.getInt(cursor.getColumnIndex("id"));
        }
        return -1;
    }

    @SuppressLint("Range")
    public String getUserName(int userId){
        Cursor cursor=getReadableDatabase().query("users",new String[]{"id","firstname","lastname","phonenumber","password"},
                "id=?",new String[]{String.valueOf(userId)},null,null,null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            String firstname=cursor.getString(cursor.getColumnIndex("firstname"));
            String lastname=cursor.getString(cursor.getColumnIndex("lastname"));
            return firstname +" "+lastname;
        }
        return null;
    }

    public boolean checkIfPhoneNumberRegistered(String phonenumber){
        Cursor cursor=getReadableDatabase().query("users",new String[]{"id","phonenumber","password",},
                "phonenumber=? ",new String[]{phonenumber},null,null,null);


        return cursor.getCount()>0;

    }
}
