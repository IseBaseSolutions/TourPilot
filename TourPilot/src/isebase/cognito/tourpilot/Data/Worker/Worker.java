package isebase.cognito.tourpilot.Data.Worker;

import java.util.Date;
import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
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
	
	public Worker() {
		clear();
	}
		
    public Worker(String strInitString)
    {
        this(strInitString, new Date());
    }

    public Worker(String strInitString, Date _actualDate) {
        StringParser initString = new StringParser(strInitString);
        initString.next(";");
        setID(Integer.parseInt(initString.next(";")));
        setName(initString.next(";"));
        setIsUseGPS(Integer.parseInt(initString.next("~")) == 1 ? true : false);
        setCheckSum(Long.parseLong(initString.next()));
        setActualDate(_actualDate);
    }

    @Override
    public String toString() {
    	return getName();
    }
    
    @Override
    public String forServer()
    {
        NCryptor nCryptor = new NCryptor();
        String strValue = new String(ServerCommandParser.WORKER + ";");
        strValue += nCryptor.LToNcode(getID()) + ";";
        strValue += nCryptor.LToNcode(getCheckSum());
        return strValue;
    }

    @Override
    protected void clear() {
    	super.clear();
    	setIsUseGPS(false);
    	setActualDate(new Date());
    }
    
    public boolean checkPIN(String strPin){
    	if (strPin.isEmpty())
			return false;
		Long pin = Long.parseLong(strPin);
		long num = 0;
		int numArray[] = new int[] { 1, 3, 5, 7, 13, 0x11 };
		try {
			byte byteText[] = getName().getBytes("latin1");
			for (int i = 0; i < byteText.length; i++)
				num += (byteText[i]) * numArray[i % 6];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num == pin;
    }
    
}
