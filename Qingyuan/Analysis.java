package com.smartaccount.smartaccount;


public class Analysis {
    public static void main(String[] args) {

    }

    //sum of the expend of a particular day
    static double sotd()

    {
        //calculate the sum
        double sum = 1;//sum of all the expend of the day
        return sum;
    }

    static double aotw()

    {
        double sum = 7;//sum of the sum of previous 7 days
        return sum / 7.0;

    }

    static double aotm()

    {
        double sum = 30;//sum of the sum of previous 30 days
        return sum / 30.0;

    }

    static int periodofcate(History[] h) {
        int counter = 0;
        int j = 0;
        boolean flag = true;
        for (int i = 0; i < h.length; i++) {
            if (h[i].cate == cate) {
                if (flag == false) {
                    return j - 1;
                } else {
                    j = i;
                    flag = false;
                }

            }

        }
    }

    static boolean reminder(History[] h) {
        for (int i = h.length; i > 0; i--) {
            if (h[i].cate == cate) {
                if (h.length - i == periodofcate(h)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    static int aop(History[] h){
        int counter = 0;
        int sum=0;
        int j = 0;
        boolean flag = true;
        for (int i = 0; i < h.length; i++) {
            if (h[i].cate == cate) {
                if (flag == false) {
                    sum=sum+j-i;
                    counter++;
                    flag=true;

                } else {
                    j = i;
                    flag = false;
                }

            }

        }
        return sum/counter;

    }
}


