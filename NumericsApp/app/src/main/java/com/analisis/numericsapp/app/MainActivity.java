package com.analisis.numericsapp.app;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.analisis.numericsapp.app.EquationSystemPackage.EquationSystemMatrix;
import com.analisis.numericsapp.app.OneVarEquation.IncrementalSearch;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        setupButtonOneVariableEq();
        setupButtonEquationSystem();
        setUpButtonInterpolation();
        return true;


    }

    private void setUpFunction()
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("One variable equation");
        alert.setMessage("Write a function");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
                WrapperMatrix.GlobalFunction = new Funcion(input.getText().toString());
                startActivity(new Intent(getApplicationContext(),OneVariableEquation.class));
            }
        });

        alert.show();

    }

    public void setUpPoints()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Interpolation");
        alert.setMessage("Number of points");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
                WrapperMatrix.pointNumbers = Integer.parseInt(input.getText().toString());
                startActivity(new Intent(getApplicationContext(),InterpolationMethod.class));
            }
        });

        alert.show();
    }

    private void setUpMatrixOrder()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Equation System");
        alert.setMessage("Write the matrix order");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
                WrapperMatrix.MatrixOrder = Integer.parseInt(input.getText().toString());
                startActivity(new Intent(getApplicationContext(),EquationSystemMatrix.class));
            }
        });

        alert.show();
    }

    private void setupButtonOneVariableEq() {
        Button bt1 =(Button) findViewById(R.id.oneVarEquation);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setUpFunction();
            }
        });
    }

    private void setupButtonEquationSystem() {

        Button bt1 =(Button) findViewById(R.id.equationSys);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpMatrixOrder();
            }
        });
    }

    private void setUpButtonInterpolation()
    {
        Button bt1 =(Button) findViewById(R.id.buttonInterpolation);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InterpolationMethod.class));
            }
        });
    }

    /*private void setUpButtonIntegration()
    {
        Button bt1 =(Button) findViewById(R.id.buttonIntegration);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InterpolationMethod.class));
            }
        });
    }*/
}
