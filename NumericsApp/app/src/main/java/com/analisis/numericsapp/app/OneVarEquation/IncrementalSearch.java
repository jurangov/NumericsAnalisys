package com.analisis.numericsapp.app.OneVarEquation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.analisis.numericsapp.app.AnswerTable;
import com.analisis.numericsapp.app.Funcion;
import com.analisis.numericsapp.app.R;
import com.analisis.numericsapp.app.WrapperMatrix;

public class IncrementalSearch extends ActionBarActivity
{

    public double xValue;
    public int delta;
    public int iterations;

    private static int contadorFilas=0;
    public static Funcion f= null;
    public static String respuesta;

    public static TextView response;
    public static double [][] matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incremental_search);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.incremental_search, menu);

        setupButtonTableButton();
        return true;
    }

    private void setupButtonTableButton()
    {
        Button bt1 =(Button) findViewById(R.id.TableButton);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("clase", "IncrementalSearch");
                myIntent.setClass(getApplicationContext(), AnswerTable.class);
                myIntent.putExtras(b);
                startActivity(myIntent);
            }
        });
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


    public void CalculateIncrementalSearch(View v)
    {
        response = (TextView)findViewById(R.id.textView5);
        GetValues();

        matrix = busquedaIncremental(xValue, delta, iterations);

        response.setText("");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Incremental Search");
        alert.setMessage(respuesta);

        // Set an EditText view to get user input
        // final TextView answer = new TextView(this);
        // answer.setText(response.getText());
        // alert.setView(answer);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {



            }
        });

        alert.show();

        WrapperMatrix.matrix = matrix;
    }

    public void GetValues()
    {
        EditText xvalueText = (EditText)findViewById(R.id.editText2);
        xValue = Double.parseDouble(xvalueText.getText().toString());

        EditText deltaText = (EditText)findViewById(R.id.editText3);
        delta = Integer.parseInt(deltaText.getText().toString());

        EditText iterationsText = (EditText)findViewById(R.id.editText4);
        iterations = Integer.parseInt(iterationsText.getText().toString());

        f = WrapperMatrix.GlobalFunction;
        WrapperMatrix.tableNames = new String[3];

        WrapperMatrix.tableNames[0] = "iter";
        WrapperMatrix.tableNames[1] = "Xn";
        WrapperMatrix.tableNames[2] = "F(Xn)";
    }

    public static double[][]  busquedaIncremental(double xanterior, double incremento, int iteraciones)
    {
        //EL unico al que no le sobra una llave abajo
        double i[][] = new double[iteraciones][3];
        //double yanterior = funcion(xanterior);
        double yanterior = f.evaluarFuncion(xanterior);

        if (yanterior == 0)
        {
            System.out.println(xanterior + " is a root");
            respuesta=xanterior + " is a root";
            response.setText(respuesta);
        }
        else
        {
            int contador=0;
            double xactual = (xanterior + incremento);
//              double yactual = funcion(xactual);
            double yactual = f.evaluarFuncion(xactual);
            yactual = f.evaluarFuncion(xactual);
            i[contador][0] = contador;
            i[contador][1] = xanterior;
            i[contador][2] = yanterior;
            System.out.println(contador);
            contadorFilas++;
            contador = 1;
            while (yactual * yanterior > 0 && contador <= iteraciones)
            {
                xanterior = xactual;
                yanterior = yactual;
                i[contador][0] = contador;
                i[contador][1] = xactual;
                i[contador][2] = yactual;
                xactual = xanterior + incremento;
                //yactual = funcion(xactual);
                yactual = f.evaluarFuncion(xactual);
                System.out.println(contador);
                contadorFilas++;
                contador = contador + 1;
            }
            i[contador][0] = contador;
            i[contador][1] = xactual;
            i[contador][2] = yactual;
            System.out.println(contador);
            contadorFilas++;
            if (yactual == 0)
            {
                System.out.println(xactual + " is a root");
                respuesta= xactual + " is a root";
                response.setText(respuesta);
            }
            else
            {
                if (yactual * yanterior < 0)
                {
                    respuesta="los puntos:" + xanterior + ";" + xactual + " made an interval";
                    response.setText(respuesta);
                    // System.out.println(i);
                }
                else
                {
                    System.out.println("Root not found");
                    respuesta="Root not found";
                    response.setText(respuesta);
                    // System.out.println(i);
                }
            }

            WrapperMatrix.Counter = contador;
            // System.out.println("xanterior:"+xanterior);
            // System.out.println("yanterior:"+yanterior);
        }
        return i;
    }

    public static void SetContador(){

        contadorFilas=0;

    }

    public static int getContador(){

        return contadorFilas;

    }
    public static String SetRespuesta(){
        return respuesta;
    }

    public static void SetFuncion(Funcion funcion){

        f=funcion;

    }
    public void helpbi(View h)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Incremental Search Help");
        alert.setMessage("It consist in start with value of x evaluated in the function with little's increments in order to search a root or an interval with contents a root(s), this what we get it's because we finding a sign change in the evaluation or get a zero. Which we found the interval we can use another method to find the root(s) ");
        // Set an EditText view to get user input
        // final TextView answer = new TextView(this);
        // answer.setText(response.getText());
        // alert.setView(answer);

        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {



            }
        });

        alert.show();
    }
}
