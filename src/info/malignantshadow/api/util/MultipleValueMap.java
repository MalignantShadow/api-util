package info.malignantshadow.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleValueMap<K, V> {
	
	private Map<K, List<V>> _map;
	
	public MultipleValueMap() {
		this(null);
	}
	
	public MultipleValueMap(Map<K, List<V>> map) {
		_map = map != null ? map : new HashMap<K, List<V>>();
	}
	
	public Map<K, List<V>> getMap() {
		return _map;
	}
	
	public void add(K key, V value) {
		List<V> list = getList(key, true);
		list.add(value);
	}
	
	public boolean remove(K key, V value) {
		List<V> list = getList(key);
		if (list == null)
			return false;
		
		return list.remove(value);
	}
	
	public boolean remove(K key, int index) {
		List<V> list = getList(key);
		if (list == null)
			return false;
		
		if (index < 0 || index >= list.size())
			return false;
		
		list.remove(index);
		return true;
	}
	
	public boolean contains(K key, V value) {
		List<V> list = getList(key);
		if (list == null)
			return false;
		
		return list.contains(value);
	}
	
	public int size() {
		int size = 0;
		for (Map.Entry<K, List<V>> e : _map.entrySet())
			size += e.getValue().size();
		
		return size;
	}
	
	public int size(K key) {
		List<V> list = getList(key);
		if (list == null)
			return -1;
		
		return list.size();
	}
	
	public List<V> getList(K key) {
		return getList(key, false);
	}
	
	public List<V> getList(K key, boolean create) {
		List<V> list = _map.get(key);
		if (list == null && create) {
			list = new ArrayList<V>();
			_map.put(key, list);
		}
		
		return list;
	}
	
	public void clear() {
		_map.clear();
	}
	
	public boolean isEmpty() {
		for (Map.Entry<K, List<V>> e : _map.entrySet()) {
			List<V> list = e.getValue();
			if (list != null && !list.isEmpty())
				return false;
		}
		
		return true;
	}
	
	public boolean isEmpty(K key) {
		List<V> list = getList(key);
		return list == null || list.isEmpty();
	}
	
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
