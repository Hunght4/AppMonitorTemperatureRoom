package com.example.monitoringtemperaturegashumidity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pl.droidsonroids.gif.GifTextView;

import java.util.Objects;

public class RoomActivity extends AppCompatActivity {
    private Button btnBackHome;
    private GifTextView gtvTemperature;
    private GifTextView gtvGas;
    private GifTextView gtvHumidity;

    Toolbar mActionBarToolbar;
    TextView mTitle;

    PushOrReceiveFirebase pushOrReceiveFirebase = new PushOrReceiveFirebase();

    DatabaseReference databaseReference;

    public static final String ROOM = "ROOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        //Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark)));

        btnBackHome = (Button) findViewById(R.id.btn_back_home);
        gtvTemperature = (GifTextView) findViewById(R.id.gtv_temperature);
        gtvGas = (GifTextView) findViewById(R.id.gtv_gas);
        gtvHumidity = (GifTextView) findViewById(R.id.gtv_humidity);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);

        Intent intent = getIntent();
        String roomNumber = intent.getStringExtra(MainActivity.ROOM);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(roomNumber).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setSupportActionBar(mActionBarToolbar);
                //getSupportActionBar().setTitle(snapshot.getValue().toString());
                mTitle.setText(snapshot.getValue().toString());
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BackHome();
        Temperature(roomNumber);
        Gas(roomNumber);
        Humidity(roomNumber);

        pushOrReceiveFirebase.ReceiveFirebase(roomNumber,"temperature", gtvTemperature);
        pushOrReceiveFirebase.ReceiveFirebase(roomNumber,"gas", gtvGas);
        pushOrReceiveFirebase.ReceiveFirebase(roomNumber,"humidity", gtvHumidity);
    }

    public void BackHome() {
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Temperature(String roomNumber) {
        gtvTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomActivity.this, TemperatureActivity.class);
                intent.putExtra(ROOM, roomNumber);
                startActivity(intent);
                //Toast.makeText(RoomActivity.this, roomNumber, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Gas(String roomNumber) {
        gtvGas = (GifTextView) findViewById(R.id.gtv_gas);

        gtvGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomActivity.this, GasActivity.class);
                intent.putExtra(ROOM, roomNumber);
                startActivity(intent);
            }
        });
    }
    public void Humidity(String roomNumber) {
        gtvHumidity = (GifTextView) findViewById(R.id.gtv_humidity);

        gtvHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomActivity.this, HumidityActivity.class);
                intent.putExtra(ROOM, roomNumber);
                startActivity(intent);
            }
        });
    }
}