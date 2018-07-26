package com.demo.account.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.account.App;
import com.demo.account.R;
import com.demo.account.db.dao.CountEntity;
import com.demo.account.util.CountMoneyUtils;
import com.demo.account.util.CountTypeHelper;
import com.demo.account.util.DateFormat;

import java.util.Date;

public class HomeCountAdapter extends BaseQuickAdapter<CountEntity, BaseViewHolder>{


    public HomeCountAdapter() {
        super(R.layout.item_home_count);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountEntity item) {
        int type = item.getType().intValue();
        helper.setImageResource(R.id.iv_count_type, CountTypeHelper.getCountTypeImage(type));

        helper.setText(R.id.tv_count_type, CountTypeHelper.getCountTypeText(App.getContext(), type));

        String note = item.getNote();
        helper.setText(R.id.tv_count_note, note);

        int comeType = item.getComeType().intValue();
        int money = item.getMoney().intValue();
        String moneyText = CountMoneyUtils.getCountMoneyText(comeType, money);
        helper.setText(R.id.tv_count_money, moneyText);

        Date date = item.getDate();
        String dateText = DateFormat.format(date, "dd/MM/yyyy");
        helper.setText(R.id.tv_count_date, dateText);


    }
}
