package info.malignantshadow.api.util.arguments;

/**
 * Represents a parsed command argument
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class ParsedArgument {
	
	private Argument _arg;
	private String _input;
	private Object _value;
	private boolean _defaultUsed;
	
	public ParsedArgument(Argument argument, String input) {
		this(argument, input, argument.getValue(input));
		_defaultUsed = Argument.shouldUseDefault(input);
	}
	
	public ParsedArgument(Argument argument, String input, Object value) {
		_arg = argument;
		_input = input;
		_value = value;
		_defaultUsed = false;
	}
	
	public boolean defaultUsed() {
		return _defaultUsed;
	}
	
	/**
	 * Get the {@link CommandArgument}
	 * 
	 * @return The argument
	 */
	public Argument getArgument() {
		return _arg;
	}
	
	/**
	 * Get the input for the argument.
	 * 
	 * @return The input
	 */
	public String getInput() {
		return _input;
	}
	
	/**
	 * Get the parsed value of the input.
	 * 
	 * @return The value
	 */
	public Object getValue() {
		return _value;
	}
	
}
