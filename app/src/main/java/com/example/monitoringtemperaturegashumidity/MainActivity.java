package com.example.monitoringtemperaturegashumidity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button btnAdd;
    private ListView lvRoom;
    ArrayList <String> arrList= new ArrayList<>();
    ArrayAdapter <String> arrAdp;
    DatabaseReference databaseReference;


    PushOrReceiveFirebase pushOrReceiveFirebase = new PushOrReceiveFirebase();

    public static final String ROOM = "ROOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark)));

        setWidget();
        ShowListRoomFromFirebase();
        setBtnAdd();
    }

    public void setWidget() {
        btnAdd = (Button) findViewById(R.id.btn_add);
        lvRoom = (ListView) findViewById(R.id.lv_room);
        arrAdp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrList);
        lvRoom.setAdapter(arrAdp);
    }

    private void ShowListRoomFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(BaseRoom.class).toString();
                arrList.add(value);
                arrAdp.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lvRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String room = String.valueOf(i+1);
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                intent.putExtra(ROOM, room);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, room, Toast.LENGTH_SHORT).show();
            }
        });

        lvRoom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_layout_room);
                Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
                Button btnNo = (Button) dialog.findViewById(R.id.btn_no);

                String room = String.valueOf(i+1);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (arrList.size() == i+1) {
                            databaseReference.child(room).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    databaseReference.child(room).removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Toast.makeText(MainActivity.this, "Room is deleted", Toast.LENGTH_SHORT).show();
                            Intent intphto = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intphto);
                        } else {
                            Toast.makeText(MainActivity.this, "You can only delete the last room", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    private void setBtnAdd() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_layout_add);
                EditText edtRoomName1 = (EditText) dialog.findViewById(R.id.edt_room_name);
                Button btnAddRoom1 = (Button) dialog.findViewById(R.id.btn_add_room);

                btnAddRoom1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = edtRoomName1.getText().toString().trim();

                        BaseRoom baseRoom = new BaseRoom("OFF", "OFF", "Null", "Null", "OFF", "Null", name);

                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(MainActivity.this, "Please input name", Toast.LENGTH_SHORT).show();
                        } else {
                            pushOrReceiveFirebase.PushFirebase(String.valueOf(arrList.size()+1), baseRoom);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}