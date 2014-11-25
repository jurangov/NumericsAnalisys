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

public class MultipleRoot extends ActionBarActivity
{

    private static int contadorFilas = 0;
    public static Funcion f = null;
    public static Funcion f1d = null;
    public static Funcion f2d = null;
    private static String respuesta;

    public static TextView response;
    public static double [][] matrix;

    public double xValue;
    public double Tol;
    public int iterations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_root);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.multiple_root, menu);

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
                b.putSerializable("clase", "MultipleRoot");
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


    public void CalculateMultipleRoot(View v)
    {
        response = (TextView)findViewById(R.id.textView6);
        GetValues();

        //fixedPoint(xValue,iterations,Tol);
        matrix = multipleRoots(xValue, Tol, iterations);

        response.setText("");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Multiple Roots");
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
        EditText xvalueText = (EditText)findViewById(R.id.editText6);
        xValue = Double.parseDouble(xvalueText.getText().toString());

        EditText deltaText = (EditText)findViewById(R.id.editText4);
        Tol = Double.parseDouble(deltaText.getText().toString());

        EditText iterationsText = (EditText)findViewById(R.id.editText2);
        iterations = Integer.parseInt(iterationsText.getText().toString());

        f = WrapperMatrix.GlobalFunction;

        EditText DfunctionText = (EditText)findViewById(R.id.editText5);
        f1d = new Funcion(DfunctionText.getText().toString());

        EditText D2functionText = (EditText)findViewById(R.id.editText3);
        f2d = new Funcion(D2functionText.getText().toString());

        WrapperMatrix.tableNames = new String[6];

        WrapperMatrix.tableNames[0] = "iter";
        WrapperMatrix.tableNames[1] = "Xi";
        WrapperMatrix.tableNames[2] = "F(Xi)";
        WrapperMatrix.tableNames[3] = "F(Xi)";
        WrapperMatrix.tableNames[4] = "F´´(Xi)";
        WrapperMatrix.tableNames[5] = "Error";
    }



    public static double[][] multipleRoots(double xinicial, double tolerancia, int iteraciones) {

        double i[][] = new double[iteraciones][6];

        double y = f.evaluarFuncion(xinicial);
        double fd = f1d.evaluarFuncion(xinicial);
        double fdd = f2d.evaluarFuncion(xinicial);
        double denominador = ((fd * fd) - (y * fdd));
        double error = tolerancia + 1;
        int contador = 0;
        i[contador][0] = contador;
        i[contador][1] = xinicial;
        i[contador][2] = y;
        i[contador][3] = fd;
        i[contador][4] = fdd;
        i[contador][5] = error;
        double xsiguiente = 0;

        while (y != 0 && error > tolerancia && denominador != 0 && contador < iteraciones) {

            xsiguiente = xinicial - ((y * fd) / denominador);
            y = f.evaluarFuncion(xsiguiente);
            fd = f1d.evaluarFuncion(xsiguiente);
            fdd = f2d.evaluarFuncion(xsiguiente);
            error = Math.abs(xsiguiente - xinicial);
            denominador = ((fd * fd) - (y * fdd));
            xinicial = xsiguiente;
            contador = contador + 1;
            i[contador][0] = contador;
            i[contador][1] = xsiguiente;
            i[contador][2] = y;
            i[contador][3] = fd;
            i[contador][4] = fdd;
            i[contador][5] = error;

            contadorFilas++;

        }

        for (int j = 0; j < iteraciones; j++) {

            for (int k = 0; k < 7; k++) {

                System.out.print(i[j][k] + "                       ");
            }

            System.out.println("");
        }

        if (y == 0) {

            System.out.println("xsiguiente:" + xsiguiente + " is a root");
            respuesta="xsiguiente:" + xsiguiente + " is a root";
            response.setText(respuesta);
        } else {

            if (error <= tolerancia) {

                System.out.println("xinicial:" + xinicial + " is a root with error");
                respuesta="xinicial:" + xinicial + " is a root with error";
                response.setText(respuesta);
            } else {

                if (denominador == 0) {

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
    public void helpMulRoots(View h7)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Multiple Roots Help");
        alert.setMessage("The multiple root method is also known as Newton's method improved, and its structure is basically very similar except that they must find the second derivative and must take into account the following expression :\n" +
                "\n" +
                "Xn +1 = Xn - ( (f ( Xn) * f ' (Xn) ) / ( ( f ' (Xn) ^ 2 - (f ( Xn) * f'' ( Xn )))\n" +
                "\n" +
                "Having defined the above expression , we proceed in a manner similar to the method of Newton\n" +
                "You must select initial approximation Xo\n" +
                "It is estimated ---------- X1 = Xn = Expression Expression (n -1 )\n" +
                "And repeats the previous step until you get to an approximation");
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
