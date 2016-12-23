package info.malignantshadow.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a map in which a key can have multiple values (or, more specifically, a list of values).
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 * @param <K>
 *            The key type
 * @param <V>
 *            The value types
 */
public class MultipleValueMap<K, V> {
	
	private Map<K, List<V>> _map;
	
	/**
	 * Construct a new map with an empty {@link HashMap}
	 */
	public MultipleValueMap() {
		this(null);
	}
	
	/**
	 * Construct a new MultipleValueMap using the given map as a basis. If the given map is a null, a new {@link HashMap} will be created instead.
	 * 
	 * @param map
	 */
	public MultipleValueMap(Map<K, List<V>> map) {
		_map = map != null ? map : new HashMap<K, List<V>>();
	}
	
	/**
	 * Get the map.
	 * 
	 * @return The Map this MultipleValueMap is backed by.
	 */
	public Map<K, List<V>> getMap() {
		return _map;
	}
	
	/**
	 * Add a new value to the given key.
	 * 
	 * @param key
	 *            The key
	 * @param value
	 *            The value
	 */
	public void add(K key, V value) {
		List<V> list = get(key, true);
		list.add(value);
	}
	
	/**
	 * Remove a value from the given key.
	 * 
	 * @param key
	 *            The key
	 * @param value
	 *            The value to remove
	 * @return {@code true} if the value was removed
	 */
	public boolean remove(K key, V value) {
		List<V> list = get(key);
		if (list == null)
			return false;
		
		return list.remove(value);
	}
	
	/**
	 * Remove a value from the given key.
	 * 
	 * @param key
	 *            The key
	 * @param index
	 *            The index of the value to remove.
	 * @return {@code true} if the value was removed
	 */
	public boolean remove(K key, int index) {
		List<V> list = get(key);
		if (list == null)
			return false;
		
		if (index < 0 || index >= list.size())
			return false;
		
		list.remove(index);
		return true;
	}
	
	/**
	 * Does the key contain the given value?
	 * 
	 * @param key
	 *            The key
	 * @param value
	 *            The value
	 * @return {@code true} if {@code key} contains the given value.
	 */
	public boolean contains(K key, V value) {
		List<V> list = get(key);
		if (list == null)
			return false;
		
		return list.contains(value);
	}
	
	/**
	 * Get the total size of this map.
	 * 
	 * @return The total size.
	 */
	public int size() {
		int size = 0;
		for (Map.Entry<K, List<V>> e : _map.entrySet())
			size += e.getValue().size();
		
		return size;
	}
	
	/**
	 * Get the amount of values the given key has.
	 * 
	 * @param key
	 *            The key
	 * @return The amount of values.
	 */
	public int size(K key) {
		List<V> list = get(key);
		if (list == null)
			return -1;
		
		return list.size();
	}
	
	/**
	 * Get the values of the given key.
	 * 
	 * @param key
	 *            The key.
	 * @return The values.
	 */
	public List<V> get(K key) {
		return get(key, false);
	}
	
	/**
	 * Get the values of the given key, and optionally create a new list of values if one doesn't already exist.
	 * 
	 * @param key
	 *            The key
	 * @param create
	 *            Whether or not to create a new list.
	 * @return The values
	 */
	public List<V> get(K key, boolean create) {
		List<V> list = _map.get(key);
		if (list == null && create) {
			list = new ArrayList<V>();
			_map.put(key, list);
		}
		
		return list;
	}
	
	/**
	 * Clear the map.
	 */
	public void clear() {
		_map.clear();
	}
	
	/**
	 * Is this map empty?
	 * 
	 * @return {@code true} if this map is empty
	 */
	public boolean isEmpty() {
		for (Map.Entry<K, List<V>> e : _map.entrySet()) {
			List<V> list = e.getValue();
			if (list != null && !list.isEmpty())
				return false;
		}
		
		return true;
	}
	
	/**
	 * Does the given key have exactly 0 values?
	 * 
	 * @param key
	 *            The key
	 * @return {@code true} if the values for {@code key} is empty.
	 */
	public boolean isEmpty(K key) {
		List<V> list = get(key);
		return list == null || list.isEmpty();
	}
	
	/**
	 * Get all values for every key.
	 * 
	 * @return All values.
	 */
	public List<V> values() {
		List<V> values = new ArrayList<V>();
		for (Map.Entry<K, List<V>> e : _map.entrySet()) {
			List<V> list = e.getValue();
			if (list == null || list.isEmpty())
				continue;
			values.addAll(list);
		}
		return values;
	}
}
