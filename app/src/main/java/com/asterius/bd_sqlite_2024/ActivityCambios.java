package com.asterius.bd_sqlite_2024;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import bd.EscuelaBD;
import entidades.Alumno;

public class ActivityCambios extends Activity implements CustomAdapter2.OnAlumnoClickListener {

    ArrayList<Alumno> datos= null;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText cajaNumControl,cajaNombre,cajaEdad;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);

        cajaNumControl = findViewById(R.id.caja_num_control_c);
        cajaNombre = findViewById(R.id.caja_nombre_c);
        cajaEdad = findViewById(R.id.caja_edad_c);

        iniciarRecyclerView();

    }

    private void iniciarRecyclerView(){

        EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());

        recyclerView = findViewById(R.id.lista_alumnos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

          new Thread(new Runnable() {

              @Override
              public void run() {

                  datos = (ArrayList<Alumno>) bd.alumnoDAO().mostarTodos();

                  runOnUiThread(new Runnable() {

                      @Override
                      public void run() {

                          adapter = new CustomAdapter2(datos, ActivityCambios.this);
                          recyclerView.setAdapter(adapter);

                    }
                });

            }

        }).start();

    }

    public void alumnoSeleccionado(String cadena){

       String[] datosCargados;
       datosCargados = cadena.split("\\|");

       Log.i ("MSJ", datosCargados[0]);

       cajaNumControl.setText(datosCargados[0]);
       cajaNombre.setText(datosCargados[1]);
       cajaEdad.setText(datosCargados[2]);

    }

    @Override
    public void onAlumnoClick(String cadena) {

        alumnoSeleccionado(cadena);

    }

    public void aceptarModificacion(View v){

        EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());

        new Thread(new Runnable() {

            @Override
            public void run() {
                String numcontrol = String.valueOf(cajaNumControl.getText());
                String nombre = String.valueOf(cajaNombre.getText());
                String edad = String.valueOf(cajaEdad.getText());

                if(!(numcontrol.equals("") && nombre.equals("") && edad.equals(""))){

                    bd.alumnoDAO().actualizarAlumnoPorNumControl(numcontrol, nombre, Byte.parseByte(edad));

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }
                    });
                }
            }
        }).start();
    }
}

class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder>{

    private ArrayList<Alumno> localDataSet;
    private OnAlumnoClickListener listener;


    //Clase INTERNA
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView textView;

        public ViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.textView_recycler);
        }

        public TextView getTextView(){
            return textView;
        }
    }

    public interface OnAlumnoClickListener {
        void onAlumnoClick(String cadena);
    }
    public CustomAdapter2(ArrayList<Alumno> dataset, OnAlumnoClickListener listener) {
        localDataSet = dataset;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_recyclerview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.getTextView().setText( localDataSet.get(position).toString() );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAlumnoClick(localDataSet.get(position).toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return localDataSet.size();

    }

}//CustomAdapter
