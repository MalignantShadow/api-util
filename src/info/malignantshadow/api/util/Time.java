package info.malignantshadow.api.util;

/**
 * Utility class for time.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class Time {
	
	/**
	 * The amount of hours in a day.
	 */
	public static final int HOURS_IN_DAY = 24;
	
	/**
	 * The amount of minutes in an hour.
	 */
	public static final int MINUTES_IN_HOUR = 60;
	
	/**
	 * The amount of minutes in a day.
	 */
	public static final int MINUTES_IN_DAY = MINUTES_IN_HOUR * HOURS_IN_DAY;
	
	/**
	 * The amount of second in a minute.
	 */
	public static final int SECONDS_IN_MINUTE = 60;
	
	/**
	 * The amount of seconds in an hour.
	 */
	public static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * MINUTES_IN_HOUR;
	
	/**
	 * The amount of seconds in a day.
	 */
	public static final int SECONDS_IN_DAY = SECONDS_IN_MINUTE * MINUTES_IN_DAY;
	
	/**
	 * The amount of days.
	 */
	public int days = 0;
	
	/**
	 * THe amount of hours.
	 */
	public int hours = 0;
	
	/**
	 * The amount of minutes.
	 */
	public int minutes = 0;
	
	/**
	 * The amount of seconds.
	 */
	public int seconds = 0;
	
	/**
	 * Get the total amount of seconds.
	 * 
	 * @return The seconds.
	 */
	public int toSeconds() {
		return seconds + (minutes * SECONDS_IN_MINUTE) + (hours * SECONDS_IN_HOUR) + (days * SECONDS_IN_DAY);
	}
	
	/**
	 * Get the total amount of milliseconds.
	 * 
	 * @return The milliseconds.
	 */
	public long toMilliseconds() {
		return toSeconds() * 1000;
	}
	
}
