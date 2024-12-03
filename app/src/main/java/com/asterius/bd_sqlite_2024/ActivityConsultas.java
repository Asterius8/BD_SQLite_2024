package com.asterius.bd_sqlite_2024;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bd.EscuelaBD;
import entidades.Alumno;

public class ActivityConsultas extends Activity{

    // Atributos
    EditText cajaNumControl, cajaNombre, cajaEdad;
    RadioButton r1, r2, r3, r4;
    RadioGroup rg1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Alumno> datos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        // Inicializamos las vistas
        cajaNumControl = findViewById(R.id.caja_num_control_b);
        cajaNombre = findViewById(R.id.caja_nombre_b);
        cajaEdad = findViewById(R.id.caja_edad_b);

        // Inicializamos RadioButtons y el RadioGroup
        rg1 = findViewById(R.id.radioGroup); // El RadioGroup
        r1 = findViewById(R.id.rb1);
        r2 = findViewById(R.id.rb2);
        r3 = findViewById(R.id.rb3);
        r4 = findViewById(R.id.rb4);

        // Configuración del RecyclerView
        recyclerView = findViewById(R.id.lista_alumnos);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    public void mostrarPorFiltro(View v){

        if(r1.isChecked()){

            EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());
            new Thread(() -> {
                datos = (ArrayList<Alumno>) bd.alumnoDAO().buscarPorNumeroDeControl(cajaNumControl.getText().toString());
                runOnUiThread(() -> {
                    adapter = new CustomAdapter(datos);
                    recyclerView.setAdapter(adapter);
                });
            }).start();

        }else if(r2.isChecked()){

            // Obtenemos datos de la misma nombre
            EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());
            new Thread(() -> {
                datos = (ArrayList<Alumno>) bd.alumnoDAO().buscarPorNombre(cajaNombre.getText().toString());
                runOnUiThread(() -> {
                    adapter = new CustomAdapter(datos);
                    recyclerView.setAdapter(adapter);
                });
            }).start();

        }else if(r3.isChecked()){
            // Obtenemos datos de la misma edad
            EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());
            new Thread(() -> {
                datos = (ArrayList<Alumno>) bd.alumnoDAO().buscarPorEdad(Byte.parseByte(cajaEdad.getText().toString()));
                runOnUiThread(() -> {
                    adapter = new CustomAdapter(datos);
                    recyclerView.setAdapter(adapter);
                });
            }).start();

        }else if(r4.isChecked()){

            // Obtenemos datos desde la base de datos en un hilo
            EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());
            new Thread(() -> {
                datos = (ArrayList<Alumno>) bd.alumnoDAO().mostarTodos();
                runOnUiThread(() -> {
                    adapter = new CustomAdapter(datos);
                    recyclerView.setAdapter(adapter);
                });
            }).start();

        }

    }


}