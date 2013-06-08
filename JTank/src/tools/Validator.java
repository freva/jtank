package tools;

import java.util.regex.Pattern;

public class Validator {
	public static boolean isValidIPv4(String IP){
		String ipv4Pattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		
		return Pattern.compile(ipv4Pattern).matcher(IP).matches();
	}
}
