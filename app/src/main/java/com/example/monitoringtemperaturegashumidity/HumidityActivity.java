package com.example.monitoringtemperaturegashumidity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifTextView;

public class HumidityActivity extends AppCompatActivity {
    //private Button btnBackHumidity;
    private Button btnTurnOnMist;
    private Button btnTurnOffMist;
    private TextView tvMist;

    PushOrReceiveFirebase pushOrReceiveFirebase = new PushOrReceiveFirebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        //btnBackHumidity = (Button) findViewById(R.id.btn_humidity_back);
        btnTurnOnMist = (Button) findViewById(R.id.btn_turn_on_mist);
        btnTurnOffMist = (Button) findViewById(R.id.btn_turn_off_mist);
        tvMist = (TextView) findViewById(R.id.tv_mist);

        Intent intent = getIntent();
        String roomNumber = intent.getStringExtra(RoomActivity.ROOM);

        //HumidityBack();

        GifTextView gtvHumidity = (GifTextView) findViewById(R.id.gtv_humidity);
        pushOrReceiveFirebase.ReceiveFirebase(roomNumber,"humidity", gtvHumidity);

        setBtnTurnOnMist(roomNumber);
        setBtnTurnOffMist(roomNumber);

        pushOrReceiveFirebase.ReceiveOnOffFirebase(roomNumber,"mist", tvMist, "Mist");
    }
    /*
    public void HumidityBack() {
        btnBackHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HumidityActivity.this, RoomActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }*/

    public void setBtnTurnOnMist(String roomNumber) {
        btnTurnOnMist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushOrReceiveFirebase.PushFirebase(roomNumber,"mist", "ON");
            }
        });
    }

    public void setBtnTurnOffMist(String roomNumber) {
        btnTurnOffMist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushOrReceiveFirebase.PushFirebase(roomNumber,"mist", "OFF");
            }
        });
    }
}