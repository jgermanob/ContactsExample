package com.germanco.contactos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Contacto> contactoList= new ArrayList<>();
    public Contacto contacto;
    public ListView listaContactos;
    Cursor contactoCursor;
    AdaptadorContacto adaptadorContacto;
    Button botonGuardar;
    public List<Contacto> contactosGuardados= new ArrayList<>();
    Gson gson;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaContactos= (ListView)findViewById(R.id.contactsList);
        botonGuardar=(Button)findViewById(R.id.botonGuardar);
        isFirstTIme();
        adaptadorContacto= new AdaptadorContacto(this,contactoList,this);
        listaContactos.setAdapter(adaptadorContacto);
        intent = new Intent(MainActivity.this,RecuperaContactos.class);
        obtenerContactos();
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Estado","Guardar contactos");
                guardarContactos();
            }
        });
    }

    //Obtiene la información de los contactos del dispositivo, ordenandolos alfabeticamente y guardarndolos en una lista
    public void obtenerContactos(){
        String[] projection= new String[]{ContactsContract.Data._ID,ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE};
        String where= ContactsContract.Data.MIMETYPE+"='"+ ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE+"' AND "+ ContactsContract.CommonDataKinds.Phone.NUMBER+ " IS NOT NULL";
        String orden= ContactsContract.Data.DISPLAY_NAME+" ASC";
        contactoCursor=getContentResolver().query(ContactsContract.Data.CONTENT_URI,projection,where,null,orden);
        while (contactoCursor.moveToNext()){
            try {
                contactoCursor.moveToNext();
                contacto= new Contacto();contacto.setIdContacto(contactoCursor.getString(0));
                contacto.setNombre(contactoCursor.getString(1));
                contacto.setTelefono(contactoCursor.getString(2));
                contacto.setSelected(false);
                contactoList.add(contacto);
                adaptadorContacto.notifyDataSetChanged();
            }catch (CursorIndexOutOfBoundsException ex){
                ex.printStackTrace();
            }
        }
    }

    //Guarda la lista de contactos seleccionados en un archivo JSON, utilizando la biblioteca GSON, y SharedPreferences
    public void guardarContactos(){
        for(int i=0; i<AdaptadorContacto.contactoList.size();i++){
            if(AdaptadorContacto.contactoList.get(i).getSelected()){
                contactosGuardados.add(contactoList.get(i));
                Log.d("Contacto guardado",contactoList.get(i).getNombre());
            }
        }
        gson= new GsonBuilder().setPrettyPrinting().create();
        String jsonContactos=gson.toJson(contactosGuardados);
        System.out.println("JSON:\n"+jsonContactos);
        //Guarda JSON con lista de contactos seleccionada
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor=sharedPreferences.edit();
        editor.putString("lista",jsonContactos);
        editor.commit();
        startActivity(intent);

    }

    //Permite mostrar solamente una vez la actividad de guardar contactos
    public void isFirstTIme(){
        SharedPreferences preferences=getPreferences(MODE_PRIVATE);
        boolean isFirst=preferences.getBoolean("isFirst",false);
        if(isFirst==false){
            Log.d("Estado","Es la primera vez");
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("isFirst",true);
            editor.commit();
        }else {
            Log.d("Estado","No es la primera vez");
            Intent intent= new Intent(MainActivity.this,RecuperaContactos.class);
            //Banderas necesarias para no regresar a la activity de Seleccionar y guardar contactos
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
