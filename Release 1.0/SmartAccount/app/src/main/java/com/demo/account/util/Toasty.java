package com.demo.account.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast package
 */

public class Toasty {

    private Toasty() {
    }

    /**
     * display Toast for a short time
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * display Toast for a long time
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
