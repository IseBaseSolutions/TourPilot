package isebase.cognito.tourpilot.Data.Tour;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tour extends BaseObject {

	public static final String IsCommonTourField = "is_common_tour";

	private boolean isCommonTour;

	@MapField(DatabaseField = IsCommonTourField)
	public boolean getIsCommonTour() {
		return isCommonTour;
	}

	@MapField(DatabaseField = IsCommonTourField)
	public void setIsCommonTour(boolean isCommonTour) {
		this.isCommonTour = isCommonTour;
	}

	public Tour() {
		clear();
	}

	public Tour(String strInitString) {
		StringParser InitString = new StringParser(strInitString);
		InitString.next(";");
		setID(Integer.parseInt(InitString.next(";")));
		setName(InitString.next(";"));
		setIsCommonTour(Integer.parseInt(InitString.next("~")) == 1 ? true
				: false);
		setCheckSum(Long.parseLong(InitString.next()));
	}

	@Override
	public String toString() {
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EE MM.dd");
		String dayOfTheWeek = simpleDateformat.format(new Date());
		return String.format("%s %s", getName(), dayOfTheWeek);
	}

	@Override
	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.TOUR + ";");
		strValue += getID() + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}

	@Override
	protected void clear() {
		super.clear();
		setIsCommonTour(false);
	}
	
}
