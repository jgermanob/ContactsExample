package com.germanco.contactos;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class RecuperaContactos extends AppCompatActivity {
    SharedPreferences preferences;
    String jsonContactos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_contactos);
        recuperaContactos();
    }

    public void recuperaContactos(){
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        jsonContactos=preferences.getString("lista","vacio");
        Log.d("Contactos",jsonContactos);
    }
}
