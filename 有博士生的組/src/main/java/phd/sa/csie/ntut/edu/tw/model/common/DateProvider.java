package phd.sa.csie.ntut.edu.tw.model.common;

import java.util.Date;

public class DateProvider {
    private static Date date = null;

    public static Date now(){
        if(date == null)
            return new Date();
        else
            return date;
    }

    public static void setDate(Date now)
    {
        date = now;
    }

    public static void resetDate()
    {
        date = null;
    }
}
