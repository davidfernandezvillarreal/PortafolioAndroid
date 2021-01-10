package com.android.myfirstthread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart;
    private Button btnStop;

    private TextView txtMinutes;
    private TextView txtSeconds;
    private TextView txtMillis;

    private RadioButton radioIncrement;
    private RadioButton radioDecrement;

    private EditText cajaMinutes;
    private EditText cajaSeconds;
    private EditText cajaMillis;

    public static final String TAG = MainActivity.class.getSimpleName();
    private boolean loop;

    private AsyncTaskRunner runner;
    private int min = 0;
    private int sec = 0;
    private int millis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        txtMinutes = (TextView) findViewById(R.id.txtMinutes);
        txtSeconds = (TextView) findViewById(R.id.txtSeconds);
        txtMillis = (TextView) findViewById(R.id.txtMillis);
        radioIncrement = (RadioButton) findViewById(R.id.radioIncrement);
        radioDecrement = (RadioButton) findViewById(R.id.radioDecrement);
        cajaMinutes = (EditText) findViewById(R.id.cajaMinutes);
        cajaSeconds = (EditText) findViewById(R.id.cajaSeconds);
        cajaMillis = (EditText) findViewById(R.id.cajaMillis);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                loop = true;
                runner = new AsyncTaskRunner(); // Inicializar la variable con la clase que hereda de AsyncTask
                runner.execute((String)null); // Ejecutar la tarea asincrona con el método execute()
                break;
            case R.id.btnStop:
                loop = false; //Presionar el botón stop y cambia el loop a false
                runner.cancel(true); //Termina la tarea asíncrona con el método cancel()
                break;
        }
    }

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            /*
             * Inicializa los valores de min, sec y millis
             */
            if (radioDecrement.isChecked()) {
                min = Integer.parseInt(cajaMinutes.getText().toString());
                sec = Integer.parseInt(cajaSeconds.getText().toString());
                millis = Integer.parseInt(cajaMillis.getText().toString());
            }

            while(loop) {
                try {
                    if (radioIncrement.isChecked()) {
                        if (millis<1000) {
                            publishProgress(String.valueOf(millis++), sec + "", min + "");
                            Thread.sleep(1);
                        } else if (sec<59) {
                            millis = 0;
                            publishProgress(millis + "", String.valueOf(sec++), min + "");
                        } else {
                            millis = 0;
                            sec = 0;
                            publishProgress(millis + "", sec + "", String.valueOf(min++));
                        }

                        /*
                         * Romper el ciclo si ha terminado el tiempo limite indicado en las cajas de texto
                         */
                        if (min>=Integer.parseInt(cajaMinutes.getText().toString()) &&
                                sec>=Integer.parseInt(cajaSeconds.getText().toString()) &&
                                millis>=Integer.parseInt(cajaMillis.getText().toString()))
                            break;
                    } else if (radioDecrement.isChecked()) {
                        if (millis>0) {
                            publishProgress(String.valueOf(millis--), sec + "", min + ""); //Actualizamos valores
                            Thread.sleep(1);
                        } else if (sec>0){
                            millis = 999; //Inicializamos los millisegundos depués de que haya pasado un segundo
                            publishProgress(millis + "", String.valueOf(sec--), min + "");
                            //Thread.sleep(1000);
                        } else {
                            millis = 999; //Inicializamos los millisegundos depués de que haya pasado un segundo
                            sec = 59; //Inicializamos los segundos depués de que haya pasado un minuto
                            publishProgress(millis + "", sec + "", String.valueOf(min--));
                        }

                        /*
                         * Romper el ciclo si la cuenta regresiva ha llegado a cero
                         */
                        if (min<=0 && sec<=0 && millis<=0)
                            break;
                    }
                } catch(InterruptedException ie) {}
            }
            return "Stop";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            txtMillis.setText(values[0]);
            txtSeconds.setText(values[1]);
            txtMinutes.setText(values[2]);
        }
    }
}
