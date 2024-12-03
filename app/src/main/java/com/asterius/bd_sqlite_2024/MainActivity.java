package com.asterius.bd_sqlite_2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void abrirActivitiesAltas(View v){

        Intent i = new Intent(this, ActivityAltas.class);
        startActivity(i);

    }

    public void abrirActivitiesBajas(View v){

        Intent i = new Intent(this, ActivityBajas.class);
        startActivity(i);

    }

    public void abrirActivitiesCambios(View v){

        Intent i = new Intent(this, ActivityCambios.class);
        startActivity(i);

    }

    public void abrirActivitiesConsultas(View v){

        Intent i = new Intent(this, ActivityConsultas.class);
        startActivity(i);

    }

}