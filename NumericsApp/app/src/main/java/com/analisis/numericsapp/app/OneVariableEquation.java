package com.analisis.numericsapp.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.analisis.numericsapp.app.OneVarEquation.Bisection;
import com.analisis.numericsapp.app.OneVarEquation.FalseRule;
import com.analisis.numericsapp.app.OneVarEquation.FixedPoint;
import com.analisis.numericsapp.app.OneVarEquation.IncrementalSearch;
import com.analisis.numericsapp.app.OneVarEquation.MultipleRoot;
import com.analisis.numericsapp.app.OneVarEquation.Newton;
import com.analisis.numericsapp.app.OneVarEquation.Secant;

public class OneVariableEquation extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_variable_equation);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.one_variable_equation, menu);

        setupButtonIncrementalSearch();
        setupButtonBisection();
        setupButtonFalseRule();
        setupButtonFixedPoint();
        setupButtonNewton();
        setupButtonSecant();
        setupButtonMultipleRoot();

        return true;
    }

    private void setupButtonMultipleRoot()
    {
        Button bt1 =(Button) findViewById(R.id.MultipleRoot);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MultipleRoot.class));
            }
        });
    }

    private void setupButtonSecant() {
        Button bt1 =(Button) findViewById(R.id.Secant);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Secant.class));
            }
        });
    }

    private void setupButtonNewton() {
        Button bt1 =(Button) findViewById(R.id.Newton);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Newton.class));
            }
        });
    }

    private void setupButtonFixedPoint() {
        Button bt1 =(Button) findViewById(R.id.FixedPoint);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FixedPoint.class));
            }
        });
    }

    private void setupButtonBisection() {
        Button bt1 =(Button) findViewById(R.id.Bisection);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Bisection.class));
            }
        });
    }

    private void setupButtonFalseRule() {
        Button bt1 =(Button) findViewById(R.id.FalseRule);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FalseRule.class));
            }
        });
    }

    private void setupButtonIncrementalSearch() {

        Button bt1 =(Button) findViewById(R.id.IncrementalSearch);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),IncrementalSearch.class));
            }
        });
    }

}
