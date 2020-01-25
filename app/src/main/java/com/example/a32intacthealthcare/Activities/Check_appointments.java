package com.example.a32intacthealthcare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.a32intacthealthcare.Adapter.MyAdapter;
import com.example.a32intacthealthcare.Model.Userdetails;
import com.example.a32intacthealthcare.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Check_appointments extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;
    TextView textView;
    int i=0;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Userdetails> list;
    Userdetails userdetails;
    MyAdapter obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_appointments);
        toolbar = findViewById(R.id.mytool);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Check_appointments.this, MainActivity.class);
                startActivity(intent);
            }
        });
        textView=findViewById(R.id.tv_tool);
        listView=findViewById(R.id.lv);
        userdetails=new Userdetails();
        list=new ArrayList<>();
        textView.setText("Appointments");
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("PatientDetails");

        obj=new MyAdapter(this, R.layout.userdata,list);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    userdetails=ds.getValue(Userdetails.class);
                    list.add(userdetails);
                }
                listView.setAdapter(obj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Check_appointments.this, ""+databaseError, Toast.LENGTH_SHORT).show();

            }
        });


    }
}
