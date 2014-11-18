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
import com.analisis.numericsapp.app.OneVariableEquation;
import com.analisis.numericsapp.app.R;
import com.analisis.numericsapp.app.WrapperMatrix;

public class Bisection extends ActionBarActivity {

    public double xValue;
    public int iterations;
    public double XsValue;
    public double tolerance;

    private static int contadorFilas=0;
    public static Funcion f= null;
    private static String respuesta;

    public static TextView response;
    public static double [][] matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bisection);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bisection, menu);

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
                b.putSerializable("clase", "Bisection");
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

    public void CalculateBisection(View v)
    {
        response = (TextView)findViewById(R.id.textView7);
        GetValues();

        //bisection(xValue, XsValue, iterations, tolerance);
        matrix = bisection(xValue, XsValue, iterations, tolerance);
        response.setText("");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Bisection");
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

        EditText iterationsText = (EditText)findViewById(R.id.editText5);
        iterations = Integer.parseInt(iterationsText.getText().toString());

        EditText XsValueText = (EditText)findViewById(R.id.editText3);
        XsValue = Double.parseDouble(XsValueText.getText().toString());

        EditText ToleranceText = (EditText)findViewById(R.id.editText4);
        tolerance = Double.parseDouble(ToleranceText.getText().toString());

        f = WrapperMatrix.GlobalFunction;
        WrapperMatrix.tableNames = new String[6];

        WrapperMatrix.tableNames[0] = "iter";
        WrapperMatrix.tableNames[1] = "Xi";
        WrapperMatrix.tableNames[2] = "Xu";
        WrapperMatrix.tableNames[3] = "Xmid";
        WrapperMatrix.tableNames[4] = "Ymid";
        WrapperMatrix.tableNames[5] = "Error";
    }


    public static double[][] bisection(double xInf, double xSup, int iterations, double tolerance) {
        double i[][] = new double[iterations][6];
        double yInf = f.evaluarFuncion(xInf);
        double ySup = f.evaluarFuncion(xSup);
        if (yInf == 0) {
            System.out.println(xInf + "xInf is a root");
            respuesta=xInf + "xInf is a root";
            response.setText(respuesta);
        } else if (ySup == 0) {
            System.out.println(xSup + "xSup is a root");
            respuesta=xSup + "xSup is a root";
            response.setText(respuesta);
        } else if ((yInf * ySup) > 0) {
            System.out.println("Wrong interval");
            respuesta="Wrong Interval";
            response.setText(respuesta);
        } else {

            int contador = 0;
            double xMedio = (xInf + xSup) / 2;
            double yMedio = f.evaluarFuncion(xMedio);
            double E = tolerance + 1;
            i[contador][0] = (double)contador;
            i[contador][1] = xInf;
            i[contador][2] = xSup;
            i[contador][3] = xMedio;
            i[contador][4] = yMedio;
            i[contador][5] = E;
            contadorFilas++;
            contador = 1;
            while (yMedio != 0 && E > tolerance &&  contador <= iterations)
            {
                if ((yInf * yMedio) < 0) {
                    xSup = xMedio;
                    ySup = yMedio;
                } else {
                    xInf = xMedio;
                    yInf = yMedio;
                }
                double Aux = xMedio;


                xMedio = (xSup + xInf) / 2;
                yMedio = f.evaluarFuncion(xMedio);
                E = Math.abs(xMedio - Aux);
                i[contador][0] = contador;
                i[contador][1] = xInf;
                i[contador][2] = xSup;
                i[contador][3] = xMedio;
                i[contador][4] = yMedio;
                i[contador][5] = E;
                contadorFilas++;
                contador = contador + 1;
            }
            for (int j = 0; j < iterations; j++) {

                for (int k = 0; k < 6; k++) {
                    System.out.print(i[j][k] + "                       ");
                }
                System.out.println("");
            }

            if (yMedio == 0) {
                System.out.println("xMedio=" + xMedio + "is a root");
                respuesta="xMedio=" + xMedio + "is a root";
                response.setText(respuesta);
            } else if (E < tolerance) {
                System.out.println("xMedio=" + xMedio + "is a root with error " + E);
                respuesta="xMedio=" + xMedio + "is a root with error " + E;
                response.setText(respuesta);
            } else {
                System.out.println("failure");
                respuesta="failure";
                response.setText(respuesta);
            }

            WrapperMatrix.Counter = contador;
        }

        return i;
    }
    public static String SetRespuesta(){
        return respuesta;
    }
    public static int getContador(){
        return contadorFilas;
    }
    public static void SetContador(){
        contadorFilas=0;
    }
    public static void SetFuncion(Funcion funcion){
        f=funcion;
    }

}
