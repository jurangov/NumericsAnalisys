package com.analisis.numericsapp.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.analisis.numericsapp.app.EquationSystemPackage.EquationSystemMatrix;
import com.analisis.numericsapp.app.Interpolation.InterpolationNewton;
import com.analisis.numericsapp.app.Interpolation.Lagrange;


public class InterpolationMethod extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolation_method);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.interpolation_method, menu);

        setUpNewton();
        setUpLagrange();

        return true;
    }

    private void setUpNewton() {
        Button bt1 =(Button) findViewById(R.id.button);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InterpolationNewton.class));
            }
        });
    }

    private void setUpLagrange() {
        Button bt1 =(Button) findViewById(R.id.buttonLagrange);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Lagrange.class));
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
}
