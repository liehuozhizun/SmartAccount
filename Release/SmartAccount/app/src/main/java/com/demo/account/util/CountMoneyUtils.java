package com.demo.account.util;

import com.demo.account.db.dao.CountComeType;

public class CountMoneyUtils {

    private CountMoneyUtils() {
    }

    public static String getCountMoneyText(int comeType, int money){
        StringBuilder builder = new StringBuilder();
        if (comeType == CountComeType.INCOME){
            builder.append("+");
        }else {
            builder.append("-");
        }

        builder.append(money);
        return builder.toString();
    }

}
