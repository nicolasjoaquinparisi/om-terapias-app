package com.example.myapplication;

import android.util.Log;

import java.text.SimpleDateFormat;

public class DateTime
{
    private int year;
    private int month;
    private int day;

    private int hour;
    private int minutes;

    //Class variables used to know which is the selected date used in the activities
    public static String SelectedDate;

    //GETTERS AND SETTERS
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /*----- CONSTRUCTOR ------*/
    public DateTime(int[] fechaYHora)
    {
        setDay(fechaYHora[0]);
        setMonth(fechaYHora[1]);
        setYear(fechaYHora[2]);
        setHour(fechaYHora[3]);
        setMinutes(fechaYHora[4]);
    }

    /*----- CLASS METHODS ------*/

    // This method generates an instance of DateTime with a string date and a time
    public static DateTime parseDateTime(String aDate, String aTime)
    {
        int[] date = getDateFromString(aDate);
        int[] time = getTimeFromString(aTime);

        int[] dateAndTime = new int[] {date[0], date[1], date[2], time[0], time[1]};

        return new DateTime(dateAndTime);
    }

    // This method generates an instance of DateTime with a string date
    public static DateTime parseDateTime(String aDate)
    {
        int[] date = getDateFromString(aDate);

        int[] dateAndTime = new int[] {date[0], date[1], date[2], 0, 0};

        return new DateTime(dateAndTime);
    }

    //This method returns an integer array with the day, month and year from a string
    private static int[] getDateFromString(String aDate)
    {
        String[] date = aDate.split("/", 0);

        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);

        return new int[]{day, month, year};
    }

    //This method returns an integer array with the hour and minutes from a string
    private static int[] getTimeFromString(String aTime)
    {
        String[] time = aTime.split(":", 0);

        int hour = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);

        return new int[]{hour, minutes};
    }

    //This method returns a formated string with the hour and minutes from two integer variables
    public static String formatTimeFrom(int hour, int minutes)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(hour);

        sb.append(':');

        sb.append(minutes);

        if (minutes == 0)
            sb.append('0');

        return sb.toString();
    }

    //This methods returns true if dateTime is equals to anotherDateTime otherwise returns false
    private static boolean dateIsEqualsTo(DateTime dateTime, DateTime anotherDateTime)
    {
        if (dateTime.year == anotherDateTime.getYear() && dateTime.month == anotherDateTime.getMonth() &&
                dateTime.day == anotherDateTime.getDay())
            return true;
        return false;
    }

    //This methods returns true if aDateTime is the selected date used by the activities otherwise returns false
    public static boolean isTheSelectedDate(DateTime aDateTime)
    {
        DateTime selectedDate = DateTime.parseDateTime(SelectedDate);
        return dateIsEqualsTo(aDateTime, selectedDate);
    }

    /* ----- INSTANCE METHODS ----- */

    //This method compares this instance with anotherDateTime and returns true if this date is bigger than anotherDateTime
    public boolean dateIsBiggerThan(DateTime anotherDateTime)
    {
        if (this.year > anotherDateTime.getYear())
        {
            return true;
        }
        if (this.month == anotherDateTime.getMonth())
        {
            if (this.day > anotherDateTime.getDay())
            {
                return true;
            }
            if (this.month == anotherDateTime.getMonth())
            {
                if (this.day > anotherDateTime.getDay())
                {
                    return true;
                }
            }
        }
        return false;
    }

    //This method compares this instance with anotherDateTime and returns true if this time is bigger than anotherDateTime
    public boolean timeIsBiggerThan(DateTime anotherDateTime)
    {
        if (this.hour > anotherDateTime.getHour())
        {
            return true;
        }
        else
        {
            if (this.hour == anotherDateTime.getHour())
            {
                if (this.minutes > anotherDateTime.getMinutes())
                {
                    return true;
                }
            }
        }
        return false;
    }

    //This method check if this date and time is less than today and return true if is the case otherwise returns false
    public boolean isAOldDate()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateNow = format.format(System.currentTimeMillis());
        format = new SimpleDateFormat("HH:mm");
        String timeNow = format.format(System.currentTimeMillis());

        DateTime dateAndTimeNow = DateTime.parseDateTime(dateNow, timeNow);

        if (this.year < dateAndTimeNow.getYear())
        {
            return true;
        }

        if (this.year == dateAndTimeNow.getYear() && this.month < dateAndTimeNow.getMonth())
        {
            return true;
        }

        if (this.year == dateAndTimeNow.getYear() && this.month == dateAndTimeNow.getMonth() && this.getDay() < dateAndTimeNow.getDay())
        {
            return true;
        }

        if (this.year == dateAndTimeNow.getYear() && this.month == dateAndTimeNow.getMonth() && this.day == dateAndTimeNow.getDay())
        {
            if (this.hour < dateAndTimeNow.getHour())
            {
                return true;
            }
            if (this.hour == dateAndTimeNow.getHour() && this.minutes < dateAndTimeNow.getMinutes())
            {
                return true;
            }
        }

        return false;
    }

    //This method compare this date and time with anotherDateTime and returns true if this and anotherDateTime are equals otherwise returns false
    public boolean isEqualsTo(DateTime anotherDateTime)
    {
        if (dateIsEqualsTo(this, anotherDateTime) && isTimeEqualsTo(anotherDateTime))
            return true;
        return false;
    }

    //This method is used to get the date and time today as a instance of DateTime
    private DateTime getDateTimeNow()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(format.format(System.currentTimeMillis()));
        format = new SimpleDateFormat("mm");
        int month = Integer.parseInt(format.format(System.currentTimeMillis()));
        format = new SimpleDateFormat("dd");
        int day = Integer.parseInt(format.format(System.currentTimeMillis()));

        format = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(format.format(System.currentTimeMillis()));
        format = new SimpleDateFormat("mm");
        int minutes = Integer.parseInt(format.format(System.currentTimeMillis()));

        int[] dateAndTime = new int[] {year, month, day, hour, minutes };
        return new DateTime(dateAndTime);
    }

    //This method compares the time of this instance with the time of anotherDateTime and returns true if they are equals otherwise returns false
    public boolean isTimeEqualsTo(DateTime anotherDateTime)
    {
        if (this.getHour() == anotherDateTime.getHour() && this.getMinutes() == anotherDateTime.getMinutes())
            return true;
        return false;
    }

    //This method return a formated string with the date (DD/MM/YYYY)
    public String getDateToString()
    {
        return this.day + "/" + this.month + "/" + this.year;
    }

    //This method return a string with the date and time. Used to generates IDs (DDMMYYYYHHMM)
    public String getFullDateToString()
    {
        return String.valueOf(this.day) + String.valueOf(this.month) + String.valueOf(this.year) + String.valueOf(this.hour) + String.valueOf(this.minutes);
    }
}
