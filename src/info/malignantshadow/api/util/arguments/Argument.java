package info.malignantshadow.api.util.arguments;

import info.malignantshadow.api.util.aliases.Aliases;

/**
 * Represents an argument in a {@link info.malignantshadow.api.commands.Command Command}.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class Argument {
	
	private String _name, _desc, _display;
	private Type<?>[] _types;
	private Object _def;
	private boolean _required, _null;
	
	/**
	 * Construct a new instance with the given name and description.
	 * 
	 * @param name
	 *            The name of this argument. Cannot be null, empty, or contain spaces.
	 * @param description
	 *            The description of this argument
	 */
	public Argument(String name, String description, boolean required) {
		this(name, "", description, required);
	}
	
	/**
	 * Construct a new instance with the given name, display, and description.
	 * 
	 * <p>
	 * If the given display is not null or empty, then it will be displayed in help listings instead of the argument's name.
	 * </p>
	 * 
	 * @param name
	 *            The name of this argument. Cannot be null, empty, or contain spaces.
	 * @param display
	 *            The display string for this argument.
	 * @param description
	 *            The description of this argument.
	 * @param requried
	 *            {@code true} if this argument is required
	 */
	public Argument(String name, String display, String description, boolean required) {
		Aliases.check(name, true, true, true);
		_name = name;
		_display = display;
		_desc = description;
		_types = new Type<?>[] { ArgumentTypes.STRING };
		_def = null;
		_required = required;
		_null = false;
	}
	
	/**
	 * Should the default value be used for an argument?
	 * 
	 * @param input
	 *            The argument input
	 * @return {@code true} if the input is null or empty.
	 */
	public static boolean shouldUseDefault(String input) {
		return input == null || input.isEmpty();
	}
	
	/**
	 * Allow this argument to have a {@code null} value.
	 * 
	 * @return this
	 */
	public Argument thatMayBeNull() {
		return thatMightBeNull(true);
	}
	
	/**
	 * Set to {@code true} if this argument should not have a {@code null} value. Note that this flag does nothing except store a value, libraries should reference
	 * this flag and throw an exception if necessary. By default, this value is {@code false}.
	 * 
	 * @param canBeNull
	 *            Whether or not this argument accepts a null value
	 * @return this
	 */
	public Argument thatMightBeNull(boolean canBeNull) {
		_null = canBeNull;
		return this;
	}
	
	/**
	 * Does this argument accept null values?
	 * 
	 * @return {@code true} if this argument will accept a {@code null} value, {@code false} otherwise.
	 */
	public boolean canBeNull() {
		return _null;
	}
	
	/**
	 * Get the name of this argument.
	 * 
	 * @return The name of this argument.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Get this argument's display string. If this argument was construct without a display, the name is returned.
	 * 
	 * @return This argument's display
	 */
	public String getDisplay() {
		if (_display == null || _display.isEmpty())
			return _name;
		
		return _display;
	}
	
	/**
	 * Is this argument required?
	 * 
	 * @return {@code true} if this argument is required. {@code false} otherwise.
	 */
	public boolean isRequired() {
		return _required;
	}
	
	/**
	 * Get the description of this argument.
	 * 
	 * @return The description of this argument.
	 */
	public String getDescription() {
		return _desc;
	}
	
	/**
	 * Set the accepted argument types for this argument.
	 * 
	 * @param types
	 *            The argument types
	 * @return this
	 */
	public Argument withAcceptedTypes(Type<?>... types) {
		_types = types;
		return this;
	}
	
	/**
	 * Set the default value of this argument. When this argument is parsed, if this argument is optional and no input was given, the default will be used.
	 * 
	 * @param def
	 *            This argument's default value
	 * @return this
	 */
	public Argument withDefault(Object def) {
		_def = def;
		return this;
	}
	
	/**
	 * Get this argument's default value
	 * 
	 * @return The default value of this argument
	 */
	public Object getDefault() {
		return _def;
	}
	
	/**
	 * Get the value of this argument as if the given String was used as the input.
	 * 
	 * @param input
	 *            The input string
	 * @return The parsed value
	 */
	public Object getValue(String input) {
		if (shouldUseDefault(input))
			return _def;
		
		for (Type<?> t : _types) {
			Object value = t.getValue(input);
			if (value != null)
				return value;
		}
		return null;
	}
	
	/**
	 * Represents how an argument should be parsed.
	 * 
	 * @author MalignantShadow (Caleb Downs)
	 *
	 * @param <R>
	 *            An Object type representing the parsed value of the argument's input.
	 */
	@FunctionalInterface
	public static interface Type<R> {
		
		/**
		 * Parse the argument's input.
		 * 
		 * @param input
		 *            The input received for the argument
		 * @return The parsed value
		 */
		public R getValue(String input);
		
	}
	
}
