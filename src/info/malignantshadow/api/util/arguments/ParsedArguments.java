package info.malignantshadow.api.util.arguments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import info.malignantshadow.api.util.ListUtil;

public class ParsedArguments implements Iterable<ParsedArgument> {
	
	private List<ParsedArgument> _args;
	private String[] _input, _extra;
	
	public ParsedArguments() {
		this(new ArrayList<ParsedArgument>());
	}
	
	public ParsedArguments(ArgumentList args, String[] input) {
		if (input == null)
			input = new String[0];
		int required = args.getMinimum();
		int given = input.length;
		if (given < required)
			throw new IllegalArgumentException(String.format("Not enough arguments given: needed %d, but got %d", required, given));
		
		_input = input;
		_args = new ArrayList<ParsedArgument>();
		int argLength = args.size();
		_extra = new String[argLength > input.length ? 0 : input.length - argLength];
		if (_extra.length > 0)
			System.arraycopy(input, argLength, _extra, 0, _extra.length);
		
		//parsing
		int optionalLeft = input.length - required;
		int inputIndex = 0;
		
		for (int i = 0; i < args.size(); i++) {
			Argument a = args.get(i);
			if (a.isRequired()) {
				_args.add(new ParsedArgument(a, input[inputIndex++]));
			} else if (optionalLeft > 0) {
				optionalLeft--;
				_args.add(new ParsedArgument(a, input[inputIndex++]));
			} else {
				_args.add(new ParsedArgument(a, null));
			}
		}
	}
	
	public ParsedArguments(List<ParsedArgument> args) {
		_input = new String[0];
		_extra = new String[0];
		_args = args;
	}
	
	/**
	 * Get the input
	 * 
	 * @return The input
	 */
	public String[] getInput() {
		return _input;
	}
	
	/**
	 * Get the parsed object associated with the given argument name, or <code>null</code> if the argument doesn't exist.
	 * 
	 * @param name
	 *            The argument name
	 * @return The parsed object (may be null)
	 */
	public Object get(String name) {
		ParsedArgument arg = getArg(name);
		if (arg == null)
			return null;
		
		return arg.getValue();
	}
	
	public ParsedArgument getArg(String name) {
		return ListUtil.find(_args, (a) -> a.getArgument().getName().equals(name));
	}
	
	/**
	 * Get the list of parsed arguments
	 * 
	 * @return The parsed argument list
	 */
	public List<ParsedArgument> getArgs() {
		return _args;
	}
	
	/**
	 * <p>
	 * Get the extra arguments. These are arguments that haven't been mapped to an argument name, or arguments that weren't expected.
	 * </p>
	 * 
	 * For example, if a command with the syntax <code>eat-cookie &lt;type&gt; [amount]</code> was invoked via <code>eat-cookie chocolate-chip 7 I really like cookies</code>
	 * the arguments would be parsed as follows:
	 * <ul>
	 * <li><b>type</b> - <i>CookieType.CHOCOLATE_CHIP</i></li>
	 * <li><b>amount</b> - <i>7</i> (Integer)</li>
	 * </ul>
	 * However, the extra arguments aren't mapped to a command name. Using this method, you can retrieve these arguments. In the above instance, the extra arguments would be
	 * the String array <code>["I", "really", "like", "cookies"]</code>
	 * 
	 * @return The extra arguments.
	 */
	public String[] getExtra() {
		return _extra;
	}
	
	@Override
	public Iterator<ParsedArgument> iterator() {
		return _args.iterator();
	}
	
}
