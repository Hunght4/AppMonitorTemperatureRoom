package com.example.monitoringtemperaturegashumidity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pl.droidsonroids.gif.GifTextView;

public class TemperatureActivity extends AppCompatActivity {
    //private Button btnBackTemperature;
    private Button btnTurnOnFan;
    private Button btnTurnOffFan;
    private TextView tvFan;

    PushOrReceiveFirebase pushOrReceiveFirebase = new PushOrReceiveFirebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        //btnBackTemperature = (Button) findViewById(R.id.btn_temperature_back);
        btnTurnOnFan = (Button) findViewById(R.id.btn_turn_on_fan);
        btnTurnOffFan = (Button) findViewById(R.id.btn_turn_off_fan);
        tvFan = (TextView) findViewById(R.id.tv_fan);

        Intent intent = getIntent();
        String roomNumber = intent.getStringExtra(RoomActivity.ROOM);

        //TemperatureBack();

        GifTextView gtvTemperature = (GifTextView) findViewById(R.id.gtv_temperature);
        pushOrReceiveFirebase.ReceiveFirebase(roomNumber,"temperature", gtvTemperature);

        setBtnTurnOnFan(roomNumber);
        setBtnTurnOffFan(roomNumber);

        pushOrReceiveFirebase.ReceiveOnOffFirebase(roomNumber,"fan", tvFan, "Fan");
    }
    /*
    public void TemperatureBack() {
        btnBackTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TemperatureActivity.this, RoomActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }*/

    public void setBtnTurnOnFan(String roomNumber) {
        btnTurnOnFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushOrReceiveFirebase.PushFirebase(roomNumber, "fan", "ON");
            }
        });
    }

    public void setBtnTurnOffFan(String roomNumber) {
        btnTurnOffFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushOrReceiveFirebase.PushFirebase(roomNumber,"fan", "OFF");
            }
        });
    }
}