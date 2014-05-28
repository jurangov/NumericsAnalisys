package com.analisis.numericsapp.app.EquationSystemPackage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.analisis.numericsapp.app.R;
import com.analisis.numericsapp.app.WrapperMatrix;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jacoby, menu);

        A = WrapperMatrix.MatrixA;
        b = WrapperMatrix.VectorB;
        x = WrapperMatrix.jacobyX;

        jacoby(WrapperMatrix.tolerance, WrapperMatrix.iterations);

        return true;
    }

    private void jacoby(double tolerance, int iterations)
    {
        
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
