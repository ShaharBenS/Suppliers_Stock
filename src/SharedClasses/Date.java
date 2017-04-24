package SharedClasses;

import java.sql.SQLData;

/**
 * Created by Shahar on 30/03/17.
 */
public class Date
{
    public short year;
    public byte month;
    public byte day;

    public Date(int _year,int _month,int _day)
    {
        this.year = (short) _year;
        this.month = (byte) _month;
        this.day = (byte) _day;
    }
    public Date(short _year,byte _month,byte _day)
    {
        this.year = _year;
        this.month = _month;
        this.day = _day;
    }
    public Date(Date date)
    {
        if(date == null)
        {
            return;
        }
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }
    public Date(String date)
    {
        if(date == null)
        {
            return;
        }
        try {
            String[] parts = date.split("\\.");
            this.day = Byte.parseByte(parts[0]);
            this.month = Byte.parseByte(parts[1]);
            this.year = Short.parseShort(parts[2]);
        }catch(Exception e){this.day = 0; this.month = 0; this.year =0;}
    }

    public Date(java.util.Date date) {
        //TODO !#$!#$ implement
    }

    @Override
    public String toString()
    {
        String date = "";
        if(day < 10)
        {
            date = date + "0";
        }
        date = date + day + ".";
        if(month < 10)
        {
            date = date + "0";
        }
        date = date + month + "."+year;
        return date;
    }


    public boolean equals(Date date)
    {
        if(date == null){return false;}
        return year == date.year && month == date.month && this.day == date.day;
    }

    public java.sql.Date toSQLdate()
    {
        //TODO test this
        return new java.sql.Date(year,month,day);
    }
}
