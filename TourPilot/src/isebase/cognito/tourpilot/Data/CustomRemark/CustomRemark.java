package isebase.cognito.tourpilot.Data.CustomRemark;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class CustomRemark extends BaseObject {
	
	public static final String PosNumberField = "pos_number";
	
	int posNumber;
	boolean textInput;

	@MapField(DatabaseField = PosNumberField)
	public int getPosNumber() {
		return posNumber;
	}

	@MapField(DatabaseField = PosNumberField)
	public void setPosNumber(int posNumber) {
		this.posNumber = posNumber;
	}
	
	public CustomRemark(){
		clear();
	}
	
	public CustomRemark(int id, String name, int posNumber){
		clear();
		setID(id);
		setName(name);
		setPosNumber(posNumber);
	}
	
	public CustomRemark(String strInitString){
		StringParser InitString = new StringParser(strInitString);
		InitString.next(";");
		setID(Integer.parseInt(InitString.next(";")));
		setName(InitString.next(";"));
		setPosNumber(Integer.parseInt(InitString.next("~")));
		setCheckSum(Integer.parseInt(InitString.next()));
	}
    
    public String toString(){
    	String strValue = "";
        return strValue;
    }    

    
	@Override
	public String forServer() {       
    	NCryptor ncryptor = new NCryptor();
    	String strValue = new String("#;");
        strValue += ncryptor.LToNcode(getID()) + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
	}

	@Override
	public void clear(){
		super.clear();
		setPosNumber(EMPTY_ID);
	}
	
}
