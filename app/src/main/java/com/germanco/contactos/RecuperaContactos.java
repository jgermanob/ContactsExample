package com.germanco.contactos;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecuperaContactos extends AppCompatActivity {
    SharedPreferences preferences;
    String jsonContactos;
    List<String> telefonosContactos= new ArrayList<>();
    EditText mensaje;
    Button botonMensaje;
    SmsManager smsManager;
    String stringMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_contactos);
        mensaje=(EditText)findViewById(R.id.mensaje);
        botonMensaje=(Button)findViewById(R.id.botonMensaje);
        leeJSON(recuperaContactos());
        botonMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringMensaje=mensaje.getText().toString().trim();
                if(stringMensaje.equals("")){
                    Toast.makeText(getApplicationContext(),"No has escrito el mensaje",Toast.LENGTH_LONG).show();
                }else{
                    enviarMensaje(stringMensaje);
                }
            }
        });
    }

    public String recuperaContactos(){
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        jsonContactos=preferences.getString("lista","vacio");
        Log.d("Contactos",jsonContactos);
        return jsonContactos;
    }

    public void leeJSON(String json){
        try {
            JSONArray jsonArray= new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject contacto=jsonArray.getJSONObject(i);
                String telefono=contacto.getString("telefono");
                telefonosContactos.add(telefono);
                Log.d("Telefono agregado",telefono);
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje){
        smsManager=SmsManager.getDefault();
        for(int i=0; i<telefonosContactos.size();i++){
            smsManager.sendTextMessage(telefonosContactos.get(i),null,mensaje,null,null);
        }
    }


}
