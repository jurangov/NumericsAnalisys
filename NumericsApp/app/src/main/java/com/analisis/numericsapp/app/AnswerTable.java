package com.analisis.numericsapp.app;

import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static android.widget.TableLayout.LayoutParams;
import static java.lang.Class.*;

public class AnswerTable extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_table);

        double [][] Amatrix = WrapperMatrix.matrix;

        ScrollView sv = new ScrollView(this);

        TableLayout ll=new TableLayout(this);
        ll.setBackgroundColor(Color.parseColor("#ff000000"));
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        TableRow tbrow1=new TableRow(this);
        tbrow1.setPadding(4,4,4,4);

        for (int n = 0; n<WrapperMatrix.tableNames.length; n++)
        {
            TextView tv1=new TextView(this);
            tv1.setId(n);
            tv1.setText(WrapperMatrix.tableNames[n]);
            tv1.setBackgroundColor(Color.parseColor("#FF828282"));
            tv1.setTextColor(Color.parseColor("#FFFFFFFF"));
            tv1.setPadding(3,3,3,3);

            tbrow1.addView(tv1);
        }
        ll.addView(tbrow1);

        for(int i=0; i<WrapperMatrix.Counter; i++)
        {
            TableRow tbrow2=new TableRow(this);
            tbrow2.setPadding(2,2,2,2);
            for(int j=0; j<Amatrix[i].length; j++)
            {
                TextView tv1=new TextView(this);
                tv1.setId(j);

                if (j == Amatrix[i].length - 1)
                {
                    tv1.setText(String.format("%.8f", Amatrix[i][j]));
                }
                else
                {
                    if (j == 0)
                    {
                        tv1.setText(String.format("%", Amatrix[i][j]));
                    }
                    else
                    {
                        tv1.setText(String.format("%.5f", Amatrix[i][j]));
                    }
                }

                tv1.setBackgroundColor(Color.parseColor("#FFC8C8C8"));
                tv1.setPadding(3,3,3,3);

                tbrow2.addView(tv1);
            }
            ll.addView(tbrow2);
        }
        hsv.addView(ll);
        sv.addView(hsv);
        setContentView(sv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.answer_table, menu);
        return true;
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
