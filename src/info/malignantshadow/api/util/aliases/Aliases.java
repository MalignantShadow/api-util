package info.malignantshadow.api.util.aliases;

/**
 * Utility class involving aliases.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public final class Aliases {
	
	private Aliases() {
	}
	
	/**
	 * Check the supplied aliases with the given options.
	 * 
	 * @param alias
	 *            The alias to check
	 * @param notNull
	 *            Whether or not the alias can be null
	 * @param notEmpty
	 *            Whether or not the alias can be an empty String.
	 * @param noSpaces
	 *            Whether or not the alias is allowed to have spaces.
	 * @throws IllegalArgumentException
	 *             If the alias does not meet the requirements.
	 */
	public static void check(String alias, boolean notNull, boolean notEmpty, boolean noSpaces) {
		if (notNull && alias == null)
			throw new IllegalArgumentException("alias cannot be null");
		
		if (alias == null)
			return;
		
		if (notEmpty && alias.isEmpty())
			throw new IllegalArgumentException("alias cannot be empty");
		if (noSpaces && alias.contains(" "))
			throw new IllegalArgumentException("alias cannot contain spaces");
	}
	
	/**
	 * Test the supplied aliases with the given options.
	 * 
	 * @param alias
	 *            The alias to check
	 * @param notNull
	 *            Whether or not the alias can be null
	 * @param notEmpty
	 *            Whether or not the alias can be an empty String.
	 * @param noSpaces
	 *            Whether or not the alias is allowed to have spaces.
	 * @return {@code true} if the alias meets the requirements.
	 */
	public static boolean test(String alias, boolean notNull, boolean notEmpty, boolean noSpaces) {
		try {
			check(alias, notNull, notEmpty, noSpaces);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
}
