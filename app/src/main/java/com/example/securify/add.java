package com.example.securify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add extends AppCompatActivity {

    private Button button;
    private EditText name,contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        button = findViewById(R.id.save);
        name = findViewById(R.id.contactData);
        contact = findViewById(R.id.nameData);
    }

    public void saveDat(View view) {
        if(name.getText().toString() != null && contact.getText().toString() != null){
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("name",name.getText().toString());
            intent.putExtra("contact",contact.getText().toString());
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"Fill The Details",Toast.LENGTH_SHORT).show();
        }
    }
}