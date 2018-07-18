package com.smartaccount.smartaccount;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddPage extends AppCompatActivity{
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private int amount;
    private String category;
    private String currency;
    private String note;
    private String addType;

    DatabaseReference myRef;

    EditText editAmount;
    EditText editCategory;
    EditText editNote;
    ToggleButton editType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //initViews
        editAmount = (EditText) findViewById (R.id.addRecordAmount);
        editCategory = (EditText) findViewById (R.id.addRecordCategory);
        editNote = (EditText) findViewById (R.id.addRecordNote);
        editType = (ToggleButton) findViewById(R.id.addRecordIncomeOutcomeButton);

        // Write a message to the database
        myRef =  FirebaseDatabase.getInstance().getReference("user");

        dateView = (TextView) findViewById(R.id.currentDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month, day);
    }

    public void jump_MainPage(View view){
        startActivity(new Intent(AddPage.this,MainActivity.class));
    }

    //submit and return to main page after pushing add button.
    public void addSubmit(View view){
        amount = Integer.parseInt(editAmount.getText().toString());
        category = editCategory.getText().toString();
        currency = "USD";
        note = editNote.getText().toString();
        if(editType.isChecked() == false) {
            addType = "Income";
        } else {
            addType = "Outcome";
        }

        History newHist = new History(amount, year, month, day, category, currency, note, addType);
        myRef.push().setValue(newHist);

        startActivity(new Intent(AddPage.this,MainActivity.class));
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
