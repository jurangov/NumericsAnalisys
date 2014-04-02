package com.analisis.numericsapp.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Secant extends ActionBarActivity {

    private static int contadorFilas=0;
    public static Funcion f=null;
    private static String respuesta;

    double xinicial;
    double xsiguiente;
    int iteraciones;
    double tolerancia;

    public static TextView response;
    public static double [][] matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secant);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.secant, menu);
        setupTableButton();

        return true;
    }

    private void setupTableButton() {

        Button bt1 =(Button) findViewById(R.id.TableButton);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),AnswerTable.class));
            }
        });
    }

    public void CalculateSecant(View v)
    {
        response = (TextView)findViewById(R.id.textView5);
        GetValues();

        //fixedPoint(xValue,iterations,Tol);
        matrix = secante(xinicial, xsiguiente, iteraciones, tolerancia);
        WrapperMatrix.matrix = matrix;
    }

    public void GetValues()
    {
        EditText xvalueText = (EditText)findViewById(R.id.editText2);
        xinicial = Double.parseDouble(xvalueText.getText().toString());

        EditText xSvalueText = (EditText)findViewById(R.id.editText3);
        xsiguiente = Double.parseDouble(xSvalueText.getText().toString());

        EditText iterationsText = (EditText)findViewById(R.id.editText4);
        iteraciones = Integer.parseInt(iterationsText.getText().toString());

        EditText toleranceText = (EditText)findViewById(R.id.editText4);
        tolerancia = Integer.parseInt(toleranceText.getText().toString());

        EditText functionText = (EditText)findViewById(R.id.editText);
        f = new Funcion(functionText.getText().toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static double [][] secante (double xinicial , double xsiguiente, int iteraciones , double tolerancia){

        double i[][] = new double[iteraciones][5];

        double y = f.evaluarFuncion(xinicial);
        double y1 = f.evaluarFuncion(xsiguiente);

        if (y == 0) {
            System.out.println("Xinicial es raiz");
            respuesta="Xinicial es raiz";
            response.setText(respuesta);
        } else {
            double error = (tolerancia + 1);
            double denominador = (y1 - y);
            int contador = 0;
            double x2 = 0;
            i[contador][0] = contador;
            i[contador][1] = xsiguiente;
            i[contador][2] = y1;
            i[contador][3] = denominador;
            i[contador][4] = error;

            contadorFilas++;
            System.out.println(xinicial);

            System.out.println(xsiguiente);

            while (y1 != 0 && error > tolerancia && denominador != 0 && contador < iteraciones) {
                x2 = xsiguiente + (-y1 * (xsiguiente - xinicial) / denominador);
                error = Math.abs(x2 - xinicial);
                xinicial = xsiguiente;
                y = y1;
                xsiguiente = x2;
                y1 = f.evaluarFuncion(xsiguiente);

                denominador = y1 - y;
                contador = contador + 1;

                i[contador - 1][0] = contador;
                i[contador - 1][1] = xsiguiente;
                i[contador - 1][2] = y1;
                i[contador - 1][3] = denominador;
                i[contador - 1][4] = error;

                contadorFilas++;
            }

            for (int j = 0; j < iteraciones; j++) {

                for (int k = 0; k < 5; k++) {

                    System.out.print(i[j][k] + "                       ");
                }

                System.out.println("");
            }

            if (error <= tolerancia) {
                System.out.println("x2=" + x2 + "Es raiz con error" + error);
                respuesta="x2= " + x2 + "Es raiz con error " + error;
                response.setText(respuesta);
            } else {
                if (y1 == 0) {

                    System.out.println("xsiguiente=" + xsiguiente + "Es Raiz");
                    respuesta="xsiguiente= " + xsiguiente + "Es Raiz";
                    response.setText(respuesta);
                } else {
                    if (denominador == 0) {
                        System.out.println("Division por 0 , imposible de realizar con este metodo");
                        respuesta="Division por 0 , imposible de realizar con este metodo";
                        response.setText(respuesta);
                    } else {

                        System.out.println("No se encontro raiz");
                        respuesta="No se encontro raiz";
                        response.setText(respuesta);

                    }
                }
            }
        }
        return i;
    }

    public static int getContador(){

        return contadorFilas;

    }
    public static String SetRespuesta(){
        return respuesta;
    }

    public static void SetContador(){

        contadorFilas=0;

    }

    public static void SetFuncion(Funcion funcion){

        f=funcion;

    }

}
