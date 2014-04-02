package com.analisis.numericsapp.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Newton extends ActionBarActivity
{

    private static int contadorFilas=0;
    public static Funcion f=null;
    public static Funcion fd=null;
    private static String respuesta;

    public static TextView response;
    public static double [][] matrix;

    public double xValue;
    public double Tol;
    public int iterations;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newton);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.newton, menu);
        return true;
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


    public void CalculateNewton(View v)
    {
        response = (TextView)findViewById(R.id.textView6);
        GetValues();

        //fixedPoint(xValue,iterations,Tol);
        matrix = newton(xValue, Tol, iterations);
        WrapperMatrix.matrix = matrix;
    }

    public void GetValues()
    {
        EditText xvalueText = (EditText)findViewById(R.id.editText3);
        xValue = Double.parseDouble(xvalueText.getText().toString());

        EditText deltaText = (EditText)findViewById(R.id.editText4);
        Tol = Double.parseDouble(deltaText.getText().toString());

        EditText iterationsText = (EditText)findViewById(R.id.editText5);
        iterations = Integer.parseInt(iterationsText.getText().toString());

        EditText functionText = (EditText)findViewById(R.id.editText);
        f = new Funcion(functionText.getText().toString());

        EditText DfunctionText = (EditText)findViewById(R.id.editText2);
        fd = new Funcion(DfunctionText.getText().toString());
    }


    public static double [][] newton (double xinicial , double tolerancia , int iteraciones ){

        double i[][] = new double[iteraciones][5];

        double y = f.evaluarFuncion(xinicial);
        double yd = fd.evaluarFuncion(xinicial);
        double error = tolerancia + 1;
        int contador = 0;
        i[contador][0] = (double)contador;
        i[contador][1] = xinicial;
        i[contador][2] = y;
        i[contador][3] = yd;
        i[contador][4] = error;
        contadorFilas++;
        while (y != 0 && error > tolerancia && yd != 0 && contador < iteraciones) {

            double xsiguiente = xinicial - (y / yd);
            y = f.evaluarFuncion(xsiguiente);
            yd = fd.evaluarFuncion(xsiguiente);
            error = Math.abs(xsiguiente - xinicial);
            xinicial = xsiguiente;
            contador = contador + 1;
            i[contador][0] = (double)contador;
            i[contador][1] = xsiguiente;
            i[contador][2] = y;
            i[contador][3] = yd;
            i[contador][4] = error;
            contadorFilas++;
        }
        for (int j = 0; j < iteraciones; j++) {

            for (int k = 0; k < 5; k++) {

                System.out.print(i[j][k] + "                       ");
            }

            System.out.println("");
        }

        if (y == 0) {
            System.out.println("xinicial" + xinicial + "es raiz");

            respuesta="xinicial" + xinicial + "es raiz";
            response.setText(respuesta);
        } else {

            if (error <= tolerancia) {

                System.out.println("xinicial" + xinicial + "es raiz con error" + error);
                respuesta="xinicial" + xinicial + "es raiz con error" + error;
                response.setText(respuesta);
            } else {

                if (yd == 0) {

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

        return i;
    }
}
