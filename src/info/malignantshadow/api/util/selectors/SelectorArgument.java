package info.malignantshadow.api.util.selectors;

import java.util.Arrays;

import info.malignantshadow.api.util.ListUtil;

public class SelectorArgument {
	
	private String _name;
	private String[] _input;
	
	public SelectorArgument(String name, String[] input) {
		_name = name;
		_input = input;
	}
	
	public String getName() {
		return _name;
	}
	
	public String[] getInput() {
		return _input;
	}
	
	@Override
	public String toString() {
		return _name + "=" + ListUtil.join(Arrays.asList(_input), "|");
	}
	
}
