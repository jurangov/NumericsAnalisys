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

public class FixedPoint extends ActionBarActivity
{

    private static int contadorFilas = 0;
    public static Funcion f = null;
    public static Funcion g = null;
    private static String respuesta;

    public static TextView response;
    public static double [][] matrix;

    public double xValue;
    public double Tol;
    public int iterations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_point);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fixed_point, menu);

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
                b.putSerializable("clase", "FixedPoint");
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


    public void CalculateFixedPoint(View v)
    {
        response = (TextView)findViewById(R.id.textView6);
        GetValues();

        //fixedPoint(xValue,iterations,Tol);
        matrix = fixedPoint(xValue,iterations,Tol);

        response.setText("");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Fixed Point");
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
        Tol = Double.parseDouble(deltaText.getText().toString());

        EditText iterationsText = (EditText)findViewById(R.id.editText4);
        iterations = Integer.parseInt(iterationsText.getText().toString());

        f = WrapperMatrix.GlobalFunction;

        EditText GfunctionText = (EditText)findViewById(R.id.editText5);
        g = new Funcion(GfunctionText.getText().toString());

        WrapperMatrix.tableNames = new String[4];

        WrapperMatrix.tableNames[0] = "iter";
        WrapperMatrix.tableNames[1] = "Xn";
        WrapperMatrix.tableNames[2] = "F(Xn)";
        WrapperMatrix.tableNames[3] = "Error";

    }

    public static double[][] fixedPoint(double xanterior, int iteraciones, double tolerancia) {

        double i[][] = new double[iteraciones][4];

        double y = f.evaluarFuncion(xanterior);

        double error = tolerancia + 1;

        int contador = 0;
        i[contador][0] = (double)contador;
        i[contador][1] = xanterior;
        i[contador][2] = y;
        i[contador][3] = error;
        contadorFilas++;

        while (y != 0 && error > tolerancia && contador < iteraciones) {

            double xactual = g.evaluarFuncion(xanterior);

            y = f.evaluarFuncion(xactual);

            error = Math.abs((xactual - xanterior)/xactual);

            xanterior = xactual;

            contador = contador + 1;

            i[contador][0] = (double)contador;
            i[contador][1] = xactual;
            i[contador][2] = y;
            i[contador][3] = error;
            contadorFilas++;

        }

        for (int j = 0; j < iteraciones; j++) {

            for (int k = 0; k < 4; k++) {

                System.out.print(i[j][k] + "                       ");
            }

            System.out.println("");
        }

        if (y == 0) {

            System.out.println("xanterior:" + xanterior + "is a root");
            respuesta="xanterior:" + xanterior + "is a root";
            response.setText(respuesta);
        } else {

            if (error < tolerancia) {

                System.out.println("xanterior:" + xanterior + "is a root with error" + error);
                respuesta="xanterior:" + xanterior + "is a root with error" + error;
                response.setText(respuesta);
            } else {

                System.out.println("root not found");
                respuesta="root not found";
                response.setText(respuesta);
            }

            WrapperMatrix.Counter = contador;

        }

        return i;
    }
}
