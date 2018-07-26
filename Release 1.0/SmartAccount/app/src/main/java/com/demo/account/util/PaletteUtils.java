package com.demo.account.util;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.demo.account.App;
import com.demo.account.R;


/**
 * @author Airsaid
 * @github https://github.com/airsaid
 * @date 2017/5/12
 * @desc
 */
public class PaletteUtils {


    /**
     *
     * @param bitmap
     * @return
     */
    public static int getColorRgb(Bitmap bitmap) {
        Palette.Builder b = new Palette.Builder(bitmap);
        b.maximumColorCount(1);
        Palette palette = b.generate();
        if(palette.getSwatches().size() > 0){
            Palette.Swatch swatch = palette.getSwatches().get(0);
            if(swatch != null){
                return swatch.getRgb();
            }
        }else{
            return palette.getLightVibrantColor(App.getContext().getResources()
                    .getColor(R.color.colorAccent));
        }
        return -1;
    }
}
