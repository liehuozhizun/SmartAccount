package com.smartaccount.smartaccount;

public class History {
        public int amount;
        public int year;
        public int month;
        public int day;
        public String category;
        public String currency;
        public String note;
        public String type;

        public History(int amount, int year, int month, int day, String category, String currency, String note, String type){
            this.amount = amount;
            this.year = year;
            this.month = month;
            this.day = day;
            this.category = category;
            this.currency = currency;
            this.note = note;
            this.type = type;
        }
}
