package com.example.monitoringtemperaturegashumidity;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pl.droidsonroids.gif.GifTextView;

public class PushOrReceiveFirebase {
    DatabaseReference mData;

    public void ReceiveFirebase(String room, String key, GifTextView gtv) {
        mData = FirebaseDatabase.getInstance().getReference();

        mData.child(String.valueOf(room)).child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gtv.setText(String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ReceiveOnOffFirebase(String room, String key, TextView state, String motor) {
        mData = FirebaseDatabase.getInstance().getReference();

        mData.child(room).child(key).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                state.setText(motor + " is " + String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void PushFirebase(String room, String key, String value) {
        mData = FirebaseDatabase.getInstance().getReference();

        mData.child(room).child(key).setValue(value);

    }

    public void PushFirebase(String key, Object value) {
        mData = FirebaseDatabase.getInstance().getReference();

        mData.child(key).setValue(value);

    }
}
