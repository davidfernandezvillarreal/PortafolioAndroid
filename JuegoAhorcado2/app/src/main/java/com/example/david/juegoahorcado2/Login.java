package com.example.david.juegoahorcado2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by David on 05/06/2016.
 */
public class Login extends AppCompatActivity
{
    EditText cajaUsuario, cajaContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cajaUsuario = (EditText) findViewById(R.id.cajaUsuario);
        cajaContraseña = (EditText) findViewById(R.id.cajaContraseña);
    }

    public void entrarMenu(View v)
    {
        Intent i;

        switch (v.getId())
        {
            case R.id.btnEntrar:
                if (cajaUsuario.getText().toString().equals("gogochillo") && cajaContraseña.getText().toString().equals("juego"))
                {
                    i = new Intent(this, Menu.class);
                    i.putExtra("inicio", "BIENVENIDO");
                    startActivity(i);
                }
                else
                {
                    Toast t1 = Toast.makeText(this, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT);
                    t1.show();
                }
                break;
        }
    }
}
