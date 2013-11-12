package isebase.cognito.tourpilot.Data.Tour;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Tour extends BaseObject {

	public static final String IsCommonTourField = "IsCommonTour";

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
		setId(Integer.parseInt(InitString.next(";")));
		setName(InitString.next(";"));
		setIsCommonTour(Integer.parseInt(InitString.next("~")) == 1 
				? true
				: false);
		setCheckSum(Long.parseLong(InitString.next()));
	}

	@Override
	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.TOUR + ";");
		strValue += getId() + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}
	
	@Override
	protected void clear() {
		super.clear();
		setIsCommonTour(false);
	}
}
