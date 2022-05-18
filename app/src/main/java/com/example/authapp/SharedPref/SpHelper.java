package com.example.authapp.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;

public class SpHelper {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor; //in buat edit share pef
    private Context context;

    public SpHelper(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("apiku", Context.MODE_PRIVATE); //inisiasi sharedpref
        editor = sp.edit();


    }
    //gettoken
    public String getToken(){
//        return sp.getString("token", "");
            return getValue("token");
    }

    //ini set token dan disimpan di share preference
    public void setToken(String token){
        editor.putString("token", token);
        editor.commit(); //share pref hrs di commit dulu
    }

    public void setEmail(String email){
        editor.putString("email", email);
        editor.commit();
    }

//    public String getEmail(){
//        return getValue("email");
//    }

    public void setBoolean(String key, boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean getBoolean(String key){
        return sp.getBoolean(key,false);
    }

    public String getValue(String key){
        return sp.getString(key, "");
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.commit(); //share pref hrs di commit dulu
    }
}
