package info.malignantshadow.api.util.random;

/**
 * An abstract pattern representing a
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 * @param <T>
 *            The type of Object each input element should be parsed as.
 */
public abstract class Pattern<T> extends WeightedRandom<T> {
	
	/**
	 * Create a new Pattern with the given input string.
	 * 
	 * @param string
	 *            The input string
	 */
	public Pattern(String string) {
		String[] split = string.split(",");
		for (String s : split) {
			int chance = 1;
			if (s.matches("[0-9]+%.+")) {
				chance = Integer.parseInt(s.substring(0, s.indexOf('%')));
				add(getValue(s.substring(s.indexOf('%') + 1, s.length())), chance);
			} else {
				add(getValue(s), chance);
			}
		}
	}
	
	/**
	 * Get a value using a substring from the input.
	 * 
	 * @param s
	 *            The substring
	 * @return The value.
	 */
	protected abstract T getValue(String s);
	
}
