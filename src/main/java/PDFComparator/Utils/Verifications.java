package PDFComparator.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Verifications
{
  public static boolean isDate(String paramString)
  {
    DateFormat localDateFormat = DateFormat.getDateInstance();
    try
    {
      localDateFormat.parse(paramString);
      return true;
    }
    catch (ParseException localParseException)
    {
    }
    return false;
  }

  public static boolean isHour(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    try
    {
      localSimpleDateFormat.parse(paramString);
      return true;
    }
    catch (ParseException localParseException)
    {
    }
    return false;
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     Utils.Verifications
 * JD-Core Version:    0.6.2
 */