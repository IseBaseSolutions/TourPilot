package isebase.cognito.tourpilot.Data.AdditionalTask;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class AdditionalTask extends BaseObject {

    private int btyp;
    private int quality;
    
	public AdditionalTask(String initString) {
		initString = initString.substring(0, 2);
		StringParser parsingString = new StringParser(initString);		
		setCatalogType(Integer.parseInt(parsingString.next(";")));
        setId(Integer.parseInt(parsingString.next(";")));
        setName(parsingString.next(";"));
        setQuality(Integer.parseInt(parsingString.next("~")));
        setCheckSum(Long.parseLong(parsingString.next()));
	}
    
    public String IdentID() { 
    	return btyp + ";" + getId(); 
	}
    
    public int getCatalogType() {
    	return btyp;
    }
    
    public void setCatalogType(int fld_btyp) {
    	this.btyp = fld_btyp;  
    }
    
    public int getQuality() {
    	return quality;
    }
    
    public void setQuality(int quality) {
    	this.quality = quality;
    }
	
    public String forServer()
    {
    	NCryptor nCryptor = new NCryptor();
        String strValue = new String("Z;");
        strValue += IdentID() + ";";
        strValue += nCryptor.LToNcode(getCheckSum()); //use ncriptor
        return strValue;
    }
	
}
