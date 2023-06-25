package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FeedPetPage extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;
    OutputStream outputStream;

    ImageView img;
    TextView text;
    float value = 0;

    static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    ImageView back;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_pet_page);

        img = findViewById(R.id.petImg);
        text = findViewById(R.id.thanks);

        img.setTranslationX(800);
        text.setTranslationX(800);
        img.setAlpha(value);
        text.setAlpha(value);
        img.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        text.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothDevice = bluetoothAdapter.getRemoteDevice("98:D3:61:F6:52:B6");
        int counter = 0;
        do{
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        counter++;
        }while(!bluetoothSocket.isConnected() && counter<3);

        try {
            outputStream = bluetoothSocket.getOutputStream();
            outputStream.write(1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toHome = new Intent(FeedPetPage.this, ListOfPets.class);
                startActivity(toHome);
            }
        });

    }
}