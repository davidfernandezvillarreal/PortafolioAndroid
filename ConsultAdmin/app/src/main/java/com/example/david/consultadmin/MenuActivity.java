package com.example.david.consultadmin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.david.consultadmin.Objetos.Chat;
import com.example.david.consultadmin.Objetos.FirebaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, generacion_de_filosofia_empresarial.OnFragmentInteractionListener {


    FirebaseAuth.AuthStateListener mAuthListener;
    String emailUsuarioIniciado;
    EditText cajaMensaje;
    final Chat ordenDeMensajes = new Chat();

    Vector vector = new Vector(1,1);
    ArrayList<String> mensajes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.i("SESION", "sesion iniciada con email: " + user.getEmail() + " en menuActivity");
                    emailUsuarioIniciado = user.getEmail();
                } else {
                    Log.i("SESION", "sesion cerrada");
                }
            }
        };
    }

    public void registrarChat()
    {
        cajaMensaje = (EditText) findViewById(R.id.caja_mensaje);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference consultadminRef = database.getReference(FirebaseReferences.CONSULTADMIN_REFERENCES);
        Log.i("LLAVES", "numero de mensajesFUERA:" + ordenDeMensajes.getNumeroDeMensaje());
        consultadminRef.child(FirebaseReferences.CHATS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ordenDeMensajes.setNumeroDeMensaje((int) dataSnapshot.getChildrenCount());
                Log.i("LLAVES", dataSnapshot.getValue() + "");
                Log.i("LLAVES", "numero de mensajes: " + ordenDeMensajes.getNumeroDeMensaje());

                String valores = dataSnapshot.getValue().toString();
                String id = "";

                for (int a=0; a<valores.length(); a++) //Recorrido de los valores de los chats
                {
                    if(String.valueOf(valores.charAt(a)).equals("="))
                    {
                        if (!String.valueOf(valores.charAt(a+1)).equals("{"))
                        {
                            a=a+1;
                            while (true)
                            {
                                if (String.valueOf(valores.charAt(a)).equals(",") || String.valueOf(valores.charAt(a)).equals("}"))
                                    break;

                                id = id + String.valueOf(valores.charAt(a));
                                a++;
                            }
                            Log.i("VECTOR", "id: " + id);
                            vector.addElement(id);
                            id="";

                        }
                    }
                }

                Log.i("VECTOR", "vector.size()=" + vector.size());

                for (int i = 0; i < vector.size(); i++)
                {
                    Log.i("VECTOR", "vector(" + i + ")=" + vector.elementAt(i));
                }

                Log.i("LLAVES", "numero de mensajes: " + dataSnapshot.getChildrenCount());

                int contador = 0;
                for (int a=3; a<vector.size(); a=a+4)
                {
                    Log.i("VECTOR", "a=" + a);
                    Log.i("VECTOR", "Integer.parseInt(vector.elementAt("+a+").toString())=" + Integer.parseInt(vector.elementAt(a).toString()));
                    //for (int b=0; b<Integer.parseInt(vector.elementAt(a).toString()); b++)
                    //{
                        //try {
                            //if (b == Integer.parseInt(vector.elementAt(a).toString()))
                            //{
                                //if (mensajes.get(b).equals("null"))
                                //{
                                    mensajes.add(contador, vector.elementAt(a-1).toString() + ":\n" + vector.elementAt(a-3).toString());
                                    Log.i("VECTOR", "ArrayList:" + mensajes.size());
                                    contador++;
                                    //mensajes.remove(b+1);
                                //}
                            //}
                            //else
                            //if (mensajes.get(b).equals("null"));

                        //} catch (Exception e) {
                            //if (b==Integer.parseInt(vector.elementAt(a).toString()))
                                //mensajes.add(b, vector.elementAt(a-1).toString() + ":\n" + vector.elementAt(a-3).toString());
                            //else
                                //mensajes.add(b,"null");
                        //}
                    //}
                }

                listViewMensajes = (ListView) findViewById(R.id.list_view_chat);
                cajaMensaje = (EditText) findViewById(R.id.caja_mensaje);
                ArrayAdapter adaptador = new ArrayAdapter(MenuActivity.this, android.R.layout.simple_list_item_1);


               // Log.i("VECTOR", "ArrayList:" + mensajes.size());
                adaptador.addAll(mensajes);
                try {
                    listViewMensajes.setAdapter(adaptador);
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Chat chat = new Chat(emailUsuarioIniciado, cajaMensaje.getText().toString(), (emailUsuarioIniciado.equals("consultadmin@gmail.com") ? "david@gmail.com" : "consultadmin@gmail.com"), ordenDeMensajes.getNumeroDeMensaje());
        consultadminRef.child(FirebaseReferences.CHATS).push().setValue(chat);


    }

    ListView listViewMensajes;

    public void enviarMensaje(View v)
    {
        //if (v.getId() == R.id.btnEnviar && !cajaMensaje.getText().toString().equalsIgnoreCase("")) {
            registrarChat();
        //}
    }

    public void abrirFragment(View v)
    {
        int id = v.getId();

        Fragment f = null;
        boolean fTransaccion = false;

        /*if (id == R.id.btnPlaneacion) {
            f = new Planeacion();
            getSupportActionBar().setTitle("Planeación");
            fTransaccion = true;
        }
        else if (id == R.id.btnOrganizacion){
            f = new Organizacion();
            getSupportActionBar().setTitle("Organización");
            fTransaccion = true;
        }
        else if (id == R.id.btnDiseñoDeEstrategias)
        {
            f = new DisenoDeEstrategias();
            getSupportActionBar().setTitle("Diseño de Estrategias");
            fTransaccion = true;
        }*/

        if (fTransaccion)
        {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.content_menu, f);
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            trans.addToBackStack(null);
            trans.commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ayuda) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.etiquetaAyuda);
            builder.setMessage(R.string.ayuda);
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            Dialog dialogo = builder.create();
            dialogo.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment f = null;
        boolean fTransaccion = false;

        if (id == R.id.generacionDeFilosofiaEmpresarial)
        {
            // Handle the camera action
            f = new generacion_de_filosofia_empresarial();
            fTransaccion = true;
        } /*else if (id == R.id.capacitacionDelPersonal)
        {
            f = new CapacitacionDelPersonal();
            fTransaccion = true;
        } else if (id == R.id.reclutamientoDelPersonal)
        {
            f = new ReclutamientoDelPersonal();
            fTransaccion = true;
        } else if (id == R.id.imagenCorporativa)
        {
            f = new Publicidad();
            fTransaccion = true;
        } else if (id == R.id.elaboracionDeEstrategias)
        {
            f = new DesarrolloDeFolletos();
            fTransaccion = true;
        } else if (id == R.id.diseñoDeLogotipo)
        {
            f = new DisenoDeMensajes();
            fTransaccion = true;
        } else if (id == R.id.diseñoDePublicidadEnRedesSociales)
        {
            f = new DisenoDePublicidadEnRS();
            fTransaccion = true;
        } else if (id == R.id.analisisDeCompetencia)
        {
            f = new Promocion();
            fTransaccion = true;
        } else if (id == R.id.imagenCorporativa)
        {
            f = new ImagenCorporativa();
            fTransaccion = true;
        } else if (id == R.id.estudioDeTendencias)
        {
            f = new FilosofiaCorporativa();
            fTransaccion = true;
        } else if (id == R.id.asesoriaEnInversiones)
        {
            f = new AsesoriaEnInversiones();
            fTransaccion = true;
        } else if (id == R.id.asesoriaEnInventarios)
        {
            f = new AsesoriaEnInventarios();
            fTransaccion = true;
        }*/

        if(fTransaccion)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_menu, f).addToBackStack(null).commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
