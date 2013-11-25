package isebase.cognito.tourpilot.Utils;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import java.util.List;

public class Utilizer {

	public static int[] getIDs(List<? extends BaseObject> items){
		int[] retVal = new int[items.size()];
		for(int i = 0; i < items.size();i++)
			retVal[i] = items.get(i).getID();
		return retVal;
	}
	
	public static String getIDsString(List<? extends BaseObject> items){
		String retVal = "";
		for(int i = 0; i < items.size();i++)
		{
			retVal += String.format("%s%s" 
					, retVal == "" ? "" : ", "
					, items.get(i).getID());
		}
		return retVal;
	}
	
	public static String getIDsString(int[] items){
		String retVal = "";
		for(int i = 0; i < items.length;i++)
		{
			retVal += String.format("%s%s" 
					, retVal == "" ? "" : ", "
					, items[i]);
		}
		return retVal;
	}	
}
