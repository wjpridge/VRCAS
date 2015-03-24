package com.team26.will.vrcas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SplashScreen extends ActionBarActivity {

    static String filename;
    static File file;
    FileOutputStream fileout;
    static OutputStreamWriter fOut;

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
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, VR_BALANCE_TEST.class));
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
            File sdCard = Environment.getExternalStorageDirectory();
            sdCard.mkdirs();
            File directory = new File (sdCard.getAbsolutePath() + "/MyFiles");
            directory.mkdirs();
            file  = new File(directory, name.getText().toString()+ ".txt");
            filename = name.getText().toString()+ ".txt";
            System.out.println(directory);

            fileout = new FileOutputStream(file);
            fOut = new OutputStreamWriter(fileout);


            fOut.write("FILESTART;\n");
            fOut.write(name.getText().toString()+ ";\n");
            fOut.write(number.getText().toString()+ ";\n");
            fOut.write(bdate.getText().toString()+ ";\n");
            fOut.write(cdate.getText().toString()+ ";\n");
            System.out.println("Path : " + getFilesDir());
            fOut.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
