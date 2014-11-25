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

        response.setText("");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Secant");
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
        xinicial = Double.parseDouble(xvalueText.getText().toString());

        EditText xSvalueText = (EditText)findViewById(R.id.editText3);
        xsiguiente = Double.parseDouble(xSvalueText.getText().toString());

        EditText iterationsText = (EditText)findViewById(R.id.editText4);
        iteraciones = Integer.parseInt(iterationsText.getText().toString());

        EditText toleranceText = (EditText)findViewById(R.id.editText4);
        tolerancia = Integer.parseInt(toleranceText.getText().toString());

        f = WrapperMatrix.GlobalFunction;
        WrapperMatrix.tableNames = new String[4];

        WrapperMatrix.tableNames[0] = "iter";
        WrapperMatrix.tableNames[1] = "Xn";
        WrapperMatrix.tableNames[2] = "F(Xn)";
        WrapperMatrix.tableNames[3] = "Error";

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
            System.out.println("Xinicial is a root");
            respuesta="Xinicial is a root";
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
                System.out.println("x2=" + x2 + " is a root with error" + error);
                respuesta="x2= " + x2 + " is a root with error" + error;
                response.setText(respuesta);
            } else {
                if (y1 == 0) {

                    System.out.println("xsiguiente=" + xsiguiente + " is a root");
                    respuesta="xsiguiente= " + xsiguiente + " is a root";
                    response.setText(respuesta);
                } else {
                    if (denominador == 0) {
                        System.out.println("Division by 0, imposible to execute");
                        respuesta="Division by 0, imposible to execute";
                        response.setText(respuesta);
                    } else {

                        System.out.println("root not found");
                        respuesta="Root not found";
                        response.setText(respuesta);

                    }
                }
            }

            WrapperMatrix.Counter = contador;
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
    public void helpSecant(View h6)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Secant Help");
        alert.setMessage("This method is a variant of the Newton method but instead of calculate the function in the point,\n" +
                "it approximates the slope of the straight line connecting the function evaluated at the point of \n" +
                "study and at the point of the previous iteration.\n" +
                "\n" +
                "The parameters for this method are:\n" +
                "\n" +
                "\n" +
                "\n" +
                "Initials values X0 and X1, tolerance and iterations. \n" +
                "On this method change how we find xn+1\n" +
                "this meths is less faster then newton method but is more used because we don't have to know the derivate\n" +
                "\n" +
                "This is the most used method in this section ");
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
