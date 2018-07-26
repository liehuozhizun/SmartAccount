package com.demo.account.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.account.R;
import com.demo.account.db.dao.CountComeType;
import com.demo.account.db.dao.CountEntity;
import com.demo.account.db.dao.helper.CountDaoHelper;
import com.demo.account.event.AddCountEvent;
import com.demo.account.event.UserLoginChangeEvent;
import com.demo.account.ui.adapter.HomeCountAdapter;
import com.demo.account.util.DateFormat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 主界面
 */
public class HomeFragment extends SupportFragment {

    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_total_outcome)
    TextView tvTotalOutcome;
    @BindView(R.id.tv_total_income)
    TextView tvTotalIncome;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    HomeCountAdapter homeCountAdapter = new HomeCountAdapter();

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        initHomeCountList();

        setDate();

        setCountData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddCountEvent(AddCountEvent event){
        setCountData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserLoginChangeEvent(UserLoginChangeEvent event){
        setCountData();
    }


    private void initHomeCountList() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setHasFixedSize(true);

        this.recyclerView.setAdapter(this.homeCountAdapter);
    }

    private void setCountData() {
        List<CountEntity> list = new CountDaoHelper().getAllMonthCount(new Date());

        setTotalCome(list);

        if (list != null){
            this.homeCountAdapter.setNewData(list);
        }else {
            this.homeCountAdapter.setNewData(new ArrayList<CountEntity>());
        }
    }

    private void setTotalCome(List<CountEntity> list) {
        int totalOutcome = 0;
        int totalIncome = 0;

        if (list != null){
            for (CountEntity entity : list) {
                if (entity.getComeType() == CountComeType.OUTCOME){
                    totalOutcome += entity.getMoney().intValue();
                }else {
                    totalIncome += entity.getMoney().intValue();
                }
            }
        }

        setTotalIncome(totalIncome);
        setTotalOutcome(totalOutcome);
    }

    private void setDate(){
        Date date = new Date();
        String year = DateFormat.format(date, "yyyy");
        String month = DateFormat.format(date, "MM");

        this.tvYear.setText(year);
        this.tvMonth.setText(month);
    }

    private void setTotalOutcome(int outcome){
        this.tvTotalOutcome.setText(String.valueOf(outcome));
    }

    private void setTotalIncome(int income){
        this.tvTotalIncome.setText(String.valueOf(income));
    }

}
