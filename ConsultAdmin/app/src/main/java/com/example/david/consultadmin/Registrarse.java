package com.example.david.consultadmin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.david.consultadmin.Objetos.FirebaseReferences;
import com.example.david.consultadmin.Objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by David on 27/04/2017.
 */
public class Registrarse extends AppCompatActivity
{
    EditText cajaNombre,
            cajaApellidos,
            cajaCorreoElectronico,
            cajaContraseña,
            cajaConfirmarContraseña,
            cajaNombreDelNegocio,
            cajaDomicilio,
            cajaTiempoDelNegocio,
            cajaGiroDelNegocio;
    FirebaseAuth.AuthStateListener mAuthListener;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        cajaNombre = (EditText) findViewById(R.id.cajaNombre);
        cajaApellidos = (EditText) findViewById(R.id.cajaApellidos);
        cajaCorreoElectronico = (EditText) findViewById(R.id.cajaCorreoElectronico);
        cajaContraseña = (EditText) findViewById(R.id.cajaContraseña);
        cajaConfirmarContraseña = (EditText) findViewById(R.id.cajaConfirmarContraseña);
        cajaNombreDelNegocio = (EditText) findViewById(R.id.cajaNombreDelNegocio);
        cajaDomicilio = (EditText) findViewById(R.id.cajaDomicilio);
        cajaTiempoDelNegocio = (EditText) findViewById(R.id.cajaTiempoDelNegocio);
        cajaGiroDelNegocio = (EditText) findViewById(R.id.cajaGiroDelNegocio);

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
    }

    public void abreDialogo(final View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tituloTerminos);
        builder.setMessage(R.string.terminosYCondiciones);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!cajaNombre.getText().toString().isEmpty() ||
                        !cajaApellidos.getText().toString().isEmpty() ||
                        !cajaCorreoElectronico.getText().toString().isEmpty() ||
                        !cajaContraseña.getText().toString().isEmpty() ||
                        !cajaConfirmarContraseña.getText().toString().isEmpty() ||
                        !cajaNombreDelNegocio.getText().toString().isEmpty() ||
                        !cajaDomicilio.getText().toString().isEmpty() ||
                        !cajaTiempoDelNegocio.getText().toString().isEmpty() ||
                        !cajaGiroDelNegocio.getText().toString().isEmpty())
                {
                    if (cajaContraseña.getText().toString().equals(cajaConfirmarContraseña.getText().toString()))
                    {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference consultadminRef = database.getReference(FirebaseReferences.CONSULTADMIN_REFERENCES);
                        Usuario usuario = new Usuario(cajaNombre.getText().toString(),
                                cajaApellidos.getText().toString(),
                                cajaCorreoElectronico.getText().toString(),
                                cajaContraseña.getText().toString(),
                                cajaNombreDelNegocio.getText().toString(),
                                cajaDomicilio.getText().toString(),
                                cajaTiempoDelNegocio.getText().toString(),
                                cajaGiroDelNegocio.getText().toString());
                        consultadminRef.child(FirebaseReferences.USUARIOS_REFERENCES).push().setValue(usuario);
                        registrarUsuario(cajaCorreoElectronico.getText().toString(), cajaContraseña.getText().toString());
                        limpiarFormulario(v);
                        intent = new Intent(Registrarse.this, MenuActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(Registrarse.this, "LAS  CONTRASEÑAS NO COINCIDEN", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Registrarse.this, R.string.usuarioRechazado, Toast.LENGTH_SHORT).show();
            }
        });

        Dialog dialogo = builder.create();
        dialogo.show();
    }

    public void limpiarFormulario(View v)
    {
        cajaNombre.setText("");
        cajaApellidos.setText("");
        cajaCorreoElectronico.setText("");
        cajaContraseña.setText("");
        cajaConfirmarContraseña.setText("");
        cajaNombreDelNegocio.setText("");
        cajaDomicilio.setText("");
        cajaTiempoDelNegocio.setText("");
        cajaGiroDelNegocio.setText("");
    }

    private void registrarUsuario(String correoElectronico, String contraseña)
    {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correoElectronico, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    Toast.makeText(Registrarse.this, R.string.usuarioAceptado, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Registrarse.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
