package isebase.cognito.tourpilot.Data.Worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Worker extends BaseObject {

	public static final String IsUseGPSField = "is_use_gps";
	public static final String ActualDateField = "actualDate";
	
	private boolean isUseGPS;

	@MapField(DatabaseField = IsUseGPSField)		
	public boolean getIsUseGPS() {
		return isUseGPS;
	}

	@MapField(DatabaseField = IsUseGPSField)
	public void setIsUseGPS(boolean isUseGPS) {
		this.isUseGPS = isUseGPS;
	}
	
	public Date actualDate;
	
	@MapField(DatabaseField = ActualDateField)
	public Date getActualDate() {
		return actualDate;
	}

	@MapField(DatabaseField = ActualDateField)
	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}

	public String getNameWithDate(){
		return "[" + actualDate.toString().substring(4,10) + "] " + getName();
	}
	
	public List<Tour> tours = new ArrayList<Tour>();
	
	public Worker() {
		clear();
	}
		
    public Worker(String strInitString)
    {
        this(strInitString, new Date());
    }

    public Worker(String strInitString, Date _actualDate) {
        StringParser initString = new StringParser(strInitString);
        //TODO [Palm server changes required] Next line should be removed 
        initString.next(";");
        setId(Integer.parseInt(initString.next(";")));
        setName(initString.next(";"));
        setIsUseGPS(Integer.parseInt(initString.next("~")) == 1 ? true : false);
        setCheckSum(Long.parseLong(initString.next()));
        setActualDate(_actualDate);
    }
    
    @Override
    public String toString()
    {
        return getName();
    }

    public String forServer()
    {
        NCryptor nCryptor = new NCryptor();
        String strValue = new String(ServerCommandParser.WORKER + ";");
        strValue += nCryptor.LToNcode(getId()) + ";";
        strValue += nCryptor.LToNcode(getCheckSum());
        return strValue;
    }

}
