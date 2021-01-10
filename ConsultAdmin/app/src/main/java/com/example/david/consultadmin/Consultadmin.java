package com.example.david.consultadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Consultadmin extends AppCompatActivity {

    Intent i;
    EditText cajaCorreoElectronico;
    EditText cajaConstraseña;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultadmin);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.i("SESION", "sesion iniciada con email: " + user.getEmail());
                } else {
                    Log.i("SESION", "sesion cerrada");
                }
            }
        };

        cajaCorreoElectronico = (EditText) findViewById(R.id.cajaCorreoElectronico);
        cajaConstraseña = (EditText) findViewById(R.id.cajaContraseña);
    }

    public void presionarBotonLogin(View v)
    {
        switch (v.getId())
        {
            case R.id.btnIniciarSesion:
                if (!cajaCorreoElectronico.getText().toString().isEmpty() &&
                        !cajaConstraseña.getText().toString().isEmpty())
                    iniciarSesion(cajaCorreoElectronico.getText().toString(), cajaConstraseña.getText().toString());

                else
                    Toast.makeText(this, "CAJAS VACIAS", Toast.LENGTH_SHORT).show();



                break;

            case R.id.btnRegistrarse:
                i = new Intent(this, Registrarse.class);
                startActivity(i);
                break;
        }

    }

    private void iniciarSesion(String correoElectronico, String contraseña)
    {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correoElectronico, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    i = new Intent(Consultadmin.this, MenuActivity.class);
                    startActivity(i);
                    Toast.makeText(Consultadmin.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Consultadmin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null)
        {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

}
