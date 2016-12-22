package info.malignantshadow.api.util;

public class Time {
	
	public static final int HOURS_IN_DAY = 24;
	
	public static final int MINUTES_IN_HOUR = 60;
	public static final int MINUTES_IN_DAY = MINUTES_IN_HOUR * HOURS_IN_DAY;
	
	public static final int SECONDS_IN_MINUTE = 60;
	public static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * MINUTES_IN_HOUR;
	public static final int SECONDS_IN_DAY = SECONDS_IN_MINUTE * MINUTES_IN_DAY;
	
	public int days = 0;
	public int hours = 0;
	public int minutes = 0;
	public int seconds = 0;
	
	public int toSeconds() {
		return seconds + (minutes * SECONDS_IN_MINUTE) + (hours * SECONDS_IN_HOUR) + (days * SECONDS_IN_DAY);
	}
	
}
