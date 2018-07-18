package com.example.josep.myapplication;

import com.example.josep.myapplication.R;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.josep.myapplication.widget.CustomDatePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.util.Log;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        selectDate = findViewById(R.id.addRecordDate);
        selectDate.setOnClickListener(this);
        currentDate = findViewById(R.id.currentDate);
        initDatePicker();
*/
        //EditText amount = findViewById(R.id.addRecordAmount);
        int amount = 11;
        int year = 2017;
        int month = 5;
        int day = 2;
        int hour = 1;
        int minute = 1;
        //EditText category = findViewById(R.id.addRecordCategory);
        String category = "test";
        String currency = "USD";
        //EditText note = findViewById(R.id.addRecordNote);
        String note = "test";
        String type = "outcome";

        writeNewHistory(
                //Integer.valueOf(amount.getText().toString()).intValue(),
                amount,
                year,
                month,
                day,
                hour,
                minute,
                //category.getText().toString(),
                category,
                currency,
                //note.getText().toString(),
                note,
                type
        );

    }

    // Object that construct the record term
    public class History {
        public int amount;
        public int year;
        public int month;
        public int day;
        public int hour;
        public int minute;
        public String category;
        public String currency;
        public String note;
        public String type;

        public History(int amount, int year, int month, int day, int hour, int minute,
                       String category, String currency, String note, String type){
            this.amount = amount;
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
            this.category = category;
            this.currency = currency;
            this.note = note;
            this.type = type;
        }
    }

    public int historyNumber = 2;
    public void writeNewHistory(int amount, int year, int month, int day, int hour, int minute,
                                 String category, String currency, String note, String type) {
        History history = new History(amount, year, month, day, hour, minute, category, currency, note, type);

        // White the new history to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");
        //DatabaseReference myRef_counter = database.getReference("user").child("counter");

/*
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int value = dataSnapshot.getValue(int.class);
                historyNumber.setValue();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
*/
/*
        // Read the counter number from the database
        myRef_counter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                historyNumber = post.getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        historyNumber++;
*/        // White the new history into the database
        //String historyName = "history".concat(Integer.toString(historyNumber));
        myRef.child("history2").setValue(history);
        //myRef_counter.setValue(historyNumber);
    }
    //private static final String TAG = "PostDetailActivity";

    // Object for obtaining the historyNumber
    public static class Post{
        public int counter;
        public Post(int counter){
        }
        public int getValue(){
            return this.counter;
        }
    }


    private RelativeLayout selectDate;
    private TextView currentDate;
    private CustomDatePicker customDatePicker1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addRecordDate:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
                break;
        }
    }

    private void initDatePicker(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, "2010-01-01", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

    }

}