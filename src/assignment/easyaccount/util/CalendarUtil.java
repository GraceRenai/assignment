package assignment.easyaccount.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtil {
	public static Date getDate(String dateString)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	public static String getDataString(int year,int month,int day)
	{
		return String.format("%04d-%02d-%02d", year,month,day);
	}
}
