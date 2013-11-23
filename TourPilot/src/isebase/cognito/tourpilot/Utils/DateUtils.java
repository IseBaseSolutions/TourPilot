package isebase.cognito.tourpilot.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
    public static int DayMillisec = 86400000;
    
    public static final Date EmptyDate = new Date(1975,1,1);    

    public static final SimpleDateFormat DateTimeformat = new SimpleDateFormat("dd.MM.yyyy/HH:mm:ss");
    public static final SimpleDateFormat HourMinutesFormat = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat WeekDateFormat = new SimpleDateFormat("EEE dd.MM");
    
    public static Date getDateOnly(Date date){
    	return parseDateOnly(date);
    }
    
    public static Date getTodayDateOnly(){
    	return parseDateOnly(new Date());
    }
    
    public static Date getTodayDateTime(){
    	return new Date();
    }
    
    private static Date parseDateOnly(Date date){
    	Date retVal = date;
    	try {
    		retVal = DateFormat.parse(DateFormat.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retVal;	
    }
    
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
    
    public static String toDateTime(Date data)
    {
    	return DateTimeformat.format(data);
    }
    	
    public static String formatDate(Date date, String format){
    	SimpleDateFormat df = new SimpleDateFormat(format);
    	return df.format(date);
    }
    
}
