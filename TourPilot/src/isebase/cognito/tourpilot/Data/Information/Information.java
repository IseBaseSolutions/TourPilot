package isebase.cognito.tourpilot.Data.Information;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Utils.StringParser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Information extends BaseObject {

	private long employmentCode;
	private Date fromDate;
	private Date tillDate;
	private Date readTime;
	private boolean isFromServer;

	public Information(String initString) {
		setIsFromServer(true);
		initString = initString.substring(0, 2);
		StringParser parsingString = new StringParser(initString);
		setId(Integer.parseInt(parsingString.next(";")));
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm");
		setEmploymentCode(Long.parseLong(parsingString.next(";")));
		try {
			setFromDate(format.parse(parsingString.next(";") + "0000"));
			setTillDate(format.parse(parsingString.next(";") + "2359"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setName(parsingString.next(";"));
		setCheckSum(Long.parseLong(parsingString.next()));
	}

	public long getEmploymentCode() {
		return employmentCode;
	}

	public void setEmploymentCode(long employmentCode) {
		this.employmentCode = employmentCode;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getTillDate() {
		return tillDate;
	}

	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	public boolean getIsFromServer() {
		return isFromServer;
	}
	
	public void setIsFromServer(boolean isFromServer) {
		this.isFromServer = isFromServer;
	}
	
    public String forServer() {
        String strValue = new String("I;");
        strValue += String.format("%d;%d", getId(), getEmploymentCode()) + ";";
        strValue += getCheckSum();
        return strValue;
    }

}
