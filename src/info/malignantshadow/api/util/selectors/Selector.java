package info.malignantshadow.api.util.selectors;

import java.util.ArrayList;
import java.util.List;

import info.malignantshadow.api.util.AttachableData;
import info.malignantshadow.api.util.ListUtil;
import info.malignantshadow.api.util.arguments.Argument;
import info.malignantshadow.api.util.arguments.ArgumentTypes;

public class Selector extends AttachableData {
	
	public static final String NAME_REGEX = "^[~!@#\\$%\\.]\\w+";
	
	public static final String CONTEXT_REGEX = NAME_REGEX + "(\\[[\\w=\\$\\^~!-]*\\])?";
	
	private String _name;
	private List<SelectorArgument> _args;
	
	public Selector(String name, List<SelectorArgument> args) {
		_name = name;
		_args = args;
	}
	
	public String getName() {
		return _name;
	}
	
	public boolean nameIs(String name) {
		return _name.equalsIgnoreCase(name);
	}
	
	public List<SelectorArgument> getArgs() {
		return _args;
	}
	
	public void add(String name, String... input) {
		add(new SelectorArgument(name, input));
	}
	
	public void add(SelectorArgument arg) {
		if (arg != null && !ListUtil.replace(_args, arg, (a) -> a != null && a.getName().equals(arg.getName())))
			_args.add(arg);
	}
	
	public SelectorArgument remove(String name) {
		return ListUtil.remove(_args, (arg) -> arg != null && arg.getName().equals(name));
	}
	
	public SelectorArgument get(String name) {
		return ListUtil.find(_args, (arg) -> arg != null && arg.getName().equals(name));
	}
	
	public String[] getInputFor(String name) {
		SelectorArgument arg = get(name);
		if (arg == null)
			return null;
		
		return arg.getInput();
	}
	
	public boolean hasInputFor(String name) {
		return getInputFor(name) != null;
	}
	
	public List<String> getAll(String name) {
		return getAll(name, ArgumentTypes.STRING);
	}
	
	public <R> List<R> getAll(String name, Argument.Type<R> type) {
		return getAll(name, type, -1);
	}
	
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
	
	public <R> R getOne(String name, Argument.Type<R> type) {
		List<R> all = getAll(name, type, 1);
		if (all == null || all.isEmpty())
			return null;
		
		return all.get(0);
	}
	
	public boolean isSet(String name) {
		return get(name) != null;
	}
	
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
