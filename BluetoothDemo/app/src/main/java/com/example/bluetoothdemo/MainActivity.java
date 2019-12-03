package com.example.bluetoothdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
BluetoothAdapter BA;

    public void turnBluetoothOff(View v){
        BA.disable();
        if(BA.isEnabled()){
            Toast.makeText(getApplicationContext(),"Bluetooth could not be disabled",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Bluetooth is turned off",Toast.LENGTH_SHORT).show();
        }
    }
    public void findDivices(View v){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(intent);
    }
    public void pairedDevices(View v){
        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        ListView listView = findViewById(R.id.listView);
        ArrayList list = new ArrayList();
        for(BluetoothDevice bluetoothDevice:pairedDevices){
            list.add(bluetoothDevice.getName());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BA=BluetoothAdapter.getDefaultAdapter();
        if(BA.isEnabled()){
            Toast.makeText(this,"Bluetooth is ON",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent  = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
            if(BA.isEnabled()){
                Toast.makeText(this,"Bluetooth now is ON",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
