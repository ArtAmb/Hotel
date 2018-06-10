package hotel;

import java.sql.Date;
import java.util.Calendar;

public class Utils {
	public static final String domainUrl = "/paihotel/";

	public static String getUrl(String template) {
		return domainUrl + template;
	}

	public static String getViewUrl(String template) {
		return getUrl("view/" + template);
	}

	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}
}