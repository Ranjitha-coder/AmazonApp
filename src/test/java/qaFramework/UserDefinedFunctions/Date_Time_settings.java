package qaFramework.UserDefinedFunctions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date_Time_settings  {
	
	public String timeNow(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
	public String getCurrentDate(String Dateformat) {
		DateFormat dateFormat = new SimpleDateFormat(Dateformat);
		Calendar cal = Calendar.getInstance();
		String date  = dateFormat.format(cal.getTime());
		return date;
	}
	

}
