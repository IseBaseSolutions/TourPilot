package isebase.cognito.tourpilot.Data.Question.Category;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Category extends BaseObject {

	private boolean blocked;
	
	public enum type {braden, norton, sturzrisiko, schmerzermittlung, normal};
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	public Category() {
		clear();
	}
	
    public Category(String initString)
    {
    	StringParser parsingString = new StringParser(initString);
    	parsingString.next(";");
    	setID(Integer.parseInt(parsingString.next(";")));
        setName(parsingString.next("~"));
        setCheckSum(Long.parseLong(parsingString.next()));
    }

    public String forServer()
    {
    	NCryptor ncryptor = new NCryptor();
    	String strValue = new String("Y;");
        strValue += ncryptor.LToNcode(getID()) + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
    }
    
    public type getCategoryType(){
		if(getName().equals(StaticResources.getBaseContext().getString(R.string.braden)))
				return type.braden;
		else if(getName().equals(StaticResources.getBaseContext().getString(R.string.norton)))
				return type.norton;
		else if(getName().equals(StaticResources.getBaseContext().getString(R.string.sturzrisiko)))
				return type.sturzrisiko;
		else if(getName().equals(StaticResources.getBaseContext().getString(R.string.schmerzermittlung)))
			return type.schmerzermittlung;
		return type.normal;
    }

}
