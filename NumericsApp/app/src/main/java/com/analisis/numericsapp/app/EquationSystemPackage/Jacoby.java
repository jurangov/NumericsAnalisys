package com.analisis.numericsapp.app.EquationSystemPackage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.analisis.numericsapp.app.AnswerTable;
import com.analisis.numericsapp.app.R;
import com.analisis.numericsapp.app.WrapperMatrix;

import static com.analisis.numericsapp.app.WrapperMatrix.MatrixA;
import static com.analisis.numericsapp.app.WrapperMatrix.MatrixOrder;

public class Jacoby extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jacoby);
    }


    private static double A [][];
    private static double b [];
    private static double Ab [][];
    private static double x [];
    public EditText[] editTextArray;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jacoby, menu);

        setupTableButton();
        SetUpInitialVector();

        A = WrapperMatrix.MatrixA;
        b = WrapperMatrix.VectorB;
        x = new double[MatrixOrder];

        return true;
    }

    private void SetUpInitialVector()
    {
        editTextArray = new EditText[MatrixOrder];
        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        TableRow tbrow2=new TableRow(this);

        for (int j = 0; j < MatrixOrder; j++)
        {
            EditText tv2=new EditText(this);
            tv2.setWidth(100);
            tv2.setId(j);
            tbrow2.addView(tv2);
            editTextArray[j] = tv2;
        }
        ll.addView(tbrow2);

        hsv.addView(ll);
        sv.addView(hsv);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout2);

        linearLayout.addView(sv, 0);

    }

    private void setupTableButton() {
        Button bt1 =(Button) findViewById(R.id.buttonJacobi);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText xvalueText = (EditText)findViewById(R.id.editText2);
                WrapperMatrix.tolerance = Double.parseDouble(xvalueText.getText().toString());

                EditText xSvalueText = (EditText)findViewById(R.id.editText);
                WrapperMatrix.iterations = Integer.parseInt(xSvalueText.getText().toString());

                for (int j = 0; j < MatrixOrder; j++)
                {
                    x[j] = Double.parseDouble(editTextArray[j].getText().toString());
                }

                jacobi(WrapperMatrix.tolerance, WrapperMatrix.iterations);
                startActivity(new Intent(getApplicationContext(),AnswerTable.class));
            }
        });
    }

    private void jacobi(double tolerance, int iterations)
    {
        int cont=0;
        double dispersion=tolerance+1;
        double x1[]=new double[x.length];
        Ab=new double[iterations][x.length];

        while((dispersion>tolerance)&&(cont<iterations))
        {
            //Calcula las nuevas x de acuerdo a la formula de jacobi
            x1 = calculateNewJacobi(x);
            //Calcula la dispersion con la formula de a norma especificada en el libro guia
            dispersion = calculateNorma(x1, x);
            //Actualiza las x anteriores con las nuevas
            x = x1;
            for (int i = 0; i < x.length; i++)
            {
                Ab[cont][i]=x[i];
            }
            cont++;
        }

        WrapperMatrix.matrix = Ab;

        //Checkeo condiciones de exito
        if(dispersion<tolerance)
        {
            //Recorre el vector de las x resultantes
            for (int i = 0; i < x1.length; i++)
            {

                System.out.println(x[i]+"es una aprox con tolerancia");
            }
        }
        else
        {
            System.out.println("fracaso en "+cont+" iteraciones");
        }
    }

    public double [] calculateNewJacobi(double x0[])
    {
        double x1[] = new double[x0.length];
        for (int i = 0; i < A.length; i++)
        {
            double suma = 0.0;
            for (int j = 0; j < A.length; j++)
            {
                if (j != i) {
                    suma += A[i][j] * x0[j];
                }
            }
            x1[i] = (b[i] - suma) / A[i][i];
        }

        return x1;
    }


    public double calculateNorma(double x1[], double x0[])
    {
        double deltas[]= new double[x1.length];

        for (int i = 0; i < deltas.length; i++)
        {
            deltas[i]   = Math.abs(x1[i]-x0[i]);
        }

        double norma=deltas[0];

        for (int i = 1; i < x0.length; i++)
        {
            if(norma < deltas[i])
            {
                norma = deltas[i];
            }
        }
        return norma;
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
}
