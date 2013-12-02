package isebase.cognito.tourpilot.Data.BaseObject;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class BaseObjectCompare implements Comparator<BaseObject> {

	@Override
	public int compare(BaseObject lhs, BaseObject rhs) {
		
		Collator deCollator = Collator.getInstance(Locale.GERMANY);
		// TODO Auto-generated method stub
		return deCollator.compare(lhs.getName(), rhs.getName());
	}
	
}
