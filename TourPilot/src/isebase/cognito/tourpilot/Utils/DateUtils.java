package isebase.cognito.tourpilot.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
    public static int DayMillisec = 86400000;

    public static final Date EmptyDate = new Date(0);
    public static final SimpleDateFormat DateTimeformat = new SimpleDateFormat("dd.MM.yyyy/HH:mm:ss");
    public static final SimpleDateFormat HourMinutesFormat = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat WeekDateFormat = new SimpleDateFormat("EEE MM.dd");
    
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

    public static String toString(Date data)
    {
    	return DateTimeformat.format(data);
    }
    
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static Date getAverageDate(Date startDate, Date endDate)
    {
    	return new Date((startDate.getTime() + endDate.getTime())/2);
    }
    	
}
