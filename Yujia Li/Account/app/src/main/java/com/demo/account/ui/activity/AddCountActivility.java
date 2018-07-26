package com.demo.account.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.demo.account.R;
import com.demo.account.db.dao.CountComeType;
import com.demo.account.db.dao.CountEntity;
import com.demo.account.db.dao.helper.CountDaoHelper;
import com.demo.account.event.AddCountEvent;
import com.demo.account.storage.BaseParamNames;
import com.demo.account.storage.SharedPreferencesHelper;
import com.demo.account.util.CountTypeHelper;
import com.demo.account.util.DateFormat;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCountActivility extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.iv_count_type)
    ImageView ivCountType;
    @BindView(R.id.sp_count_array)
    Spinner spCountArray;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private Calendar calendar = Calendar.getInstance();

    private int comeCountType = CountComeType.OUTCOME;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AddCountActivility.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activility_add_count);
        ButterKnife.bind(this);

        setActionBar();

        Date date = this.calendar.getTime();
        setDate(date);
        setTime(date);

        setCountTypeSelectedListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
                finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tv_title_left, R.id.tv_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_left:   // 支出
                this.comeCountType = CountComeType.OUTCOME;
                this.tvTitleLeft.setBackgroundResource(R.drawable.bg_left_tb_select);
                this.tvTitleRight.setBackgroundResource(0);
                break;
            case R.id.tv_title_right:  // 收入
                this.comeCountType = CountComeType.INCOME;
                this.tvTitleLeft.setBackgroundResource(0);
                this.tvTitleRight.setBackgroundResource(R.drawable.bg_right_tb_select);
                break;
        }
    }

    @OnClick(R.id.tv_date)
    public void onDateClicled(){
        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        AddCountActivility.this.calendar.set(Calendar.YEAR, year);
                        AddCountActivility.this.calendar.set(Calendar.MONTH, month);
                        AddCountActivility.this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        setDate(AddCountActivility.this.calendar.getTime());
                    }
                },
                this.calendar.get(Calendar.YEAR),
                this.calendar.get(Calendar.MONTH),
                this.calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnClick(R.id.tv_time)
    public void onTimeClicked(){
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                AddCountActivility.this.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                AddCountActivility.this.calendar.set(Calendar.MINUTE, minute);
                setTime(AddCountActivility.this.calendar.getTime());
            }
        },
        this.calendar.get(Calendar.HOUR_OF_DAY),
        this.calendar.get(Calendar.MINUTE),
        true);
        dialog.show();
    }

    @OnClick(R.id.btn_submit)
    public void submit(){
        String moneyStr = this.etMoney.getText().toString();
        if (TextUtils.isEmpty(moneyStr)) {
            return;
        }

        Integer money = Integer.valueOf(moneyStr);
        int countType = this.spCountArray.getSelectedItemPosition();
        Date date = this.calendar.getTime();
        String note = this.etNote.getText().toString();
        String username = SharedPreferencesHelper.getInstance()
                .getString(BaseParamNames.USER_NAME, "");

        CountEntity countEntity = new CountEntity();
        countEntity.setMoney(money);
        countEntity.setComeType(this.comeCountType);
        countEntity.setDate(date);
        countEntity.setNote(note);
        countEntity.setType(countType);
        countEntity.setUserName(username);

        new CountDaoHelper().insertCount(countEntity);

        EventBus.getDefault().post(new AddCountEvent());

        finish();
    }


    private void setActionBar() {
        setSupportActionBar(this.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void setCountTypeSelectedListener(){
        this.spCountArray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setComeCountType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setComeCountType(int type){
        this.ivCountType.setImageResource(CountTypeHelper.getCountTypeImage(type));
    }


    private void setDate(Date date){
        String text = DateFormat.format(date, "MM dd, yyyy");
        this.tvDate.setText(text);
    }


    private void setTime(Date date){
        String text = DateFormat.format(date, "HH:mm");
        this.tvTime.setText(text);
    }

}
