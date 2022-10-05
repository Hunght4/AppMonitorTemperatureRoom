package com.example.monitoringtemperaturegashumidity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifTextView;

public class GasActivity extends AppCompatActivity {
    //private Button btnBackGas;
    private Button btnTurnOnFan1;
    private Button btnTurnOffFan1;
    private TextView tvFan1;

    PushOrReceiveFirebase pushOrReceiveFirebase = new PushOrReceiveFirebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);
        //btnBackGas = (Button) findViewById(R.id.btn_gas_back);
        btnTurnOnFan1 = (Button) findViewById(R.id.btn_turn_on_fan1);
        btnTurnOffFan1 = (Button) findViewById(R.id.btn_turn_off_fan1);
        tvFan1 = (TextView) findViewById(R.id.tv_fan1);

        Intent intent = getIntent();
        String roomNumber = intent.getStringExtra(RoomActivity.ROOM);

        //GasBack();

        GifTextView gtvGas = (GifTextView) findViewById(R.id.gtv_gas);
        pushOrReceiveFirebase.ReceiveFirebase(roomNumber,"gas", gtvGas);

        setBtnTurnOnFan1(roomNumber);
        setBtnTurnOffFan1(roomNumber);

        pushOrReceiveFirebase.ReceiveOnOffFirebase(roomNumber,"fan1", tvFan1, "Fan1");
    }
    /*
    public void GasBack() {
        btnBackGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GasActivity.this, RoomActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }*/

    public void setBtnTurnOnFan1(String roomNumber) {
        btnTurnOnFan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushOrReceiveFirebase.PushFirebase(roomNumber,"fan1", "ON");
            }
        });
    }

    public void setBtnTurnOffFan1(String roomNumber) {
        btnTurnOffFan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushOrReceiveFirebase.PushFirebase(roomNumber,"fan1", "OFF");
            }
        });
    }
}