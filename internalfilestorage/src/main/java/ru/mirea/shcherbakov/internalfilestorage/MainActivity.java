package ru.mirea.shcherbakov.internalfilestorage;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private String fileName = "mirea.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView3);

        String string = "Hello mirea and practice6!";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(getTextFromFile());
                    }
                });
            }
        }).start();
    }

    public String getTextFromFile(){
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput(fileName);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        return null;
    }
}