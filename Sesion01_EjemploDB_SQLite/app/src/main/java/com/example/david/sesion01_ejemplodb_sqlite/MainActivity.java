package com.example.david.sesion01_ejemplodb_sqlite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void abrirActivities(View v)
    {
        Intent intent;

        switch (v.getId())
        {
            case R.id.btnAgregar:
                intent = new Intent(this, AgregarAlumno.class);
                startActivity(intent);
                break;
            case R.id.btnEliminar:
                intent = new Intent(this, ActivityConsultas.class);
                startActivity(intent);
                break;
        }
    }
}
