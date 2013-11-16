package isebase.cognito.tourpilot.Data.Diagnose;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Diagnose extends BaseObject{

	public Diagnose(){
		clear();
	}
	
    public Diagnose(String initString) {
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
        setId(Integer.parseInt(parsingString.next(";")));
        setName(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next()));
    }
    
    public String forServer() {
        NCryptor ncryptor = new NCryptor();
        String strValue = new String(ServerCommandParser.DIAGNOSE + ";");
        strValue += ncryptor.LToNcode(getId()) + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
    }
    
}
