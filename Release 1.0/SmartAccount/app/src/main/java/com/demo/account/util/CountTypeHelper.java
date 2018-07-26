package com.demo.account.util;

import android.content.Context;

import com.demo.account.R;

public class CountTypeHelper {

    private static int[] countRes = {R.drawable.ic_add_food, R.drawable.ic_add_clothes,
            R.drawable.ic_add_education, R.drawable.ic_add_fuel, R.drawable.ic_add_electornics,
            R.drawable.ic_add_entertament, R.drawable.ic_add_other};

    private CountTypeHelper() {
    }

    public static int getCountTypeImage(int type){
        return countRes[type];
    }

    public static String getCountTypeText(Context context, int type){
        String[] array = context.getResources().getStringArray(R.array.count_type_array);
        return array[type];
    }

}
