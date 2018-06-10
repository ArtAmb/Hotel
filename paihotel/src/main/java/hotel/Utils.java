package hotel;

public class Utils {
	public static final String domainUrl = "/paihotel/";

	public static String getUrl(String template) {
		return domainUrl + template;
	}

	public static String getViewUrl(String template) {
		return getUrl("view/" + template);
	}

}