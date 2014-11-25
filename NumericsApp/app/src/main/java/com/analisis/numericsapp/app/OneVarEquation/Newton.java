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
                b.putSerializable("clase", "Newton");
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


    public void CalculateNewton(View v)
    {
        response = (TextView)findViewById(R.id.textView6);
        GetValues();

        response.setText("");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Newton");
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

        f = WrapperMatrix.GlobalFunction;

        EditText DfunctionText = (EditText)findViewById(R.id.editText2);
        fd = new Funcion(DfunctionText.getText().toString());

        WrapperMatrix.tableNames = new String[5];

        WrapperMatrix.tableNames[0] = "iter";
        WrapperMatrix.tableNames[1] = "Xn";
        WrapperMatrix.tableNames[2] = "F(Xn)";
        WrapperMatrix.tableNames[3] = "F´(Xn)";
        WrapperMatrix.tableNames[4] = "Error";
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
            System.out.println("xinicial" + xinicial + "is a root");

            respuesta="xinicial" + xinicial + "is a root";
            response.setText(respuesta);
        } else {

            if (error <= tolerancia) {

                System.out.println("xinicial" + xinicial + "is a root with error" + error);
                respuesta="xinicial" + xinicial + "is a root with error" + error;
                response.setText(respuesta);
            } else {

                if (yd == 0) {

                    System.out.println("Division by 0, imposible to execute");
                    respuesta="Division by 0, imposible to execute";
                    response.setText(respuesta);

                } else {

                    System.out.println("Root not found");
                    respuesta="Root not found";
                    response.setText(respuesta);

                }
            }

            WrapperMatrix.Counter = contador;

        }

        return i;
    }
    public void helpNewton(View h5)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Newton Help");
        alert.setMessage("this method is based in fix point\n" +
                "This method consist to find better approximations to the roots of real valued function.\n" +
                "is faster than secant\n" +
                "this methos have a proporcionality of his error is cuadratic\n" +
                "Given a function ƒ defined over the reals x, and its derivative ƒ', \n" +
                "we begin with a first guess x0 for a root of the function f.\n" +
                "\n" +
                "Having defined the function g, it must perform the following steps, as in the fixed-point method.\n" +
                "\n" +
                "You must select an initial approximation Xo\n" +
                "Calculated X1 = g (Xo)\n" +
                "An estimated X2 = g (X1)........................ Xn = g (Xn-1)\n");
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
