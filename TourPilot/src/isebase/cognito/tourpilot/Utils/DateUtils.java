package isebase.cognito.tourpilot.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
    public static int DayMillisec = 86400000;
    public static final Date EmptyDate = new Date(1975,1,1,0,0,0);
    
    public static final SimpleDateFormat HourMinutesFormat = new SimpleDateFormat(" HH:mm");
    
	private static long timeDiff = 0L;
	
	public static Date GetServerDateTime() {
		return getLocalDate(getLocalTime(new Date()) + timeDiff);
	}
	
    public static Date getLocalDate(long milliseconds)
    {
    	return new Date(milliseconds -(Calendar.getInstance().get(Calendar.ZONE_OFFSET)
									 + Calendar.getInstance().get(Calendar.DST_OFFSET)));
    }
    
    public static long getLocalTime(Date value)
    {
        return value.getTime() + (Calendar.getInstance().get(Calendar.ZONE_OFFSET)
        						+ Calendar.getInstance().get(Calendar.DST_OFFSET));
    }
    	
}
