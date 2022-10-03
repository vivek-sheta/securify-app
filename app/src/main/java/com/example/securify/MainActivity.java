package com.example.securify;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.SmsManager;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements LocationListener {
    ArrayList<String> contactName,contactNumber;
    String LOC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton send = findViewById(R.id.send);
        FloatingActionButton add = findViewById(R.id.add);
    }

    //For Button Click Perform Action

    public void performAction(View view) {
        if(view.getId() == R.id.send){
            sendMessage();
        }else{
            Intent intent = new Intent(this,add.class);
            startActivity(intent);

        }
    }
    private void setDataFromAnotherActivity(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String contact = intent.getStringExtra("contact");
        contactName.add(name);
        contactNumber.add(contact);
    }

    private void sendMessage(){
        setDataFromAnotherActivity();
        int LocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int SmsPermissionCheck =  ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(LocationPermissionCheck == PackageManager.PERMISSION_GRANTED){
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            if(SmsPermissionCheck == PackageManager.PERMISSION_GRANTED){
                for (String val: contactNumber) {
                    String phoneNumber = val.trim();
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, "SOS MESSAGE FROM YOUR FRIENDS\n"+LOC, null, null);
                }
            }else ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, 0);
        }else ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        LOC = "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}