package main.java.uk.co.utilisoft.utilities;

/**
 * Created by bilbowm (Max Bilbow) on 08/12/2015.
 */
public class NumberValidator {

    /**
     *
     * @param text
     * @param orDefault
     * @return a Long or a default value (including null) if not possible
     */
    public static Long tryParseLong(String text, Long orDefault)
    {
        try {
            return Long.parseLong(text); //If it's a columnNumber, we don't require anymore formatting.
        } catch (NumberFormatException e){}
        return orDefault;
    }

    /**
     *
     * @param text
     * @param orDefault
     * @return an Integer or a default value (including null) if not possible
     */
    public static Integer tryParseInt(String text, Integer orDefault)
    {
        if (text.length() <= 10)// || text.indexOf('.') <= 10 )//if length > 10 then this may be a Long
        try {
            return Integer.parseInt(text); //If it's a columnNumber, we don't require anymore formatting.
        } catch (NumberFormatException e){}
        return orDefault;
    }

    /**
     *
     * @param text
     * @param orDefault
     * @return an Integer or a default value (including null) if not possible
     */
    public static Short tryParseShort(String text, Short orDefault)
    {
        if (text.length() <= 3 || text.indexOf('.') <= 3 )//if length > 10 then this may be a Long
            try {
                return Short.parseShort(text); //If it's a columnNumber, we don't require anymore formatting.
            } catch (NumberFormatException e){}
        return orDefault;
    }

    public static Integer tryParseInt(Object o, Integer orDefault)
    {
        if (o==null)
            return orDefault;
        if (o instanceof Integer)
            return (Integer) o;
        return tryParseInt(String.valueOf(o),orDefault);
    }

    /**
     *
     * @param text
     * @param orDefault
     * @return a Double or a default value (including null) if not possible
     */
    public static Double tryParseDouble(String text, Double orDefault)
    {
        try {
            return Double.parseDouble(text); //If it's a columnNumber, we don't require anymore formatting.
        } catch (NumberFormatException e){}
        return orDefault;
    }
    /**
     *
     * @param text
     * @return
     */
    public static boolean isNumeric(String text)
    {
        return tryParseDouble(text,null) != null;
    }

    /**
     *
     * @param text
     * @return true if can be parsed as a Long but Not an Integer
     */
    public static boolean isLongNotInt(String text)
    {
        return text.length() > 10 && tryParseLong(text,null) != null;
    }

    /**
     *
     * @param text
     * @return true if can be parsed as a Long
     */
    public static boolean isInt(String text)
    {
        if (text.length() <= 10)//if length > 10 then this may be a Long
            try {
                Integer.parseInt(text); //If it's a columnNumber, we don't require anymore formatting.
                return true;
            } catch (NumberFormatException e){}
        return false;
    }

    /**
     *
     * @param text
     * @return true if can be parsed as a Long
     */
    public static boolean isShort(String text)
    {
        if (text.length() <= 3)//if length > 10 then this may be a Long
            try {
                Short.parseShort(text); //If it's a columnNumber, we don't require anymore formatting.
                return true;
            } catch (NumberFormatException e){}
        return false;
    }

}
