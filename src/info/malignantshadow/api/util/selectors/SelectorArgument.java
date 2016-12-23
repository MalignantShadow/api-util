package info.malignantshadow.api.util.selectors;

import java.util.Arrays;

import info.malignantshadow.api.util.ListUtil;

/**
 * Represents an argument to a selector.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class SelectorArgument {
	
	private String _name;
	private String[] _input;
	
	/**
	 * Create a new selector argument
	 * 
	 * @param name
	 *            The name of the argument
	 * @param input
	 *            The inputs for the argument
	 */
	public SelectorArgument(String name, String[] input) {
		_name = name;
		_input = input;
	}
	
	/**
	 * Get the name of this argument
	 * 
	 * @return The name.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Get the inputs of this argument.
	 * 
	 * @return The inputs.
	 */
	public String[] getInput() {
		return _input;
	}
	
	@Override
	public String toString() {
		return _name + "=" + ListUtil.join(Arrays.asList(_input), "|");
	}
	
}
