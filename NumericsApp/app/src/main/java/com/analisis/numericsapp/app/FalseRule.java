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

        EditText functionText = (EditText)findViewById(R.id.editText);
        f = new Funcion(functionText.getText().toString());
    }


    public static double [][]reglaFalsa(double xInf, double xSup, int iteraciones, double tolerancia) {

        double i[][] = new double[iteraciones][6];

        double yInf = f.evaluarFuncion(xInf);

        double ySup = f.evaluarFuncion(xSup);

        if (yInf == 0) {

            System.out.println(xInf + "es raiz");
            respuesta=xInf + "es raiz";
            response.setText(respuesta);
        } else if (ySup == 0) {

            System.out.println(xSup + "es raiz");
            respuesta=xSup + "es raiz";
            response.setText(respuesta);
        } else if ((yInf * ySup) > 0) {

            System.out.println("El intervalo no tiene raices");
            respuesta="El intervalo no tiene raices";
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

                System.out.println("xMedio=" + xMedio + "es una raiz");
                respuesta="xMedio=" + xMedio + "es una raiz";
                response.setText(respuesta);
            }
            else if (E <= tolerancia) {

                System.out.println("xMedio=" + xMedio + "es una raiz con tolerancia" + tolerancia);
                respuesta="xMedio=" + xMedio + "es una raiz con tolerancia" + tolerancia;
                response.setText(respuesta);
            } else {

                System.out.println("No dio :S ");
                respuesta="No dio :S ";
                response.setText(respuesta);
            }
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

}
