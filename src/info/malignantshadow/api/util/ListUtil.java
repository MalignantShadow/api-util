package info.malignantshadow.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Utility class for {@link List} manipulation
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class ListUtil {
	
	/**
	 * Represents an object that restricts the size of a list given a size
	 * 
	 * @author MalignantShadow (Caleb Downs)
	 *
	 */
	@FunctionalInterface
	public static interface SizeRestricter {
		
		/**
		 * Restrict the size of the given list. The method of restriction is undefined, but a list should be returned
		 * with a maximum size of {@code size} elements of the given list.
		 * 
		 * @param list
		 *            The list
		 * @param size
		 *            The size
		 * @return A new list containing a maximum of {@code size} elements of the given list.
		 */
		public <T> List<T> restrictSize(List<T> list, int size);
		
	}
	
	/**
	 * A size restricter that simply returns the first {@code size} elements in the given list.
	 * 
	 * @author MalignantShadow (Caleb Downs)
	 *
	 */
	public static class DefaultSizeRestricter implements SizeRestricter {
		
		@Override
		public <T> List<T> restrictSize(List<T> list, int size) {
			if (list == null || list.isEmpty() || size == 0)
				return new ArrayList<T>();
			
			int amount = Math.abs(size);
			List<T> restricted = new ArrayList<T>();
			for (int i = 0; i < amount; i++) {
				int index = size > 0 ? i : list.size() - 1 - i;
				if (index < 0 || index >= list.size())
					break;
				
				restricted.add(list.get(index));
			}
			return restricted;
		}
	}
	
	/**
	 * A size restricter that returns a list with a maximum size of {@code size} elements added at random from the given list.
	 * 
	 * @author MalignantShadow (Caleb Downs)
	 *
	 */
	public static class RandomSizeRestricter implements SizeRestricter {
		
		@Override
		public <T> List<T> restrictSize(List<T> list, int size) {
			List<T> restricted = new ArrayList<T>();
			if (list == null || list.isEmpty() || size <= size)
				return restricted;
			
			List<T> copy = new ArrayList<T>(list);
			Random r = new Random();
			for (int i = 0; i < size; i++) {
				if (copy.isEmpty())
					break;
				
				restricted.add(copy.remove(r.nextInt(copy.size())));
			}
			return restricted;
		}
		
	}
	
	/**
	 * Does the given list contain an element?
	 * 
	 * @param list
	 *            The list
	 * @param f
	 *            A Function that takes in an element from the list and returns a Boolean representing whether the item matches the desired criteria.
	 * @return {@code true} if the list contains the item.
	 */
	public static <T> boolean contains(List<T> list, Function<T, Boolean> f) {
		return indexOf(list, f) > -1;
	}
	
	/**
	 * Get the first item that matches desired criteria.
	 * 
	 * @param list
	 *            The list
	 * @param f
	 *            A Function that takes in an element from the list and returns a Boolean representing whether the item matches the desired criteria.
	 * @return The found item, or {@code null} if the given list is {@null} or empty, or the item isn't found
	 */
	public static <T> T find(List<T> list, Function<T, Boolean> f) {
		if (list == null || list.isEmpty() || f == null)
			return null;
		
		for (T t : list)
			if (f.apply(t))
				return t;
			
		return null;
	}
	
	/**
	 * Get the index of the first item matching desired criteria.
	 * 
	 * @param list
	 *            The list
	 * @param f
	 *            A Function that takes in an element from the list and returns a Boolean representing whether the item matches the desired criteria.
	 * @return The index of the first item matching the criteria.
	 */
	public static <T> int indexOf(List<T> list, Function<T, Boolean> f) {
		if (list == null || list.isEmpty() || f == null)
			return -1;
		
		for (int i = 0; i < list.size(); i++)
			if (f.apply(list.get(i)))
				return i;
			
		return -1;
	}
	
	/**
	 * Remove the first item that matches the desired criteria
	 * 
	 * @param list
	 *            The list
	 * @param f
	 *            A Function that takes in an element from the list and returns a Boolean representing whether the item matches the desired criteria.
	 * @return The removed element, or {@code null} if none was removed.
	 */
	public static <T> T remove(List<T> list, Function<T, Boolean> f) {
		int index = indexOf(list, f);
		if (index == -1)
			return null;
		
		return list.remove(index);
	}
	
	/**
	 * Get a list of all items within the given that match the desired criteria.
	 * 
	 * @param list
	 *            The list
	 * @param f
	 *            A Function that takes in an element from the list and returns a Boolean representing whether the item matches the desired criteria.
	 * @return A list of all items that match the criteria.
	 */
	public static <T> List<T> slice(List<T> list, Function<T, Boolean> f) {
		List<T> slice = new ArrayList<T>();
		if (list == null || list.isEmpty() || f == null)
			return slice;
		
		list.forEach((item) -> {
			if (f.apply(item))
				slice.add(item);
		});
		return slice;
	}
	
	/**
	 * Count the amount of items that match desired criteria.
	 * 
	 * @param list
	 *            The list
	 * @param f
	 *            A Function that takes in an element from the list and returns a Boolean representing whether the item matches the desired criteria.
	 * @return The amount of items matching the criteria
	 */
	public static <T> int count(List<T> list, Function<T, Boolean> f) {
		return slice(list, f).size();
	}
	
	/**
	 * Replace the first item in the given list that matches the desired criteria with another item.
	 * 
	 * @param list
	 *            The list
	 * @param item
	 *            The new item
	 * @param f
	 *            A Function that takes in an element from the list and returns a Boolean representing whether the item matches the desired criteria.
	 * @return {@code true} if the item was replaced
	 */
	public static <T> boolean replace(List<T> list, T item, Function<T, Boolean> f) {
		return replace(list, item, 1, f) > 0;
	}
	
	/**
	 * Replace the first {@code amount} items in the given list that matches the desired criteria with another item.
	 * 
	 * @param list
	 *            The list
	 * @param item
	 *            The new item
	 * @param amount
	 *            The amount of items to replace
	 * @param f
	 *            A Function that takes in an element from the list and returns a Boolean representing whether the item matches the desired criteria.
	 * @return The amount of items that were replaced
	 */
	public static <T> int replace(List<T> list, T item, int amount, Function<T, Boolean> f) {
		if (list == null || list.isEmpty() || amount <= 0 || f == null)
			return 0;
		
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (count == amount)
				break;
			
			T loopItem = list.get(i);
			if (f.apply(loopItem)) {
				list.set(i, item);
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Join all elements in the list together.
	 * 
	 * @param list
	 *            The list
	 * @return The resulting String.
	 */
	public static <T> String join(List<T> list) {
		return join(list, (item) -> "" + item);
	}
	
	/**
	 * Join all elements in the list together.
	 * 
	 * @param list
	 *            The list
	 * @param describer
	 *            A Function that takes in an item of the list and returns a String that describes that item.
	 * @return The resulting String
	 */
	public static <T> String join(List<T> list, Function<T, String> describer) {
		return join(list, describer, " ");
	}
	
	/**
	 * Join all elements in the list together, separated by a delimiter.
	 * 
	 * @param list
	 *            The list
	 * @param delimiter
	 *            The delimiter
	 * @return The resulting String
	 */
	public static <T> String join(List<T> list, String delimiter) {
		return join(list, (item) -> "" + item, delimiter);
	}
	
	/**
	 * Join all elements in the list together, separated by a delimiter.
	 * 
	 * @param list
	 *            The list
	 * @param delimiter
	 *            The delimiter
	 * @param describer
	 *            A Function that takes in an item of the list and returns a String that describes that item.
	 * @return The resulting String
	 */
	public static <T> String join(List<T> list, Function<T, String> describer, String delimiter) {
		if (list == null || list.isEmpty())
			return "";
		
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += describer.apply(list.get(i));
			if (i < list.size() - 1)
				result += delimiter;
		}
		
		return result;
	}
	
}
