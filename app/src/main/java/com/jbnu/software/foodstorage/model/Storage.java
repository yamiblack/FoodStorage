package com.jbnu.software.foodstorage.model;


import java.io.Serializable;
import java.util.Calendar;

public class Storage implements Serializable {
    String name;
    String expiration;
    String email;
    int amount;
    int dDay;
    int regTime;
    int notifyDate;
    boolean expanded = false;
    boolean notification;


    public Storage() {
        this.notification = true;
        this.notifyDate = 1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public int getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(int notifydate) {
        this.notifyDate = notifydate;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

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

    public void setExpiration(String expration) {
        int year, month, day;
        String[] split = expration.split("\\.");

        if (split[0].length() == 2) {
            split[0] = "20" + split[0];
        }

        if (split[1].length() == 1) {
            split[1] = "0" + split[1];
        }

        if (split[2].length() == 1) {
            split[2] = "0" + split[2];
        }

        year = Integer.parseInt(split[0]);
        month = Integer.parseInt(split[1]);
        day = Integer.parseInt(split[2]);

        //JANUARY = 0
        int dDay = registDDay(year, month - 1, day);
        String sDDay = Integer.toString(dDay);
        setDDay(dDay);

        String expiration = Integer.toString(year) + "." + Integer.toString(month) + "." + Integer.toString(day);

        this.expiration = expiration;
    }

    private static int registDDay(int year, int month, int day) {
        try {
            Calendar toDay = Calendar.getInstance();
            Calendar dday = Calendar.getInstance();

            dday.set(year, month, day);

            long ldday = dday.getTimeInMillis() / (24 * 60 * 60 * 1000);
            long ltoday = toDay.getTimeInMillis() / (24 * 60 * 60 * 1000);

            long substract = ldday - ltoday;
            return (int) substract;
        } catch (Exception e) {
            return -1;
        }
    }

}

