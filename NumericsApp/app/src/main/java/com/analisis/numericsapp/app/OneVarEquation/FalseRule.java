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

public class FalseRule extends ActionBarActivity {

    double xInf;
    double xSup;
    int iterations;
    double tolerance;

    private static int contadorFilas=0;
    public static Funcion f= null;
    public static String respuesta;

    public static TextView response;
    public static double [][] matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_false_rule);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.false_rule, menu);

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
                b.putSerializable("clase", "FalseRule");
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

    public void CalculateFalseRule(View v)
    {
        response = (TextView)findViewById(R.id.textView7);
        GetValues();
        //reglaFalsa(xInf, xSup, iterations, tolerance);
        matrix = reglaFalsa(xInf, xSup, iterations, tolerance);

        response.setText("");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("False Rule");
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
        xInf = Double.parseDouble(xvalueText.getText().toString());

        EditText iterationsText = (EditText)findViewById(R.id.editText5);
        iterations = Integer.parseInt(iterationsText.getText().toString());

        EditText XsValueText = (EditText)findViewById(R.id.editText3);
        xSup = Double.parseDouble(XsValueText.getText().toString());

        EditText ToleranceText = (EditText)findViewById(R.id.editText4);
        tolerance = Double.parseDouble(ToleranceText.getText().toString());

        f = WrapperMatrix.GlobalFunction;
        WrapperMatrix.tableNames = new String[6];

        WrapperMatrix.tableNames[0] = "iter";
        WrapperMatrix.tableNames[1] = "Xi";
        WrapperMatrix.tableNames[2] = "Xu";
        WrapperMatrix.tableNames[3] = "Xmid";
        WrapperMatrix.tableNames[4] = "F(Xmid)";
        WrapperMatrix.tableNames[5] = "Error";
    }


    public static double [][]reglaFalsa(double xInf, double xSup, int iteraciones, double tolerancia) {

        double i[][] = new double[iteraciones][6];

        double yInf = f.evaluarFuncion(xInf);

        double ySup = f.evaluarFuncion(xSup);

        if (yInf == 0) {

            System.out.println(xInf + "is a root");
            respuesta=xInf + "is a root";
            response.setText(respuesta);
        } else if (ySup == 0) {

            System.out.println(xSup + "is a root");
            respuesta=xSup + "is a root";
            response.setText(respuesta);
        } else if ((yInf * ySup) > 0) {

            System.out.println("The interval has not roots");
            respuesta="The interval has not roots";
            response.setText(respuesta);
        } else {
            int contador=0;
            double xMedio = xInf - ((yInf * (xSup - xInf)) / (ySup - yInf));

            double yMedio = f.evaluarFuncion(xMedio);

            double E = (tolerancia + 1);
            i[contador][0] = (double)contador;
            i[contador][1] = xInf;
            i[contador][2] = xSup;
            i[contador][3] = xMedio;
            i[contador][4] = yMedio;
            i[contador][5] = E;

            contadorFilas++;

            contador = 1;

            while (yMedio != 0 && E > tolerancia && contador < iteraciones) {

                double xAuxiliar = xMedio;

                if (yInf * yMedio > 0) {
                    xInf = xMedio;
                    yInf = yMedio;

                } else {

                    xSup = xMedio;

                    ySup = yMedio;

                }

                xMedio = xInf - ((yInf * (xInf - xSup)) / (yInf - ySup));

                yMedio = f.evaluarFuncion(xMedio);

                E = Math.abs(xMedio - xAuxiliar);
                contador = contador + 1;
                i[contador][0] = contador;
                i[contador][1] = xInf;
                i[contador][2] = xSup;
                i[contador][3] = xMedio;
                i[contador][4] = yMedio;
                i[contador][5] = E;

                contadorFilas++;


            }

            for (int j = 0; j < iteraciones; j++) {

                for (int k = 0; k < 6; k++) {

                    System.out.print(i[j][k] + "                       ");
                }

                System.out.println("");
            }


            if (yMedio == 0)
            {

                System.out.println("xMedio=" + xMedio + "is a root");
                respuesta="xMedio=" + xMedio + "is a root";
                response.setText(respuesta);
            }
            else if (E <= tolerancia) {

                System.out.println("xMedio=" + xMedio + "is a root with tolerance" + tolerancia);
                respuesta="xMedio=" + xMedio + "is a root with tolerance" + tolerancia;
                response.setText(respuesta);
            } else {

                System.out.println("Failure");
                respuesta="Failure";
                response.setText(respuesta);
            }

            WrapperMatrix.Counter = contador;
        }

        return i ;
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
    public void helpFalseRule(View h3)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("False Rule Help");
        alert.setMessage("firs you input the values of x, for the low interval you put the value in x and for the superior value you put the value in xs, the tolarance and the iteration are the stopping criterions\n" +
                "\n" +
                "This is how false rule works: \n" +
                "\n" +
                "It consist to find the intersection of a line formed by point a and b in the x-axi.\n" +
                "If you have two points (a, f (a)) and (b, f (b)) and draw the line joining these two points,\n" +
                "we see that a point is below the x-axis and another above this, and an intermediate point (Xm, 0),\n" +
                "with this mid-point limits can compare and get a new interval\n" +
                "If f (A) f (B) <0, then the root is at the left side of the interval.\n" +
                "If f (A) f (B)> 0, then the root is on the right side of the interval.\n" +
                "\n" +
                "To find the intersection of the line with the X-axis use the following formula:\n" +
                "\n" +
                "Xm = a - ((f (a) * (b - a)) / (f (b) - f (a)))");
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
