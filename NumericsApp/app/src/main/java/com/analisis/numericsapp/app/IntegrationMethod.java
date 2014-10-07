package com.analisis.numericsapp.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.analisis.numericsapp.app.Integration.SimpleTrapeze;
import com.analisis.numericsapp.app.Integration.Simpson13;
import com.analisis.numericsapp.app.Integration.Simpson38;


public class IntegrationMethod extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integration_method);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.integration_method, menu);

        setUpSimpleTrapeze();
        setUpSimpson13();
        setUpSimpson38();

        return true;
    }

    private void setUpSimpson38()
    {
        Button bt1 =(Button) findViewById(R.id.buttonsimpson38);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Simpson38.class));
            }
        });
    }

    private void setUpSimpson13()
    {
        Button bt1 =(Button) findViewById(R.id.buttonSimpson13);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Simpson13.class));
            }
        });
    }

    private void setUpSimpleTrapeze()
    {
        Button bt1 =(Button) findViewById(R.id.buttonTrapeze);

        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SimpleTrapeze.class));
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
