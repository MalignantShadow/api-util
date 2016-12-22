package info.malignantshadow.api.util.aliases;

public final class Aliases {
	
	private Aliases() {
	}
	
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
	
	public static boolean test(String alias, boolean notNull, boolean notEmpty, boolean noSpaces) {
		try {
			check(alias, notNull, notEmpty, noSpaces);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
}
