package com.example.david.sesion01_ejemplodb_sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by David on 17/11/2016.
 */
public class Login extends Activity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void iniciarSesion (View v)
    {
        Intent intent = null;

        switch (v.getId())
        {
            case R.id.btnIniciarSesion:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
