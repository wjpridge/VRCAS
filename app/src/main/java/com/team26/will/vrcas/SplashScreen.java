package com.team26.will.vrcas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SplashScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashScreen.this.startActivity(new Intent(String.valueOf(SplashScreen.this)));
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFile();
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, VideoPlane.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createFile() {
        EditText name = (EditText) findViewById(R.id.editText);
        EditText number = (EditText) findViewById(R.id.editText2);
        EditText bdate = (EditText) findViewById(R.id.editText3);
        EditText cdate = (EditText) findViewById(R.id.editText4);

        try {
            FileOutputStream fOut = openFileOutput("file.txt", MODE_WORLD_READABLE);
            fOut.write(name.toString().getBytes());
            fOut.write(number.toString().getBytes());
            fOut.write(bdate.toString().getBytes());
            fOut.write(cdate.toString().getBytes());
            fOut.close();
            System.out.println("Path : " + getFilesDir());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
