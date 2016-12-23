package info.malignantshadow.api.util.selectors;

import java.util.ArrayList;
import java.util.List;

import info.malignantshadow.api.util.AttachableData;
import info.malignantshadow.api.util.ListUtil;
import info.malignantshadow.api.util.arguments.Argument;
import info.malignantshadow.api.util.arguments.ArgumentTypes;

/**
 * Represents a selector. The syntax for a selector is largely inspired by Minecraft'a target selector system, but some improvements have been made to it.
 * See <a href='https://www.github.com/MalignantShadow/api-util/wiki/Selectors'>the wiki</a> for more information.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class Selector extends AttachableData {
	
	/**
	 * The regex used in determining whether the name of a selector is valid. The name can one or more alphanmeric characters, but can optionally be
	 * prefixed by one of the following characters: {@code ~!@#$*%.?}
	 */
	public static final String NAME_REGEX = "^[~!@#\\$%\\.\\*\\?]?\\w+";
	
	/**
	 * The regex in determining whether the entire input string is valid.
	 */
	public static final String CONTEXT_REGEX = NAME_REGEX + "(\\[[\\w=\\$\\^~!-|\\*><]*\\])?";
	
	private String _name;
	private List<SelectorArgument> _args;
	
	/**
	 * Create a new Selector context with the given name and argument values.
	 * 
	 * @param name
	 * @param args
	 */
	public Selector(String name, List<SelectorArgument> args) {
		_name = name;
		_args = args;
	}
	
	/**
	 * Get the name of this selector.
	 * 
	 * @return
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Does this selector have the given name?
	 * 
	 * @param name
	 *            The name to test for
	 * @return {@code true} if the name matches.
	 */
	public boolean nameIs(String name) {
		return _name.equalsIgnoreCase(name);
	}
	
	/**
	 * Get the arguments of this selector.
	 * 
	 * @return The arguments.
	 */
	public List<SelectorArgument> getArgs() {
		return _args;
	}
	
	/**
	 * Add an argument to this Selector.
	 * 
	 * @param name
	 *            The name of the argument
	 * @param input
	 *            The input to argument
	 */
	public void add(String name, String... input) {
		add(new SelectorArgument(name, input));
	}
	
	/**
	 * Add an argument to this selector. It will not be added if it is {@code null}
	 * 
	 * @param arg
	 *            The argument
	 */
	public void add(SelectorArgument arg) {
		if (arg != null && !ListUtil.replace(_args, arg, (a) -> a != null && a.getName().equals(arg.getName())))
			_args.add(arg);
	}
	
	/**
	 * Remove the argument with the given name from this selector.
	 * 
	 * @param name
	 *            The name of the argument
	 * @return The argument that was removed.
	 */
	public SelectorArgument remove(String name) {
		return ListUtil.remove(_args, (arg) -> arg != null && arg.getName().equals(name));
	}
	
	/**
	 * Get the argument with the given name.
	 * 
	 * @param name
	 *            The name of the argument
	 * @return The argument.
	 */
	public SelectorArgument get(String name) {
		return ListUtil.find(_args, (arg) -> arg != null && arg.getName().equals(name));
	}
	
	/**
	 * Get the input for the argument with the given name
	 * 
	 * @param name
	 *            The name of the argument
	 * @return The input, or {@code null} if the argument wasn't found
	 */
	public String[] getInputFor(String name) {
		SelectorArgument arg = get(name);
		if (arg == null)
			return null;
		
		return arg.getInput();
	}
	
	/**
	 * Does the argument with the given name have any input?
	 * 
	 * @param name
	 *            The name of the argument
	 * @return {@code true} if the argument has any input.
	 */
	public boolean hasInputFor(String name) {
		return getInputFor(name) != null;
	}
	
	/**
	 * Get all inputs for the argument with the given name.
	 * 
	 * @param name
	 *            The name of the argument
	 * @return The inputs.
	 */
	public List<String> getAll(String name) {
		return getAll(name, ArgumentTypes.STRING);
	}
	
	/**
	 * Get all inputs for the argument with the given name and transform them into the given argument type
	 * 
	 * @param name
	 *            The name of the argument
	 * @param type
	 *            The type to transform the inputs into
	 * @return The parsed inputs.
	 */
	public <R> List<R> getAll(String name, Argument.Type<R> type) {
		return getAll(name, type, -1);
	}
	
	/**
	 * Get a maximum of {@code max} inputs for the argument with the given name and transform them into the given argument type.
	 * 
	 * @param name
	 *            The name of the argument
	 * @param type
	 *            The type to transform the inputs into
	 * @param max
	 *            The maximum amount of inputs to return
	 * @return The parsed inputs.
	 */
	public <R> List<R> getAll(String name, Argument.Type<R> type, int max) {
		SelectorArgument arg = get(name);
		if (arg == null || type == null)
			return null;
		
		List<R> all = new ArrayList<R>();
		if (arg.getInput() == null || arg.getInput().length == 0 || max == 0)
			return all;
		
		int count = 0;
		for (String s : arg.getInput())
			if (max < 0 || count < max)
				all.add(type.getValue(s));
			
		return all;
	}
	
	/**
	 * Get the first input for the argument with the given name.
	 * 
	 * @param name
	 *            The name of the argument
	 * @return The input.
	 */
	public String getOne(String name) {
		return getOne(name, ArgumentTypes.STRING);
	}
	
	/**
	 * Get the first input for the argument with the given name and transform it into the given argument type.
	 * 
	 * @param name
	 *            The name of the argument
	 * @param type
	 *            The type to transform the input into
	 * @return The parsed input.
	 */
	public <R> R getOne(String name, Argument.Type<R> type) {
		List<R> all = getAll(name, type, 1);
		if (all == null || all.isEmpty())
			return null;
		
		return all.get(0);
	}
	
	/**
	 * Is the given argument set?
	 * 
	 * @param name
	 *            The name of the argument.
	 * @return {@code true if the argument with the given name is set}
	 */
	public boolean isSet(String name) {
		return get(name) != null;
	}
	
	/**
	 * Compile a selector
	 * 
	 * @param s
	 *            The input string
	 * @return The compiled selector.
	 */
	public static final Selector compile(String s) {
		if (!s.matches(CONTEXT_REGEX))
			return null;
		
		String name = s.substring(0, s.contains("[") ? s.indexOf('[') : s.length());
		if (!name.matches(NAME_REGEX))
			return null;
		
		Selector selector = new Selector(name, new ArrayList<SelectorArgument>());
		if (name.length() == s.length() || s.length() == name.length() + 2) //no arguments given
			return selector;
		
		String arguments = s.substring(s.indexOf('[') + 1, s.indexOf(']'));
		String[] pairs = arguments.split(",");
		for (String p : pairs) {
			if (p.isEmpty())
				continue;
			
			int index = p.indexOf('=');
			if (index == -1 || index == p.length() - 1) { //no '=' or ends with it
				selector.add(new SelectorArgument(p, new String[0]));
				continue;
			}
			
			String argName = p.substring(0, index);
			String[] input = p.substring(index + 1, p.length()).split("\\|");
			selector.add(new SelectorArgument(argName, input));
		}
		return selector;
	}
	
	@Override
	public String toString() {
		String s = _name;
		if (_args.isEmpty())
			return s + "[]";
		
		return s + "[" + ListUtil.join(_args, ",") + "]";
	}
	
}
