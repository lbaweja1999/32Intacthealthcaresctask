package com.example.a32intacthealthcare.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;


import com.example.a32intacthealthcare.Broadcastreceivers.MyNotificationPublisher;
import com.example.a32intacthealthcare.Model.Userdetails;
import com.example.a32intacthealthcare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Book_Appointment extends AppCompatActivity {
    Toolbar toolbar;
    TextView textView;
    EditText editText,editText1,editText2,editText3,editText4,editText5;
    Button button,button1,button2;
    final Calendar mycalendar = Calendar.getInstance();
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Userdetails userdetails;
    int selhour;

    int i=0;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__appointment);
        toolbar = findViewById(R.id.mytool);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Book_Appointment.this, MainActivity.class);
                startActivity(intent);
            }
        });
        textView=findViewById(R.id.tv_tool);

        firebaseAuth= FirebaseAuth.getInstance();
        textView.setText("Book Appointment");
        button=findViewById(R.id.bookapp);
        button1=findViewById(R.id.remind);
        button2=findViewById(R.id.clear);
        editText=findViewById(R.id.tv_name);
        editText1=findViewById(R.id.tv_phone);
        editText2=findViewById(R.id.tv_add);
        editText3=findViewById(R.id.tv_date);
        editText4=findViewById(R.id.tv_time);
        editText5=findViewById(R.id.tv_prob);
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("PatientDetails");
        userdetails=new Userdetails();

        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Book_Appointment.this,R.style.MyDatePickerDialogStyle, dateDialog, mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH), mycalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Book_Appointment.this,R.style.MyDatePickerDialogStyle, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String min=String.valueOf(selectedMinute);
                        if (min.length()<2){
                            min="0"+min;
                        }
                        else
                        {
                            min=min;
                        }
                        editText4.setText( selectedHour + ":" + min);
                        selhour=selectedHour;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (TextUtils.isEmpty(editText.getText().toString())){
                            editText.setError("Enter your Name");
                        }
                        if (TextUtils.isEmpty(editText1.getText().toString())){
                            editText1.setError("Enter your Contact No.");
                        }
                        if (TextUtils.isEmpty(editText2.getText().toString())){
                            editText2.setError("Enter your Address");
                        }
                        if (TextUtils.isEmpty(editText3.getText().toString())){
                            editText3.setError("Enter  Date");
                        }
                        if (TextUtils.isEmpty(editText4.getText().toString())){
                            editText4.setError("Enter Time");
                        }
                        if (TextUtils.isEmpty(editText5.getText().toString())){
                            editText5.setError("Enter your Problem");
                        }
                        else{

                        getValues();

                        databaseReference.child("user0"+i).setValue(userdetails);

                        Toast.makeText(Book_Appointment.this, "Appointment booked successfully", Toast.LENGTH_SHORT).show();

                    }}

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(Book_Appointment.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                i++;

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleNotification(getNotification(  editText5.getText().toString() ) , (selhour-7200000)) ;

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                editText5.setText("");
            }
        });
    }
    private void scheduleNotification (Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }
    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Appointment Reminder" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable.intactlogo ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }


    DatePickerDialog.OnDateSetListener dateDialog = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dateofMonth) {

            mycalendar.set(year, month, dateofMonth);
            String dateFormat= "dd/MM/YYYY";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            editText3.setText(simpleDateFormat.format(mycalendar.getTime()));

        }
    };
public void getValues(){
userdetails.setName(editText.getText().toString());
userdetails.setContact(editText1.getText().toString());
userdetails.setAddress(editText2.getText().toString());
userdetails.setDate(editText3.getText().toString());
userdetails.setTime(editText4.getText().toString());
userdetails.setProblem(editText5.getText().toString());
}

}
