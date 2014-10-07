package com.analisis.numericsapp.app.Integration;

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

public class SimpleTrapeze extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_trapeze);
    }

    private static double x[];
    private static double evaluated[];
    public EditText[][] editTextArray;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_trapeze, menu);

        setUpButton();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Simple Trapeze");
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

    private void setUpButton() {
        Button bt1 =(Button) findViewById(R.id.buttonTrapezed);

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

                simpleTrapeze();

            }
        });
    }


    private void simpleTrapeze()
    {
        double integral = ((x[x.length-1] - x[0])/2) * (evaluated[0] + evaluated[evaluated.length-1]);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Simple Trapeze");
        alert.setMessage("The result of the integral is:");

        // Set an EditText view to get user input
        final TextView input = new TextView(this);
        input.setText(""+integral);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
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
