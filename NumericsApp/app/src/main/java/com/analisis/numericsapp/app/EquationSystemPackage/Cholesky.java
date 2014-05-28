package com.analisis.numericsapp.app.EquationSystemPackage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.analisis.numericsapp.app.R;
import com.analisis.numericsapp.app.WrapperMatrix;

public class Cholesky extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cholesky);
    }

    private static double A [][];
    private static double b [];
    private static double z[];
    private static double x[];
    private static double L[][];
    private static double U[][];

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cholesky, menu);

        A = WrapperMatrix.MatrixA;
        b = WrapperMatrix.VectorB;
        z = new double[b.length];
        x = new double[b.length];
        L = new double[A.length][A.length];
        U = new double[A.length][A.length];

        Procedure();

        return true;
    }

    private void Procedure()
    {
        LUFactorization();
    }

    private void LUFactorization()
    {
        int n = A.length;
        for (int k = 0; k < n; k++)
        {
            double sum = 0.0;
            for (int p = 0; p <= k - 1; p++)
            {
                sum += L[k][p] * U[p][k];
            }

            double diag = Math.abs(A[k][k]) - sum;
            if (diag < 0)
            {
                System.out.println("Diagonal Negativa, no se puede sacar raiz cuadrada de un negativo");
            }

            diag = Math.sqrt(diag);
            L[k][k] = diag;
            U[k][k] = diag;

            for (int i = k + 1; i < n; i++)
            {
                sum = 0;
                for (int p = 0; p <= k - 1; p++)
                {
                    sum += L[i][p] * U[p][k];
                }
                L[i][k] = (A[i][k] - sum) / U[k][k];
            }

            for (int j = k + 1; j < n; j++)
            {
                sum = 0;
                for (int p = 0; p <= k - 1; p++)
                {
                    sum += L[k][p] * U[p][j];
                }
                U[k][j] = (A[k][j] - sum) / L[k][k];
            }
        }

        z = EquationSystemAuxiliar.RegresiveSustitutionLU(L, b);
        x = EquationSystemAuxiliar.RegresiveSustitutionLU(U, z);
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
