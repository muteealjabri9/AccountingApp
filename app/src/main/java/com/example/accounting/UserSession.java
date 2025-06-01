package com.example.accounting;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    private SharedPreferences.Editor editor;
    private  SharedPreferences sharedPreferences;
    public UserSession(Context context){
        sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        editor =sharedPreferences.edit();
    }
    public void saveUserId(int id){
        editor.putInt("id",id);
        editor.commit();
    }
    public int getUserId(){
        return  sharedPreferences.getInt("id",0);
    }


    public void saveUserType(int id){
        editor.putInt("type",id);
        editor.commit();
    }
    public int getUserType(){
        return  sharedPreferences.getInt("type",0);
    }
}
