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
        //Ab = new double [n][n+1];
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

        TableLayout ll1=new TableLayout(this);
        HorizontalScrollView hsv1 = new HorizontalScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (int k = 0; k < n-1; k++)
        {
            TableRow tbrow1 = new TableRow(this);
            TextView tv1=new TextView(this);
            tv1.setId(k);
            tv1.setText(String.format("Step %d", k+1));
            tbrow1.addView(tv1);
            ll1.addView(tbrow1);

            for (int l = 0; l <= k; l++)
            {
                TableRow tbrow3 = new TableRow(this);

                for (int h = 0; h < n+1; h++)
                {
                    TextView tv3=new TextView(this);
                    tv3.setId(h);
                    tv3.setText(String.format("  |  %.2f", Ab[l][h]));
                    tbrow3.addView(tv3);
                }
                ll1.addView(tbrow3);
            }


            for (int i = k+1; i < n; i++)
            {
                TableRow tbrow2=new TableRow(this);

                //Se verifica que el elemento de la diagonal no sea 0
                if (Ab[k][k] != 0)
                {
                    //Se calcula el multiplicador para ejecutar el metodo
                    double Multiplicator = Ab[i][k] / Ab[k][k];

                    //Con el sigte ciclo se rellenan los espacios en blanco de la matriz resultante de la etapa con 0
                    for (int z = 0; z < k; z++)
                    {
                        TextView tv2=new TextView(this);
                        tv2.setId(z);
                        tv2.setText("  |  0.00");
                        tbrow2.addView(tv2);
                    }

                    //
                    for (int j = k; j < n+1; j++)
                    {
                        Ab[i][j] = Ab[i][j] - (Multiplicator * Ab[k][j]);
                        TextView tv2=new TextView(this);
                        tv2.setId(j);
                        tv2.setText(String.format("  |  %.2f", Ab[i][j]));
                        tbrow2.addView(tv2);
                    }
                    ll1.addView(tbrow2);
                }
                else
                {
                    //Division por 0
                }
            }


        }
        hsv1.addView(ll1);

        x = EquationSystemAuxiliar.RegresiveSustitutionAB(Ab, n);
        TableRow tbrow3 = new TableRow(this);

        TableLayout ll2=new TableLayout(this);
        HorizontalScrollView hsv2 = new HorizontalScrollView(this);
        //Se imprime el resultado
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
