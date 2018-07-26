package com.demo.account.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.account.App;
import com.demo.account.R;
import com.demo.account.db.dao.CountEntity;
import com.demo.account.util.CountMoneyUtils;
import com.demo.account.util.CountTypeHelper;

public class CountCategoryAdapter extends BaseQuickAdapter<CountEntity, BaseViewHolder> {
    public CountCategoryAdapter() {
        super(R.layout.item_count_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountEntity item) {
        int type = item.getType().intValue();
        String typeText = CountTypeHelper.getCountTypeText(App.getContext(), type);
        helper.setText(R.id.tv_count_type, typeText);

        String note = item.getNote();
        helper.setText(R.id.tv_count_note, note);

        int comeType = item.getComeType().intValue();
        int money = item.getMoney().intValue();
        String moneyText = CountMoneyUtils.getCountMoneyText(comeType, money);
        helper.setText(R.id.tv_count_money, moneyText);;
    }
}
