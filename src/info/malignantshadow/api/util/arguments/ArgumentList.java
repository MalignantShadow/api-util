package info.malignantshadow.api.util.arguments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import info.malignantshadow.api.util.ListUtil;

public class ArgumentList implements Iterable<Argument> {
	
	private List<Argument> _args;
	private Argument _extra;
	
	public ArgumentList() {
		this(new ArrayList<Argument>());
	}
	
	public ArgumentList(ArgumentList args) {
		this(args._args);
	}
	
	public ArgumentList(List<Argument> args) {
		_args = args;
	}
	
	/**
	 * Set the {@link Argument} that represents all the extra arguments that can be passed to this command.
	 * This argument is not included in {@link #getArgs(boolean)}.
	 * 
	 * @param display
	 *            The display string
	 * @param description
	 *            A string describing the extra arguments
	 * @param required
	 *            {@code true} if the argument is required
	 * @return this
	 */
	public ArgumentList setExtraArgument(String display, String description, boolean required) {
		_extra = new Argument("extra", display, description, required);
		return this;
	}
	
	/**
	 * Get the {@link Argument} that represents all the extra arguments that can be passed to this command.
	 * This argument is not included in {@link #getArgs(boolean)}.
	 * 
	 * @return The extra argument
	 */
	public Argument getExtraArgument() {
		return _extra;
	}
	
	/**
	 * Is this argument list empty?
	 * 
	 * @return {@code true} if empty
	 */
	public boolean isEmpty() {
		return _args.isEmpty();
	}
	
	/**
	 * Gets the size of this list
	 * 
	 * @return The size
	 */
	public int size() {
		return _args.size();
	}
	
	/**
	 * Get the argument at the specified index (or <code>null</code> if it doesn't exist).
	 * 
	 * @param index
	 *            The index
	 * @return The argument, if one exists. <code>null</code> otherwise.
	 */
	public Argument get(int index) {
		if (index < 0 || index >= _args.size())
			return null;
		
		return _args.get(index);
	}
	
	/**
	 * Get the argument with the given name.
	 * 
	 * @param name
	 *            The name
	 * @return The argument with the specified name.
	 */
	public Argument get(String name) {
		return ListUtil.find(_args, (a) -> a != null && a.getName().equals(name));
	}
	
	/**
	 * Does this command have an argument with the given name?
	 * 
	 * @param name
	 *            The name to test for (case-sensitive)
	 * @return true if this command has an argument with the given name, false otherwise
	 */
	public boolean hasArgWithName(String name) {
		for (Argument a : _args)
			if (a.getName().equals(name))
				return true;
		return false;
	}
	
	/**
	 * Add an argument to this command. The given argument cannot have the same name as another argument already added to this command.
	 * 
	 * @param arg
	 *            The argument to add
	 * @throws IllegalArgumentException
	 *             If the given arg has the same name as another argument added to this command
	 * @return this
	 */
	public ArgumentList add(Argument arg) {
		if (hasArgWithName(arg.getName()))
			throw new IllegalArgumentException(String.format("Cannot have two arguments with the same name (%s)", arg.getName()));
		
		_args.add(arg);
		return this;
	}
	
	/**
	 * Get the minimum argument count for this command.
	 * 
	 * @return The minimum argument count
	 */
	public int getMinimum() {
		int count = ListUtil.count(_args, (arg) -> arg.isRequired());
		if (_extra != null && _extra.isRequired())
			count++;
		
		return count;
	}
	
	/**
	 * Get arguments from this list.
	 * 
	 * @param required
	 *            {@code true} if only required args should be returned, {@code false} otherwise
	 * @return The arguments.
	 */
	public List<Argument> getArgs(boolean required) {
		return ListUtil.slice(_args, (arg) -> arg.isRequired() == required);
	}
	
	/**
	 * Get the required arguments in this list.
	 * 
	 * @return The required arguments.
	 */
	public List<Argument> getRequiredArgs() {
		return getArgs(true);
	}
	
	/**
	 * Get the optional arguments in this list.
	 * 
	 * @return The optional arguments.
	 */
	public List<Argument> getOptionalArgs() {
		return getArgs(false);
	}
	
	@Override
	public Iterator<Argument> iterator() {
		return _args.iterator();
	}
	
}
