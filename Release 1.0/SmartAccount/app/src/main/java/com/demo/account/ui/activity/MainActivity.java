package com.demo.account.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.account.R;
import com.demo.account.db.dao.CountComeType;
import com.demo.account.event.CountComeTypeEvent;
import com.demo.account.storage.BaseParamNames;
import com.demo.account.storage.SharedPreferencesHelper;
import com.demo.account.ui.adapter.ContentPagerAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setActionBar();

        setupViewPager();

        setupTabLayout();

        setFloatingActionButtonClickListener();
    }


    @OnClick({R.id.tv_title_left, R.id.tv_title_right})
    public void onClick(View view) {
        int type = CountComeType.INCOME;
        switch (view.getId()) {
            case R.id.tv_title_left:   // expense
                this.tvTitleLeft.setBackgroundResource(R.drawable.bg_left_tb_select);
                this.tvTitleRight.setBackgroundResource(0);
                type = CountComeType.OUTCOME;
                break;
            case R.id.tv_title_right:  // income
                this.tvTitleLeft.setBackgroundResource(0);
                this.tvTitleRight.setBackgroundResource(R.drawable.bg_right_tb_select);
                break;
        }
        EventBus.getDefault().post(new CountComeTypeEvent(type));
    }

    private void setActionBar() {
        setSupportActionBar(this.toolbar);

        this.actionBar = getSupportActionBar();
    }


    /**
     * initialize ViewPager
     */
    private void setupViewPager() {
        ContentPagerAdapter adapter = new ContentPagerAdapter(getSupportFragmentManager());
        adapter.setContentFragments();
        this.viewPager.setAdapter(adapter);
        this.viewPager.setOffscreenPageLimit(2);
    }


    /**
     * initialize bottom button
     */
    private void setupTabLayout() {
        this.tabLayout.setupWithViewPager(this.viewPager);

        this.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                boolean visible = position == 0;
                setFloatingActionButtonVisibility(visible);

                boolean enabled = position == 1;
                setDisplayShowTitleEnabled(enabled);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setDisplayShowTitleEnabled(boolean enabled) {
        if (this.actionBar != null) {
            this.actionBar.setDisplayShowTitleEnabled(enabled);
        }

        this.actionBar.setDisplayShowTitleEnabled(!enabled);
        if (enabled) {
            this.llTitle.setVisibility(View.VISIBLE);
        }else {
            this.llTitle.setVisibility(View.GONE);
        }

    }

    /**
     * make FloatingActionButton visible
     *
     * @param visible
     */
    private void setFloatingActionButtonVisibility(boolean visible) {
        if (visible) {
            this.fab.setVisibility(View.VISIBLE);
        } else {
            this.fab.setVisibility(View.GONE);
        }
    }

    /**
     * FloatingActionButton click
     */
    private void setFloatingActionButtonClickListener() {
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCountRecord();
            }
        });
    }

    private void addCountRecord() {
        String username = SharedPreferencesHelper.getInstance()
                .getString(BaseParamNames.USER_NAME, "");
        if (TextUtils.isEmpty(username)) {
            this.viewPager.setCurrentItem(2);
            return;
        }

        startActivity(AddCountActivility.getCallingIntent(this));
    }

}
