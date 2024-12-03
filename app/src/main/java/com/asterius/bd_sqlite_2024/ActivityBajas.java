package com.asterius.bd_sqlite_2024;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;
import java.util.ArrayList;

import bd.EscuelaBD;
import entidades.Alumno;

public class ActivityBajas extends Activity {

    EditText cajaNumControl;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<Alumno> datos = null;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);

        cajaNumControl = findViewById(R.id.txt_num_control);

        recyclerView = findViewById(R.id.lista_alumnos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());

        new Thread(new Runnable() {

            @Override
            public void run() {

                datos = (ArrayList<Alumno>) bd.alumnoDAO().mostarTodos();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter = new CustomAdapter(datos);
                        recyclerView.setAdapter(adapter);
                    }
                });

            }

        }).start();


    }

    public void eliminarAlumno(View v){

        EscuelaBD bd = EscuelaBD.getAppDataBase(getBaseContext());

        new Thread(new Runnable() {

            @Override
            public void run() {
                String numcontrol = String.valueOf(cajaNumControl.getText());
                bd.alumnoDAO().eliminarAlumnoPorNumControl(numcontrol);

                runOnUiThread(new Runnable() {//hilo necesario para la GUI

                    @Override
                    public void run() {

                        Toast.makeText(getBaseContext(), "Eliminado correctamente", Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    }
                });

            }

        }).start();

    }

}
//CLASE Adaptador ------------------------------------------------------------------------------------------------------------------------------------
class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<Alumno> localDataSet;


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

    public CustomAdapter(ArrayList<Alumno> dataset){
        localDataSet = dataset;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_recyclerview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getTextView().setText( localDataSet.get(position).toString() );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Clicado", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {

        return localDataSet.size();

    }

}//CustomAdapter
