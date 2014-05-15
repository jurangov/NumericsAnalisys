package com.analisis.numericsapp.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.analisis.numericsapp.app.EquationSystemPackage.GaussianElimination;
import com.analisis.numericsapp.app.OneVarEquation.MultipleRoot;


public class EquationSystem extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation_system);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.equation_system, menu);

        setupButtonGaussianElimination();
        setupButtonPartialPivoting();
        setupButtonTotalPivoting();
        setupButtonDoolittle();
        setupButtonCrout();
        setupButtonCholesky();
        setupButtonGaussSeidel();
        setupButtonJacobi();

        return true;
    }

    private void setupButtonJacobi() {
        Button bt1 =(Button) findViewById(R.id.jacobi);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MultipleRoot.class));
            }
        });
    }

    private void setupButtonGaussSeidel() {
        Button bt1 =(Button) findViewById(R.id.gaussSeidel);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MultipleRoot.class));
            }
        });
    }

    private void setupButtonCholesky() {
        Button bt1 =(Button) findViewById(R.id.cholesky);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MultipleRoot.class));
            }
        });
    }

    private void setupButtonCrout() {
        Button bt1 =(Button) findViewById(R.id.crout);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MultipleRoot.class));
            }
        });
    }

    private void setupButtonDoolittle() {
        Button bt1 =(Button) findViewById(R.id.doolittle);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MultipleRoot.class));
            }
        });
    }

    private void setupButtonTotalPivoting() {
        Button bt1 =(Button) findViewById(R.id.totalPivoting);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MultipleRoot.class));
            }
        });
    }

    private void setupButtonPartialPivoting() {
        Button bt1 =(Button) findViewById(R.id.partialPivoting);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MultipleRoot.class));
            }
        });
    }

    private void setupButtonGaussianElimination() {
        Button bt1 =(Button) findViewById(R.id.gaussianElimintation);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),GaussianElimination.class));
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
