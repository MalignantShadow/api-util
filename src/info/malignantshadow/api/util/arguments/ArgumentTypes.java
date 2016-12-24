package info.malignantshadow.api.util.arguments;

import java.util.regex.Matcher;

import info.malignantshadow.api.util.Time;
import info.malignantshadow.api.util.aliases.Aliasable;
import info.malignantshadow.api.util.selectors.Selector;

/**
 * A Utility class holding command argument types representing some Java primitives (and String)
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public final class ArgumentTypes {
	
	public static final String TIME_REGEX = "([0-9]*)([dDhHmMsS])";
	public static final String TIME_REGEX_MULTI = "(" + TIME_REGEX + ")+";
	
	private ArgumentTypes() {
	}
	
	/**
	 * Parse the argument as a String (return the input without modification)
	 */
	public static final Argument.Type<String> STRING = (input) -> input;
	
	/**
	 * Parse the argument as an Integer (or null if it cannot be parsed)
	 */
	public static final Argument.Type<Integer> INT = (input) -> {
		try {
			return Integer.valueOf(input);
		} catch (NumberFormatException e) {
			return null;
		}
	};
	
	/**
	 * Parse the argument as a {@link Time} object.
	 */
	public static final Argument.Type<Time> TIME = (input) -> {
		if (input == null || !input.matches(TIME_REGEX_MULTI))
			return null;
		
		java.util.regex.Pattern regex = java.util.regex.Pattern.compile(TIME_REGEX);
		Matcher matcher = regex.matcher(input);
		Time time = new Time();
		while (matcher.find()) {
			String g1 = matcher.group(1);
			int num = g1.isEmpty() ? 1 : Integer.parseInt(g1);
			String unit = matcher.group(2);
			if (unit.equalsIgnoreCase("d"))
				time.days += num;
			else if (unit.equalsIgnoreCase("h"))
				time.hours += num;
			else if (unit.equalsIgnoreCase("m"))
				time.minutes += num;
			else if (unit.equalsIgnoreCase("s"))
				time.seconds += num;
		}
		
		return time;
	};
	
	/**
	 * Parse the argument as a bitwise flag. The input will be passed to {@code {@link #arrayOf(info.malignantshadow.api.util.arguments.Argument.Type...) arrayOf}(flagType)} and
	 * each value (if not null) will be bitwise OR'd into a new integer. The resulting integer is the result of the argument type.
	 * 
	 * @param flagType
	 *            The argument type for parsing a single value of all the flags.
	 * @return A new argument type that represents bitwise flag parsing.
	 */
	public static final Argument.Type<Integer> bitwiseFlag(Argument.Type<Integer> flagType) {
		return (input) -> {
			int bits = 0;
			Object[] values = arrayOf(flagType).getValue(input);
			for (Object v : values) {
				Integer i = (Integer) v;
				if (i != null)
					bits |= i;
			}
			return bits;
		};
	}
	
	/**
	 * Parse the argument as a Double (or null if it cannot be parsed)
	 */
	public static final Argument.Type<Double> DOUBLE = (input) -> {
		try {
			return Double.valueOf(input);
		} catch (NumberFormatException e) {
			return null;
		}
	};
	
	/**
	 * Parse the argument as an Integer or Double.
	 * 
	 * <p>
	 * The argument will first try to be parsed as an Integer (via {@link #INT}),
	 * if that fails (the value is null) then it will parsed as a double (via {@link #DOUBLE})
	 * </p>
	 */
	public static final Argument.Type<Number> NUMBER = (input) -> {
		Number value = INT.getValue(input);
		if (value != null)
			return value;
		return DOUBLE.getValue(input);
	};
	
	/**
	 * Parse the argument as an {@link Integer}Integer (using {@link #NUMBER} and the returning {@link Number#intValue() intValue()} if it is not{@code null}).
	 * If the input would have had a {@link Double} value, it is coerced into an Integer.
	 */
	public static final Argument.Type<Integer> INT_LENIENT = (input) -> {
		Number number = NUMBER.getValue(input);
		if (number == null)
			return null;
		return number.intValue();
	};
	
	/**
	 * Parse the argument as a Boolean (or null if it cannot be parsed).
	 * 
	 * <p>
	 * <code> Boolean.valueOf()</code> is not used. Instead the argument will be parsed as follows:
	 * </p>
	 * <ul>
	 * <li>If the argument equals (ignoring case) "true", "yes" or "on", the returned value will be <code>true</code>}</li>
	 * <li>If the argument equals (ignoring case) "false", "no" or "off", the returned value will be <code>false</code></li>
	 * <li>Any other value will yield <code>null</code> as a result</li>
	 * </ul>
	 */
	public static final Argument.Type<Boolean> BOOLEAN = (input) -> {
		if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("on"))
			return true;
		else if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("off"))
			return false;
		else
			return null;
	};
	
	/**
	 * Parse the argument as a Boolean. If the input is {@code null} or empty, the default is returned. Otherwise, the return value
	 * of {@link #BOOLEAN} is returned;
	 * 
	 * @param def
	 *            The default
	 * @return A lenient argument type for booleans.
	 */
	public static final Argument.Type<Boolean> BOOLEAN_LENIENT(boolean def) {
		return (input) -> {
			if (input == null || input.equals(""))
				return def;
			return BOOLEAN.getValue(input);
		};
	}
	
	/**
	 * Parse the argument as any 'primitive' type.
	 * 
	 * <p>
	 * The argument will try to be parsed in the following order:
	 * </p>
	 * <ul>
	 * <li>Boolean (via {@link #BOOLEAN}</li>
	 * <li>Number (via {@link #NUMBER}</li>
	 * <li>If any of the above fails, the input is simply returned.</li>
	 * </ul>
	 * This will never return <code>null</code>, unless the input itself was <code>null</code>.
	 */
	public static final Argument.Type<Object> PRIMTIVE = (input) -> {
		Boolean b = BOOLEAN.getValue(input);
		if (b != null)
			return b;
		
		Number n = NUMBER.getValue(input);
		if (n != null)
			return n;
		
		return input;
	};
	
	/**
	 * Parse the argument as a {@link Selector}
	 */
	public static final Argument.Type<Selector> SELECTOR = (input) -> Selector.compile(input);
	
	/**
	 * Parse the argument as a {@link info.malignantshadow.api.util.random.Pattern Pattern}
	 * 
	 * @param type
	 *            An argument type representing the type of Object each element in the pattern should parsed as
	 * @param <T>
	 *            The Object type
	 * @return An argument type representing a Pattern.
	 */
	public static <T> Argument.Type<info.malignantshadow.api.util.random.Pattern<T>> pattern(Argument.Type<T> type) {
		return input -> new Pattern<T>(input, type);
	}
	
	/**
	 * Parse the argument as an array.
	 * 
	 * <p>
	 * The input will be split by commas, and each item will be parsed with the given types, in the order they were given.
	 * </p>
	 * 
	 * @param types
	 *            The argument types to be used for parsing
	 * @return A {@link Argument.Type} that represents array parsing
	 */
	public static final Argument.Type<Object[]> arrayOf(Argument.Type<?>... types) {
		return (input) -> {
			String[] split = input.split(",");
			Object[] values = new Object[split.length];
			
			for (int i = 0; i < split.length; i++) {
				String str = split[i].replace("\\,", ",");
				Object val = null;
				for (Argument.Type<?> t : types) {
					val = t.getValue(str);
					if (val != null)
						break;
				}
				values[i] = val;
			}
			
			return values;
		};
	}
	
	private static boolean conditionedEquals(String s, String test, boolean caseSensitive) {
		return caseSensitive ? s.equals(test) : s.equalsIgnoreCase(test);
	}
	
	/**
	 * Parse the argument as an Enum value. For each value in the array, the value's {@link Enum#name() name()} method method will be checked with the input,
	 * if they match (case-insensitive), the value is returned. If no value is found, <code>null</code> is returned.
	 * 
	 * <p>
	 * If the value implements {@link Aliasable}, the name and aliases of the value will be checked first (via {@link Aliasable#getName() getName()}
	 * and {@link Aliasable#getAliases() getAliases()}, respectively).
	 * </p>
	 * 
	 * @param enumValues
	 *            The allowed values
	 * @param <E>
	 *            The enum
	 * @return A {@link Argument.Type} that represents the Enum search
	 */
	public static final <E extends Enum<E>> Argument.Type<E> enumValue(E[] enumValues) {
		return enumValue(enumValues, false);
	}
	
	/**
	 * 
	 * Parse the argument as an Enum value. For each value in the array, the value's {@link Enum#name() name()} method method will be checked with the input,
	 * if they match (case-sensitive based on the <code>caseSenstive</code> argument), the value is returned. If no value is found, <code>null</code> is returned.
	 * 
	 * <p>
	 * If the value implements {@link Aliasable}, the name and aliases of the value will be checked first (via {@link Aliasable#getName() getName()}
	 * and {@link Aliasable#getAliases() getAliases()}, respectively).
	 * </p>
	 * 
	 * @param enumValues
	 *            The allowed values
	 * @param caseSensitive
	 *            <code>true</code> if the search is to be case-sensitive
	 * @param <E>
	 *            The enum2
	 * @return A {@link Argument.Type} that represents the Enum search
	 */
	public static final <E extends Enum<E>> Argument.Type<E> enumValue(E[] enumValues, boolean caseSensitive) {
		return (input) -> {
			for (E e : enumValues) {
				if (e == null)
					continue;
				
				if (e instanceof Aliasable) {
					Aliasable a = (Aliasable) e;
					if (conditionedEquals(a.getName(), input, caseSensitive))
						return e;
					
					for (String s : a.getAliases()) {
						if (conditionedEquals(s, input, caseSensitive))
							return e;
					}
				}
				
				if (conditionedEquals(e.name(), input, caseSensitive))
					return e;
			}
			return null;
		};
	}
	
	/**
	 * Represents a {@link info.malignantshadow.api.util.random.Pattern Pattern} that uses an {@link Argument.Type} to get the values of each substring.
	 * 
	 * @author MalignantShadow (Caleb Downs)
	 *
	 * @param <T>
	 *            The type of Object
	 */
	public static class Pattern<T> extends info.malignantshadow.api.util.random.Pattern<T> {
		
		private Argument.Type<T> _type;
		
		/**
		 * Create a new Pattern using the given arguments.
		 * 
		 * @param string
		 *            The input string
		 * @param type
		 *            The argument type to use when parsing each substring of the input
		 */
		public Pattern(String string, Argument.Type<T> type) {
			super(string);
			_type = type;
		}
		
		@Override
		protected T getValue(String s) {
			return _type.getValue(s);
		}
		
	}
	
}
