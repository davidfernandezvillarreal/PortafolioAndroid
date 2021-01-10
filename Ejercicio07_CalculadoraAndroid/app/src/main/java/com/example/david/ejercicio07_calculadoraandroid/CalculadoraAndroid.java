package com.example.david.ejercicio07_calculadoraandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CalculadoraAndroid extends AppCompatActivity
{
    String numeroAnterior;
    EditText cajaResultado;
    private double numero1=0, numero2=0, resultado=0;
    private String operador1="", operador2="";
    private boolean igual=false, punto=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_android);

        cajaResultado = (EditText) (findViewById(R.id.cajaResultado));
    }

    //Método MAGICO que muestra los numeros en la caja de texto

    /*public void mostrarNumero(View v)
    {
        //obtener texto de la caja de texto
        numeroAnterior = cajaResultado.getText().toString();

        //Button btn = (Button) findViewById(v.getId());
        //cajaResultado.setText(numeroAnterior + btn.getText());

        cajaResultado.setText(numeroAnterior + ((Button) findViewById(v.getId())).getText());


    }*/

    /**/
    //agrega el numero 0
    public void agregar0(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "0" : cajaResultado.getText() + "0"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 1
    public void agragar1(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "1" : cajaResultado.getText() + "1"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 2
    public void agragar2(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "2" : cajaResultado.getText() + "2"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 3
    public void agragar3(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "3" : cajaResultado.getText() + "3"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 4
    public void agragar4(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "4" : cajaResultado.getText() + "4"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 5
    public void agragar5(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "5" : cajaResultado.getText() + "5"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 5
    public void agragar6(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "6" : cajaResultado.getText() + "6"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 7
    public void agragar7(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "7" : cajaResultado.getText() + "7"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 8
    public void agragar8(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "8" : cajaResultado.getText() + "8"));
        operador2 = "";
        igual = false;
    }
    //agrega el numero 9
    public void agragar9(View v)
    {
        cajaResultado.setText(((cajaResultado.getText().toString().equals("0") || cajaResultado.getText().toString().equals("0.0")) ? "9" : cajaResultado.getText() + "9"));
        operador2 = "";
        igual = false;
    }
    /**/

    //borra el ultimo dígito escrito
    public void botonRetroceso(View v)
    {
        if (cajaResultado.getText().length()!=1)
            cajaResultado.setText(cajaResultado.getText().toString().substring(0, cajaResultado.getText().length()-1));
        else
            cajaResultado.setText("0");
    }
    //borra todos los dígitos escritos
    public void botonC(View v)
    {
        cajaResultado.setText("0");
        numero1 = 0;
        numero2 = 0;
        resultado = 0;
        operador1 = "";
        operador2 = "";
    }
    //borra el operador escrito y todos los dígitos escritos después del operador, si no hay operador borra todos los digitos
    public void botonCE(View v)
    {
        numero2 = 0;
        cajaResultado.setText(numero1 + "");
        operador1 = "";
    }
    //agregara operadores y realizara operaciones
    public void agregarOperadorYRealizarOperacion(View v, String operador)
    {
        try {
            if (numero1==0 || igual)
            {
                if (operador1.equals("") && resultado==0)
                {
                    operador1 = operador;
                    numero1 = Double.parseDouble(cajaResultado.getText().toString());
                    cajaResultado.setText(numero1 + " " + operador + " ");
                }
                else
                {
                    if (numero1==0)
                    {
                        numero2 = Double.parseDouble(cajaResultado.getText().toString().substring(String.valueOf(numero1).length()+3, cajaResultado.getText().length()));
                        operador2 = operador;
                        switch (operador1)
                        {
                            case "/":
                                resultado = numero1 / numero2;
                                break;
                            case "*":
                                resultado = numero1 * numero2;
                                break;
                            case "+":
                                resultado = numero1 + numero2;
                                break;
                            case "-":
                                resultado = numero1 - numero2;
                                break;
                        }
                        operador1 = operador2;
                        cajaResultado.setText(resultado + " " + operador + " ");
                        numero1 = resultado;
                        numero2 = 0;
                    }
                    else
                    {
                        numero1 = Double.parseDouble(cajaResultado.getText().toString());
                        cajaResultado.setText(numero1 + " " + operador + " ");
                        operador1 = operador;
                    }
                }
            }
            else if (numero2==0)
            {
                operador2 = operador;
                if (resultado==0)
                {
                    numero2 = Double.parseDouble(cajaResultado.getText().toString().substring(String.valueOf(numero1).length()+3, cajaResultado.getText().length()));
                    switch (operador1)
                    {
                        case "/":
                            resultado = numero1 / numero2;
                            break;
                        case "*":
                            resultado = numero1 * numero2;
                            break;
                        case "+":
                            resultado = numero1 + numero2;
                            break;
                        case "-":
                            resultado = numero1 - numero2;
                            break;
                        default: System.out.println(operador1 + ", " + operador2);
                            break;
                    }
                    numero1 = resultado;
                    numero2 = 0;
                    cajaResultado.setText(resultado + " " + operador + " ");
                    operador1 = operador2;
                }
                else
                {
                    numero2 = Double.parseDouble(cajaResultado.getText().toString().substring(String.valueOf(numero1).length()+3, cajaResultado.getText().length()));
                    switch (operador1)
                    {
                        case "/":
                            resultado = numero1 / numero2;
                            break;
                        case "*":
                            resultado = numero1 * numero2;
                            break;
                        case "+":
                            resultado = numero1 + numero2;
                            break;
                        case "-":
                            resultado = numero1 - numero2;
                            break;
                    }
                    numero1 = resultado;
                    numero2 = 0;
                    cajaResultado.setText(resultado + " " + operador + " ");
                    operador1 = operador2;
                }
            }
        }
        catch (Exception e)
        {
            operador1 = operador;
            resultado = numero1;
            cajaResultado.setText(resultado + " " + operador + " ");
        }
        finally
        {
            punto=false;
        }
    }

    public void botonDivision(View v)
    {
        agregarOperadorYRealizarOperacion(v, "/");
    }

    public void botonMultiplicacion(View v)
    {
        agregarOperadorYRealizarOperacion(v, "*");
    }

    public void botonResta(View v)
    {
        agregarOperadorYRealizarOperacion(v, "-");
    }

    public void botonSuma(View v)
    {
        agregarOperadorYRealizarOperacion(v, "+");
    }
    //realiza la operacion que se indica y muestra un numero
    public void botonIgual(View v)
    {
        if (!igual)
        {
            try {
                numero2 = Double.parseDouble(cajaResultado.getText().toString().substring(String.valueOf(numero1).length()+3, cajaResultado.getText().length()));
                switch (operador1)
                {
                    case "/":
                        resultado = numero1 / numero2;
                        break;
                    case "*":
                        resultado = numero1 * numero2;
                        break;
                    case "+":
                        resultado = numero1 + numero2;
                        break;
                    case "-":
                        resultado = numero1 - numero2;
                        break;
                }
                numero1 = resultado;
                numero2 = 0;
                cajaResultado.setText(resultado + "");
                igual = true;
                operador1 = "";
            }
            catch (Exception e)
            {
                igual = true;
            }
        }
    }
    //agrega el punto para los numeros decimales
    public void agregarPunto(View v)
    {
        if (!punto)
        {
            cajaResultado.setText(((cajaResultado.getText().equals("0") || cajaResultado.getText().equals("0.0")) ? "0." : cajaResultado.getText() + "."));
            punto=true;
        }
    }
    //cambia el signo al número, si hay un operador entonces el singo se lo cambia al numero después del operador
    public void botonMasMenos(View v)
    {
        if (operador1.equals(""))
        {
            if (String.valueOf(cajaResultado.getText().charAt(0)).equals("-"))
                cajaResultado.setText(cajaResultado.getText().toString().substring(1, cajaResultado.getText().length()));
            else
                cajaResultado.setText("-" + cajaResultado.getText());
        }
        else
        {
            try {
                numero2 = Double.parseDouble(cajaResultado.getText().toString().substring(String.valueOf(numero1).length()+3, cajaResultado.getText().length()));
                numero2 = numero2 * -1;
                cajaResultado.setText(cajaResultado.getText().toString().substring(0, String.valueOf(numero1).length()+3) + numero2);
            }
            catch (Exception e){}
        }
    }
    //obtiene el porcentaje de los numeros ingresados
    public void botonPorcentaje(View v)
    {
        try {
            if (operador1.equals("*"))
            {
                numero2 = Double.parseDouble(cajaResultado.getText().toString().substring(String.valueOf(numero1).length()+3, cajaResultado.getText().length()));
                resultado = numero1 * numero2;
                resultado = resultado / 100;
                cajaResultado.setText(resultado + "");
            }
        }
        catch(Exception e){}
    }
    //obtiene la raiz cuadrada del numero ingresado
    public void botonRaizCuadrada(View v)
    {
        if (operador1.equals(""))
        {
            numero1 = Double.parseDouble(cajaResultado.getText().toString());
            if (numero1!=0)
            {
                numero1 = Math.sqrt(numero1);
                cajaResultado.setText(numero1 + "");
            }
            else
            {
                cajaResultado.setText("0");
            }
        }
        else
        {
            numero1 = Math.sqrt(numero1);
            cajaResultado.setText(numero1 + "");
        }
    }
    //obtiene el cuadrado del numero ingresado
    public void botonXCuadrada(View v)
    {
        if (operador1.equals(""))
        {
            numero1 = Double.parseDouble(cajaResultado.getText().toString());
            if (numero1!=0)
            {
                numero1 = Math.pow(numero1, 2);
                cajaResultado.setText(numero1 + "");
            }
            else
            {
                cajaResultado.setText("0");
            }
        }
        else
        {
            numero1 = Math.pow(numero1, 2);
            cajaResultado.setText(numero1 + "");
        }
    }
    //obtiene el reciproco del numero ingresado
    public void boton1SobreX(View v)
    {
        if (operador1.equals(""))
        {
            numero1 = Double.parseDouble(cajaResultado.getText().toString());
            if (numero1!=0)
            {
                numero1 = (1/numero1);
                cajaResultado.setText(numero1 + "");
            }
            else
            {
                cajaResultado.setText("NaN");
            }
        }
        else
        {
            numero1 = (1/numero1);
            cajaResultado.setText(numero1 + "");
        }
    }
}
