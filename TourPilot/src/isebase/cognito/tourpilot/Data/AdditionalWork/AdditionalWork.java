package isebase.cognito.tourpilot.Data.AdditionalWork;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class AdditionalWork extends BaseObject{

	public AdditionalWork(){
		clear();
	}
	
	public AdditionalWork(String initString) {
		StringParser parsingString = new StringParser(initString);		
		parsingString.next(";");
        setId(Integer.parseInt(parsingString.next(";")));
        setName(parsingString.next("~"));
        setCheckSum(Long.parseLong(parsingString.next()));
	}
	
	@Override
    public String forServer(){
    	NCryptor ncryptor = new NCryptor();
        String strValue = new String(ServerCommandParser.ADDITIONAL_WORK + ";");
        strValue += getId() + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
    }
	
}
