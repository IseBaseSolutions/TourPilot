package isebase.cognito.tourpilot.Utils;

final public class StringParser {
	
    private String fldInitString;
    private int pos1 = 0;
    private int pos2 = 0;

    public StringParser(String InitString)
    {
        fldInitString=InitString;
    }

    public String next(String token)
    {
        pos2 = fldInitString.indexOf(token, pos1);
        if (pos2 == -1)
            return "";
        String subString = fldInitString.substring(pos1, pos2);
        pos1 = ++pos2;
        return subString;
    }

    public String next()
    {
        return fldInitString.substring(pos1);
    }

}
