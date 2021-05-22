package com.jbnu.software.foodstorage;


import java.util.Calendar;

public class Storage {
    String name;
    int amount;
    int dDay;
    String expiration;
    int regTime;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    private boolean expanded;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegTime() {
        return regTime;
    }

    public void setRegTime(int regTime) {
        this.regTime = regTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDDay() {
        return dDay;
    }

    public void setDDay(int dDay) {
        this.dDay = dDay;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(int year, int month, int day) {
        //JANUARY = 0
        int dDay = registDDay(year, month-1, day);
        String sDDay = Integer.toString(dDay);
        setDDay(dDay);

        String expiration = Integer.toString(year) + "." + Integer.toString(month) + "." + Integer.toString(day);

        this.expiration = expiration;
    }

    private static int registDDay(int year, int month, int day) {
        try {
            Calendar toDay = Calendar.getInstance();
            Calendar dday = Calendar.getInstance();

            dday.set(year, month,day);

            long ldday  = dday.getTimeInMillis() / (24*60*60*1000);
            long ltoday = toDay.getTimeInMillis() / (24*60*60*1000);

            long substract =ldday - ltoday;
            return (int)substract;
        } catch (Exception e) {
            return -1;
        }
    }

}
