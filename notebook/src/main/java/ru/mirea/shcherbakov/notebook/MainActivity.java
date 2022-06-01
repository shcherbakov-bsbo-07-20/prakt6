package ru.mirea.shcherbakov.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private EditText fileNameEdit;
    private EditText fileTextEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileNameEdit = findViewById(R.id.fileNameEdit);
        fileTextEdit = findViewById(R.id.fileTextEdit);
        preferences = getPreferences(MODE_PRIVATE);

        if (!preferences.getString("FilePath", "empty").equals("empty")){
            fileNameEdit.setText(preferences.getString("FilePath", "empty"));
            fileTextEdit.setText(getTextFromFile());
        }
    }

    public void saveClick(View view) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileNameEdit.getText().toString(), Context.MODE_PRIVATE);
            outputStream.write(fileTextEdit.getText().toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("FilePath", fileNameEdit.getText().toString());
        editor.apply();
    }

    public String getTextFromFile(){
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput(fileNameEdit.getText().toString());
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            String text = new String(bytes);
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