package isebase.cognito.tourpilot.Data.AdditionalTask;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class AdditionalTask extends BaseObject {

	public static final String CatalogTypeField = "catalog_type";
	public static final String QualityField = "quality"; 
	
    private int catalogType;
    private int quality;

	public String IdentID() { 
    	return catalogType + ";" + getId(); 
	}
    
    @MapField(DatabaseField = CatalogTypeField)
    public int getCatalogType() {
    	return catalogType;
    }
    
    @MapField(DatabaseField = CatalogTypeField)
    public void setCatalogType(int catalogType) {
    	this.catalogType = catalogType;  
    }
    
    @MapField(DatabaseField = QualityField)
    public int getQuality() {
    	return quality;
    }
    
    @MapField(DatabaseField = QualityField)
    public void setQuality(int quality) {
    	this.quality = quality;
    }
    
    public AdditionalTask(){
    	clear();
    }
    
	public AdditionalTask(String initString) {
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setCatalogType(Integer.parseInt(parsingString.next(";")));
        setId(Integer.parseInt(parsingString.next(";")));
        setName(parsingString.next(";"));
        setQuality(Integer.parseInt(parsingString.next("~")));
        setCheckSum(Long.parseLong(parsingString.next()));
	}
	
	@Override
    public String forServer()
    {
    	NCryptor nCryptor = new NCryptor();
        String strValue = new String(ServerCommandParser.ADDITIONAL_TASK_Z + ";");
        strValue += IdentID() + ";";
        strValue += nCryptor.LToNcode(getCheckSum());
        return strValue;
    }
    
    @Override
    protected void clear() {
    	super.clear();
    	setCatalogType(EMPTY_ID);
    	setQuality(EMPTY_ID);
    }
	
}
