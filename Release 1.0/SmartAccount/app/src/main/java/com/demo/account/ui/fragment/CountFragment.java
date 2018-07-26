package com.demo.account.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.account.R;
import com.demo.account.db.dao.CountComeType;
import com.demo.account.db.dao.CountEntity;
import com.demo.account.db.dao.helper.CountDaoHelper;
import com.demo.account.event.AddCountEvent;
import com.demo.account.event.CountComeTypeEvent;
import com.demo.account.event.UserLoginChangeEvent;
import com.demo.account.ui.adapter.CountCategoryAdapter;
import com.demo.account.ui.widget.ChartFormatter;
import com.demo.account.util.ArithUtils;
import com.demo.account.util.ChartUtils;
import com.demo.account.util.CountMoneyUtils;
import com.demo.account.util.CountTypeHelper;
import com.demo.account.util.DateFormat;
import com.demo.account.util.PaletteUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public class CountFragment extends SupportFragment {

    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.pieChartView)
    PieChart pieChartView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private Date currentDate = new Date();
    private int countComeType = CountComeType.OUTCOME;

    private List<CountEntity> totalCountList = new ArrayList<>();


    private HashMap<Integer, Double> moneyMap = new HashMap<>();

    private HashMap<Integer, Integer> colorMap = new HashMap<>();

    private HashMap<String, Float> percentMap = new HashMap<>();

    private CountDaoHelper countDaoHelper = new CountDaoHelper();

    private CountCategoryAdapter countCategoryAdapter = new CountCategoryAdapter();

    public static CountFragment getInstance() {
        return new CountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count, container, false);
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

        initChart();

        initAdapter();

        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountComeTypeEvent(CountComeTypeEvent event){
        this.countComeType = event.getType();
        clear();
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddCountEvent(AddCountEvent event){
        clear();
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserLoginChangeEvent(UserLoginChangeEvent event){
        clear();
        init();
    }


    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onClick(View view) {
        int offset = 0;
        switch (view.getId()) {
            case R.id.btn_left: // request data of last month
                offset = -1;
                break;
            case R.id.btn_right:// request data of next month
                offset = 1;
                break;
        }
        this.currentDate = DateFormat.getDateNxtMonth(currentDate, offset);
        clear();
        init();
    }

    private void init() {
        setDate();

        this.totalCountList = this.countDaoHelper
                .getAllMonthCountOfType(this.currentDate, this.countComeType);

        if (this.totalCountList != null) {
            for (CountEntity entity : this.totalCountList) {
                Integer type = entity.getType();
                Integer money = entity.getMoney();

                this.moneyMap.put(type, Double.valueOf(money));
            }
        }

        setChartData();
    }

    private void clear(){
        this.moneyMap.clear();
        this.totalCountList.clear();
        this.countCategoryAdapter.removeAllHeaderView();
        this.countCategoryAdapter.setNewData(this.totalCountList);
    }

    /**
     * initialize adapter
     */
    private void initAdapter() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        this.recyclerView.setAdapter(this.countCategoryAdapter);
    }

    private void setAdapterData(int type, int money){
        setHeaderView(type, money);

        List<CountEntity> list = new ArrayList<>();
        for (CountEntity entity : totalCountList) {
            if (entity.getType() == type){
                list.add(entity);
            }
        }

        this.countCategoryAdapter.setNewData(list);
    }

    private void setHeaderView(int type, int money) {
        View view = getLayoutInflater().inflate(R.layout.item_count_category, null);
        ImageView ivCountType = view.findViewById(R.id.iv_count_type);
        TextView tvCountType = view.findViewById(R.id.tv_count_type);
        TextView tvCountNote = view.findViewById(R.id.tv_count_note);
        TextView tvCountMoney = view.findViewById(R.id.tv_count_money);


        ivCountType.setImageResource(CountTypeHelper.getCountTypeImage(type));
        tvCountType.setText(CountTypeHelper.getCountTypeText(getContext(), type));

        DecimalFormat format = new DecimalFormat("##0.0");
        double percent = money / getTotalMoney();
        String percentText = format.format(percent * 100) + " %";
        tvCountNote.setText(percentText);

        String moneyText = CountMoneyUtils.getCountMoneyText(this.countComeType, money);

        tvCountMoney.setText(moneyText);

        this.countCategoryAdapter.setHeaderView(view, 0);
    }

    /**
     * 初始化图表
     */
    private void initChart() {
        ChartUtils.initPieChart(this.pieChartView);
        this.pieChartView.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // 单独显示每个分类的统计情况
                PieEntry entry = (PieEntry) e;
                float money = entry.getValue();
                Integer type = (Integer) entry.getData();

                setAdapterData(type, (int) money);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    /**
     * set chart data
     */
    public void setChartData() {
        if (this.moneyMap.size() > 0) {
            this.pieChartView.setVisibility(View.VISIBLE);

            String typeStr = this.countComeType == CountComeType.OUTCOME
                    ? getContext().getString(R.string.total_outcome_chart)
                    : getContext().getString(R.string.total_income_chart);
            this.pieChartView.setCenterText(generateCenterSpannableText(
                    String.valueOf(getTotalMoney()), typeStr));

            ArrayList<PieEntry> entries = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : this.moneyMap.entrySet()) {

                Float money = Float.valueOf(String.valueOf(entry.getValue()));
                int type = entry.getKey();
                PieEntry pieEntry = new PieEntry(money, type);
                entries.add(pieEntry);

                int image = CountTypeHelper.getCountTypeImage(type);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image);
                int colorRgb = PaletteUtils.getColorRgb(bitmap);
                this.colorMap.put(type,colorRgb);
                colors.add(colorRgb);
            }

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(colors);

            PieData data = new PieData(dataSet);
            ChartFormatter formatter = new ChartFormatter(this.percentMap, this.moneyMap.size());
            data.setValueFormatter(formatter);
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.WHITE);
            this.pieChartView.setData(data);
            this.pieChartView.animateX(800);
        }else{
            this.pieChartView.setVisibility(View.GONE);
        }
    }

    /**
     *  SpannableString
     *
     * @return SpannableString
     */
    private SpannableString generateCenterSpannableText(String money, String descText) {
        SpannableString s = new SpannableString(getContext().getString(R.string.rmb)
                .concat(money).concat("\n").concat(descText));
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length() - descText.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, s.length() - descText.length(), 0);
        s.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.textRed))
                , 0, s.length() - descText.length(), 0);
        s.setSpan(new RelativeSizeSpan(.9f), s.length() - descText.length(), s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - descText.length(), s.length(), 0);
        s.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.textGrayish))
                , s.length() - descText.length(), s.length(), 0);
        return s;
    }

    public double getTotalMoney() {
        Double totalMoney = 0.0;
        for (Map.Entry<Integer, Double> entry : this.moneyMap.entrySet()) {
            totalMoney = ArithUtils.add(totalMoney, entry.getValue());
        }
        return totalMoney;
    }


    private void setDate() {
        String text = DateFormat.format(this.currentDate, "yyyy/MM");
        this.tvMonth.setText(text);
    }



}
