package com.analisis.numericsapp.app.Interpolation;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.analisis.numericsapp.app.AnswerTable;
import com.analisis.numericsapp.app.R;
import com.analisis.numericsapp.app.WrapperMatrix;

import static com.analisis.numericsapp.app.WrapperMatrix.interValue;
import static com.analisis.numericsapp.app.WrapperMatrix.pointNumbers;

public class InterpolationNewton extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolation_newton);
    }

    private static int contadorFilas=0;
    private static double B[];
    private static double x[];
    private static double evaluated[];
    public EditText[][] editTextArray;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.interpolation_newton, menu);

        x = new double[pointNumbers];
        evaluated = new double[pointNumbers];
        setUpButton();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Lagrange");
        alert.setMessage("How many points are?");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                WrapperMatrix.pointNumbers = Integer.parseInt(input.getText().toString());
                setUpPointsMatrix();
            }
        });

        alert.show();

        return true;
    }


    private void setUpPointsMatrix()
    {
        editTextArray = new EditText[pointNumbers][2];
        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        TableRow tbrow1 = new TableRow(this);
        TextView tv1=new TextView(this);
        tv1.setId(0);
        tv1.setText("X       Y");
        tbrow1.addView(tv1);
        ll.addView(tbrow1);

        for (int i = 0; i < pointNumbers; i++)
        {
            TableRow tbrow2=new TableRow(this);

            for (int j = 0; j < 2; j++)
            {
                EditText tv2=new EditText(this);
                tv2.setWidth(100);
                tv2.setId(j);
                tbrow2.addView(tv2);
                editTextArray[i][j] = tv2;
            }
            ll.addView(tbrow2);
        }

        hsv.addView(ll);
        sv.addView(hsv);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout1);
        linearLayout.addView(sv, 0);
    }

    private void setUpButton()
    {
        Button bt1 =(Button) findViewById(R.id.buttonLagrange);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText xvalueText = (EditText)findViewById(R.id.editText);
                WrapperMatrix.interValue = Double.parseDouble(xvalueText.getText().toString());

                for (int i = 0; i < pointNumbers; i++)
                {
                    x[i] = Double.parseDouble(editTextArray[i][0].getText().toString());
                }

                for (int i = 0; i < pointNumbers; i++)
                {
                    evaluated[i] = Double.parseDouble(editTextArray[i][1].getText().toString());
                }

                newton(x, evaluated, interValue);

            }
        });
    }

    public void newton(double x[],double evaluatedValues[],double interpolateValue)
    {
        double[] b = new double[x.length];
        B= new double[x.length];

        if (x.length > 0)
        {
            b[0] = evaluatedValues[0];
        }

        for ( int i = 1 ; i < x.length ; i++)
        {
            b[i] = dividedDiference(0, i, x, evaluatedValues);
            B[i]=b[i];
            System.out.println(B[i]);
            contadorFilas++;
        }

        double sum = 0.0;

        for (int k = 0; k < b.length; k++)
        {
            double producto = 0.0;
            producto += b[k];
            int counter = k - 1;

            while (counter >= 0)
            {
                producto *= (interpolateValue - x[counter]);
                counter--;
            }

            sum += producto;
        }

        //resultado de la suma
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Newton");
        alert.setMessage("Result:");

        // Set an EditText view to get user input
        final TextView input = new TextView(this);
        input.setText(""+sum);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }


    private double dividedDiference(int xInitial, int xFinal, double x[], double evaluatedValues[])
    {
        if (xFinal - xInitial == 1)
        {
            return (evaluatedValues[xInitial] - evaluatedValues[xFinal])/(x[xInitial] - x[xFinal]);
        }
        else
        {
            return (dividedDiference(xInitial, xFinal-1, x, evaluatedValues) - dividedDiference(xInitial + 1, xFinal, x, evaluatedValues))/
                    (x[xInitial] - x[xFinal]);
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
