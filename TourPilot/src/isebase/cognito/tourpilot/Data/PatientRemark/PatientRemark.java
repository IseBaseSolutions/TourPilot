package isebase.cognito.tourpilot.Data.PatientRemark;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class PatientRemark extends BaseObject {

	public PatientRemark(){
		clear();
	}
	
	public PatientRemark(String initString) {
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setId(Integer.parseInt(parsingString.next(";")));
		setName(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next()));
	}

	@Override
	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.PATIENT_REMARK + ";");
		strValue += ncryptor.LToNcode(getId()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}

}
