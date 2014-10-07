package com.analisis.numericsapp.app.EquationSystemPackage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.analisis.numericsapp.app.R;
import com.analisis.numericsapp.app.WrapperMatrix;

public class Doolittle extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doolittle);
    }

    private static double A [][];
    private static double b [];
    private static double z[];
    private static double x[];
    private static double L[][];
    private static double U[][];
    private TableLayout ll1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doolittle, menu);

        A = WrapperMatrix.MatrixA;
        b = WrapperMatrix.VectorB;
        z = new double[b.length];
        x = new double[b.length];
        L = new double[A.length][A.length];
        U = new double[A.length][A.length];

        Procedure();

        return true;
    }

    public void Procedure()
    {
        MatrixSetUp(L);
        MatrixSetUp(U);
        LUFactorization();
    }

    public void LUFactorization()
    {
        ScrollView sv = new ScrollView(this);

        ll1=new TableLayout(this);
        HorizontalScrollView hsv1 = new HorizontalScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        int n = A.length;
        for (int k = 0; k < n; k++)
        {
            //Se calcula el primer valor de U
            double sum = 0.0;
            for (int p = 0; p <= k-1; p++)
            {
                sum += L[k][p] * U[p][k];
            }

            U[k][k] = A[k][k] - sum;

            for (int i = k + 1; i < n; i++)
            {
                sum = 0;
                for (int p = 0; p <= k - 1; p++)
                {
                    sum += L[i][p] * U[p][k];
                }

                L[i][k]=(A[i][k] - sum)/U[k][k];
            }

            for (int j = k + 1; j < n; j++)
            {
                sum = 0;
                for (int p = 0; p <= k - 1; p++)
                {
                    sum += L[k][p] * U[p][j];
                }

                U[k][j] = (A[k][j] - sum);
            }
        }

        z = EquationSystemAuxiliar.RegresiveSustitutionLU(L, b);
        x = EquationSystemAuxiliar.RegresiveSustitutionLU(U, z);


        TableRow tbrow1 = new TableRow(this);
        TextView tv1=new TextView(this);
        tv1.setText("L");
        tbrow1.addView(tv1);
        ll1.addView(tbrow1);
        showMatrix(L);

        TableRow tbrow2 = new TableRow(this);
        TextView tv2=new TextView(this);
        tv2.setText("U");
        tbrow2.addView(tv2);
        ll1.addView(tbrow2);
        showMatrix(U);

        hsv1.addView(ll1);

        TableRow tbrow3 = new TableRow(this);

        TableLayout ll2=new TableLayout(this);
        HorizontalScrollView hsv2 = new HorizontalScrollView(this);
        //Se imprime el resultado de las x
        for (int i = 0; i < x.length; i++)
        {
            TextView tv3=new TextView(this);
            tv3.setId(i);
            tv3.setText(String.format("X%d : %.2f ", i+1, x[i]));
            tbrow3.addView(tv3);
        }
        ll2.addView(tbrow3, 0);

        hsv2.addView(ll2);
        sv.addView(hsv1);
        linearLayout.addView(hsv2, 0);
        linearLayout.addView(sv, 0);
        setContentView(linearLayout);
    }

    public void showMatrix(double [][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            TableRow tbrow3 = new TableRow(this);

            for (int j = 0; j < matrix.length; j++)
            {
                TextView tv3=new TextView(this);
                tv3.setId(j);
                tv3.setText(String.format("  |  %.2f", matrix[i][j]));
                tbrow3.addView(tv3);
            }
            ll1.addView(tbrow3);
        }
    }

    public double [][] MatrixSetUp(double matriz[][])
    {
        for (int i = 0; i < matriz.length; i++)
        {
            for (int j = 0; j < matriz.length; j++)
            {
                if (i != j)
                {
                    matriz[i][j] = 0;
                }
                else
                {
                    matriz[i][j] = 1;
                }
            }
        }

        return matriz;
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
