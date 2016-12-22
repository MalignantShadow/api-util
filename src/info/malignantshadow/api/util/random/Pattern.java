package info.malignantshadow.api.util.random;

public abstract class Pattern<T> extends WeightedRandom<T> {
	
	public Pattern(String string) {
		
		String[] split = string.split(",");
		for (String s : split) {
			int chance = 1;
			if (s.matches("[0-9]+%.+")) {
				chance = Integer.parseInt(s.substring(0, s.indexOf('%')));
				add(getObject(s.substring(s.indexOf('%') + 1, s.length())), chance);
			} else {
				add(getObject(s), chance);
			}
		}
	}
	
	protected abstract T getObject(String s);
	
}
