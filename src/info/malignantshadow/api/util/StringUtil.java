package info.malignantshadow.api.util;

import java.util.ArrayList;
import java.util.List;

import info.malignantshadow.api.util.aliases.Aliasable;
import info.malignantshadow.api.util.aliases.Nameable;

/**
 * Utility class for Strings.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class StringUtil {
	
	/**
	 * Test if a string leniently matches another. The test string can be prefixed with the following characters:
	 * <ul>
	 * <li>~ - The method will return true if {@code s} contains the substring {@code test} (case-insensitive)</li>
	 * <li>* - The method will return true if {@code s} ends with {@code test} (case-insensitive)</li>
	 * <li>$ - The method will return true if {@code s} ends with {@code test} (case sensitive)</li>
	 * <li>^ - The method will return true if {@code s} starts with {@code test} (case in-sensitive)</li>
	 * <li>% - The method will return true if {@code s} starts with {@code test} (case sensitive)</li>
	 * <li>= - The method will return true if {@code s} equals {@code test} (case sensitive)</li>
	 * <li>> - The method will return true if {@code s} is positioned after {@code test} in a sorted list/array (case sensitive)</li>
	 * <li>< - The method will return true if {@code s} is positioned before {@code test} in a sorted list/array (case sensitive)</li>
	 * <li>! - Recursive. The method will return the result of {@code lenientMatch(s, sub)} where {@code sub} is equal to {@code test.substring(1)}}</li>
	 * <li></li>
	 * </ul>
	 * 
	 * @param s
	 * @param test
	 * @return
	 */
	public static boolean lenientMatch(String s, String test) {
		if (test == null ^ s == null) //if one (but not both) are null
			return false;
		
		if ((test == null && s == null) || (s.isEmpty() && test.isEmpty()))
			return true;
		
		char first = test.charAt(0);
		String sub = test.substring(1);
		if (first == '~') // "similar" [.contains()]
			return s.toLowerCase().contains(sub.toLowerCase());
		if (first == '*') // ends with
			return s.toLowerCase().endsWith(sub.toLowerCase());
		if (first == '$') //strict ends with
			return s.endsWith(sub);
		if (first == '^') //starts with
			return s.toLowerCase().startsWith(sub.toLowerCase());
		if (first == '%') //strict starts with
			return s.startsWith(sub);
		if (first == '=') // strict equals
			return s.equals(sub);
		if (first == '>') //compareTo: sub is greater than s
			return sub.compareTo(s) >= 1;
		if (first == '<') //compareTo: sub is less than s
			return sub.compareTo(s) <= -1;
		if (first == '!') //not 
			return !lenientMatch(s, sub); //the downside to this is that "arg=!!!!!!!!!value" is completely valid
			
		return s.equalsIgnoreCase(test);
	}
	
	/**
	 * Iterate through an array of enum values and return any values whose name (or aliases) leniently match the given name.
	 * 
	 * @param values
	 *            The values
	 * @param name
	 *            The name
	 * @return A List of matching values.
	 */
	public static <E extends Enum<E>> List<E> lenientEnumSearch(E[] values, String name) {
		List<E> list = new ArrayList<E>();
		for (E e : values) {
			if (lenientMatch(e.name(), name))
				list.add(e);
			else if (e instanceof Nameable) {
				Nameable n = (Nameable) e;
				if (lenientMatch(n.getName(), name))
					list.add(e);
				else if (n instanceof Aliasable) {
					String[] aliases = ((Aliasable) n).getAliases();
					for (String s : aliases) {
						if (lenientMatch(s, name)) {
							list.add(e);
							break;
						}
					}
				}
			}
		}
		
		return list;
	}
	
	/**
	 * Iterate through an array of enum values and return any values whose name (or aliases) leniently match any of the given names.
	 * 
	 * @param values
	 *            The values
	 * @param name
	 *            The name
	 * @return A List of matching values.
	 */
	public static <E extends Enum<E>> List<E> lenientEnumSearch(E[] values, String[] input) {
		List<E> found = new ArrayList<E>();
		if (input == null)
			return found;
		
		for (String s : input)
			found.addAll(lenientEnumSearch(values, s));
		
		return found;
	}
	
	/**
	 * Apply word wrapping to the given string.
	 * 
	 * @param string
	 *            The text to wrap
	 * @param maxLength
	 *            The maximum character length of a line.
	 * @return The same string, with the newline character inserted where needed to apply text wrap.
	 */
	public static String wrap(String string, int maxLength) {
		if (string == null || string.isEmpty() || string.length() <= maxLength)
			return string;
		
		String[] split = string.split("\\s+");
		int counter = 0;
		String newString = "";
		for (String s : split) {
			if (counter + s.length() + 1 <= maxLength)
				newString += s + " ";
			else {
				newString += "\n" + s + " ";
				counter = 0;
			}
			counter += s.length() + 1;
		}
		return newString;
	}
	
	/**
	 * Make a string proper case. All words, aside from some small words, will be capitalized (this is ignored if the word is the first or last word).
	 * 
	 * @param s
	 *            The string
	 * @return The proper-cased string
	 */
	public static String toProperCase(String s) {
		if (s.isEmpty())
			return "";
		String[] unimportant = new String[] { "a", "an", "and", "but", "is",
			"are", "for", "nor", "of", "or", "so", "the", "to", "yet", "by" };
		String[] split = s.split("\\s+");
		String result = "";
		for (int i = 0; i < split.length; i++) {
			String word = split[i];
			boolean capitalize = true;
			for (String str : unimportant) {
				if (str.equalsIgnoreCase(word)) {
					if (i > 0 && i < split.length - 1) {
						capitalize = false;
						break;
					}
				}
			}
			if (capitalize || i == 0 || i == split.length - 1) //first and last words are always capital
				result += capitalize(word) + " ";
			else
				result += word.toLowerCase() + " ";
		}
		return result.trim();
	}
	
	/**
	 * Make the first character in the string upper cased.
	 * 
	 * @param s
	 *            The string
	 * @return The same string, with the first character upper-cased
	 */
	public static String capitalize(String s) {
		if (s.isEmpty())
			return "";
		if (s.length() == 1) {
			return s.toUpperCase();
		} else if (s.length() == 2) {
			String first = (s.charAt(0) + "").toUpperCase();
			String sec = (s.charAt(1) + "").toLowerCase();
			return first + sec;
		} else {
			s = s.toUpperCase();
			return s.charAt(0) + s.substring(1, s.length()).toLowerCase();
		}
	}
	
	/**
	 * Tests if any of the given test string match ({@link String#equalsIgnoreCase(String)}) {@code s}
	 * 
	 * @param s
	 *            The string
	 * @param tests
	 *            Any amount of test string
	 * @return true if one of the given strings matches (case-insensitive) {@code s}
	 */
	public static boolean eic(String s, String... tests) {
		if (tests.length == 0)
			return true;
		
		for (String t : tests)
			if (t.equalsIgnoreCase(s))
				return true;
			
		return false;
	}
	
}
