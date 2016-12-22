package info.malignantshadow.api.util;

import java.util.ArrayList;
import java.util.List;

import info.malignantshadow.api.util.aliases.Aliasable;
import info.malignantshadow.api.util.aliases.Nameable;

public class StringUtil {
	
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
	
	public static <E extends Enum<E>> List<E> lenientEnumSearch(E[] values, String[] input) {
		List<E> found = new ArrayList<E>();
		if (input == null)
			return found;
		
		for (String s : input)
			found.addAll(lenientEnumSearch(values, s));
		
		return found;
	}
	
	public static String wrap(String string, int maxLength) {
		if (string == null || string.isEmpty())
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
			if (capitalize)
				result += capitalize(word) + " ";
			else
				result += word.toLowerCase() + " ";
		}
		return result.trim();
	}
	
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
	
	public static boolean eic(String s, String... tests) {
		if (tests.length == 0)
			return true;
		
		for (String t : tests)
			if (t.equalsIgnoreCase(s))
				return true;
			
		return false;
	}
	
}
