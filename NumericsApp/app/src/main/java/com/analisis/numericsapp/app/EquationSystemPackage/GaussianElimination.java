package com.analisis.numericsapp.app.EquationSystemPackage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.analisis.numericsapp.app.R;
import com.analisis.numericsapp.app.WrapperMatrix;

public class GaussianElimination extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaussian_elimination);
    }


    private double A [][];
    private double b [];
    private double Ab [][];
    private int n;
    private double x [];


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gaussian_elimination, menu);

        A = WrapperMatrix.MatrixA;
        b = WrapperMatrix.VectorB;
        n = A.length;
        Ab = new double [n][n+1];
        x = new double[n];

        Procedure();

        return true;
    }



    public void Procedure()
    {
        Ab = EquationSystemAuxiliar.AumentedMatrix(A, b);
        GaussianEliminationMethod();
    }


    public void GaussianEliminationMethod()
    {

        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        for (int k = 0; k < n; k++)
        {
            TableRow tbrow1 = new TableRow(this);
            TextView tv1=new TextView(this);
            tv1.setId(k);
            tv1.setText(String.format("Step %d", k));
            tbrow1.addView(tv1);
            ll.addView(tbrow1);

            for (int i = k+1; i < n; i++)
            {
                TableRow tbrow2=new TableRow(this);

                //Se verifica que el elemento de la diagonal no sea 0
                if (Ab[k][k] != 0)
                {
                    double Multiplicator = Ab[i][k] / Ab[k][k];
                    for (int j = k; j < n+1; j++)
                    {
                        Ab[i][j] = Ab[i][j] - (Multiplicator * Ab[k][j]);
                        TextView tv2=new TextView(this);
                        tv2.setId(j);
                        tv2.setText(String.format("  |  %.3f", Ab[i][j]));
                        tbrow2.addView(tv2);
                    }
                    ll.addView(tbrow2);
                }
                else
                {
                    //Division por 0
                }
            }

            x = EquationSystemAuxiliar.RegresiveSustitutionAB(Ab, n);
            TableRow tbrow3 = new TableRow(this);

            //Se imprime el resultado
            for (int i = 0; i < x.length; i++)
            {
                TextView tv3=new TextView(this);
                tv3.setId(i);
                tv3.setText(String.format("X%d : %d", i+1, x[i]));
                tbrow3.addView(tv3);
            }
            ll.addView(tbrow3);

            hsv.addView(ll);
            sv.addView(hsv);
            setContentView(sv);
        }
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
