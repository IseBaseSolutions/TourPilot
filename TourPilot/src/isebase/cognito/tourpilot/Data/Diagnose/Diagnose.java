package isebase.cognito.tourpilot.Data.Diagnose;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Diagnose extends BaseObject{

    public Diagnose(String initString) {
		initString = initString.substring(0, 2);
		StringParser parsingString = new StringParser(initString);
        setId(Integer.parseInt(parsingString.next(";")));
        setName(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next()));
    }
    
    public String forServer() {
        NCryptor ncryptor = new NCryptor();
        String strValue = new String("D;");
        strValue += ncryptor.LToNcode(getId()) + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
    }
    
}
