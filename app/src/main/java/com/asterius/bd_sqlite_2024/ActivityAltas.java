package com.asterius.bd_sqlite_2024;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import bd.EscuelaBD;
import entidades.Alumno;


public class ActivityAltas extends Activity {

    EditText cajaNumControl,cajaEdad, cajaNombre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        cajaNumControl = findViewById(R.id.caja_num_control);
        cajaNombre = findViewById(R.id.caja_nombre);
        cajaEdad = findViewById(R.id.caja_edad);

    }

    public void agregarAlumno(View v){

        EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());

        //Ejecutamos esta linea en un hilo separado del HILO PRINCIPAL de la GUI
        new Thread(new Runnable() {

            @Override
            public void run() {

                bd.alumnoDAO().agregarAlumno(new Alumno( (Byte.parseByte(cajaEdad.getText().toString())) , cajaNombre.getText().toString(), cajaNumControl.getText().toString() ));

                Log.i("MSJ->", "Insertado correctamente");

                runOnUiThread(new Runnable() {//hilo necesario para la GUI

                    @Override
                    public void run() {

                        Toast.makeText(getBaseContext(), "Insertado correctamente", Toast.LENGTH_LONG).show();

                    }
                });

            }
        }).start();

    }
}
