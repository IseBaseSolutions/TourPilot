package isebase.cognito.tourpilot.Data.Information;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;
import isebase.cognito.tourpilot.Utils.StringParser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Information extends BaseObject {

	public static final String EmploymentCodeField = "employment_code";
	public static final String FromDateField = "from_date";
	public static final String TilldateField = "till_date";
	public static final String ReadTimeField = "read_time";
	public static final String IsFromServerField = "is_from_server";

	private long employmentCode;
	private Date fromDate;
	private Date tillDate;
	private Date readTime;
	private boolean isFromServer;

	@MapField(DatabaseField = EmploymentCodeField)
	public long getEmploymentCode() {
		return employmentCode;
	}

	@MapField(DatabaseField = EmploymentCodeField)
	public void setEmploymentCode(long employmentCode) {
		this.employmentCode = employmentCode;
	}

	@MapField(DatabaseField = FromDateField)
	public Date getFromDate() {
		return fromDate;
	}

	@MapField(DatabaseField = FromDateField)
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@MapField(DatabaseField = TilldateField)
	public Date getTillDate() {
		return tillDate;
	}

	@MapField(DatabaseField = TilldateField)
	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	@MapField(DatabaseField = ReadTimeField)
	public Date getReadTime() {
		return readTime;
	}

	@MapField(DatabaseField = ReadTimeField)
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	@MapField(DatabaseField = IsFromServerField)
	public boolean getIsFromServer() {
		return isFromServer;
	}

	@MapField(DatabaseField = IsFromServerField)
	public void setIsFromServer(boolean isFromServer) {
		this.isFromServer = isFromServer;
	}

	public Information(){
		clear();
	}
	
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

	@Override
	public String forServer() {
		String strValue = new String(ServerCommandParser.INFORMATION + ";");
		strValue += String.format("%d;%d", getId(), getEmploymentCode()) + ";";
		strValue += getCheckSum();
		return strValue;
	}

	@Override
	protected void clear() {
		super.clear();
		setEmploymentCode(EMPTY_ID);
		setFromDate(DateUtils.EmptyDate);
		setTillDate(DateUtils.EmptyDate);
		setReadTime(DateUtils.EmptyDate);
		setIsFromServer(false);
		
	}
	
}
