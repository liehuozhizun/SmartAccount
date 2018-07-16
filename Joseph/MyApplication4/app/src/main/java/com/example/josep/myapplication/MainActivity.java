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

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    // White the new history to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectDate = (RelativeLayout) findViewById(R.id.addRecordDate);
        selectDate.setOnClickListener(this);
        currentDate = (TextView) findViewById(R.id.currentDate);
        initDatePicker();

        EditText amount = (EditText)findViewById(R.id.addRecordAmount);
        int year = 2017;
        int month = 5;
        int day = 2;
        EditText category = (EditText)findViewById(R.id.addRecordCategory);
        String currency = "USD";
        EditText note = (EditText)findViewById(R.id.addRecordNote);
        String type = "outcome";

        writeNewHistory(
                Integer.valueOf(amount.getText().toString()).intValue(),
                year,
                month,
                day,
                category.getText().toString(),
                currency,
                note.getText().toString(),
                type
        );

    }

    // Object that construct the record term
    public class History {
        public int amount;
        public int year;
        public int month;
        public int day;
        public String category;
        public String currency;
        public String note;
        public String type;

        // Default constructor
        public History(){
        }

        public History(int amount, int year, int month, int day,
                       String category, String currency, String note, String type){
            this.amount = amount;
            this.year = year;
            this.month = month;
            this.day = day;
            this.category = category;
            this.currency = currency;
            this.note = note;
            this.type = type;
        }
    }

    private void writeNewHistory(int amount, int year, int month, int day,
                                 String category, String currency, String note, String type) {
        History history = new History(amount, year, month, day, category, currency, note, type);

        // Read the counter number from the database
        final int historyNumber=0;
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post counter = dataSnapshot.getValue(Post.class);
                int historyNumber = counter.counter +1 ;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // White the new history into the database
        String historyName = "history".concat(Integer.toString(historyNumber));
        myRef.child("user").child("history2").setValue(history);
        myRef.child("user").child("counter").setValue(historyNumber+1);
    }

    // Object for obtaining the historyNumber
    public static class Post{
        public int counter;
        public Post(int counter){
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