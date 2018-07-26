package com.demo.account.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Float number calculator
 */
public class ArithUtils {


    private static final int DEF_DIV_SCALE = 10;

    private ArithUtils(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * addition
     * @param v1 addend1
     * @param v2 addend2
     * @return sum
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * substraction
     * @param v1 minuend
     * @param v2 subtractor
     * @return difference
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * muliplication
     * @param v1 multiplier1
     * @param v2 multiplier2
     * @return product
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * division
     * @param v1 dividend
     * @param v2 divisor
     * @return quotient
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     *
     * accurate division
     * @param v1 dividend
     * @param v2 divisor
     * @param scale accuracy
     * @return quotient
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *  decrease accuracy
     * @param v the input number
     * @param scale new accuracy
     * @return new number
     */
    public static double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * change data type(Float)
     * @param v input number
     * @return float number
     */
    public static float convertsToFloat(double v){
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * change input number to int
     * @param v input number
     * @return int number
     */
    public static int convertsToInt(double v){
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    /**
     * change inout number to long
     * @param v input number
     * @return long number
     */
    public static long convertsToLong(double v){
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    /**
     * return the greater one of two input numbers
     * @param v1 first number
     * @param v2 second number
     * @return the greater one
     */
    public static double returnMax(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    /**
     * the smaller one of two input numbers
     * @param v1 the first number
     * @param v2 the second number
     * @return the smaller one
     */
    public static double returnMin(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    /**
     * compare two numbers
     * @param v1 the first number
     * @param v2 the second number
     * @return if they are the same return 0; if the first one is greater return 1; else -1
     */
    public static int compareTo(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    /**
     * make 3 decimal to 2 decimal
     * @param money 10.123
     * @return 10.12
     */
    public static String formatMoney(Double money){
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);
        format.setGroupingUsed(false);
        return format.format(money);
    }

}  