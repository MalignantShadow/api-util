package info.malignantshadow.api.util.aliases;

/**
 * Represents an Object that has a name and aliases.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public interface Aliasable extends Nameable {
	
	/**
	 * Get the aliases of this Object.
	 * 
	 * @return This Object's aliases.
	 */
	public String[] getAliases();
	
}
