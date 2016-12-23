package info.malignantshadow.api.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an Object which custom data can be added.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class AttachableData {
	
	private Map<String, Object> _data = new HashMap<String, Object>();
	
	/**
	 * Set data on this object.
	 * 
	 * @param key
	 *            The key
	 * @param data
	 *            The value
	 * @return The previous value, if any.
	 */
	public Object setData(String key, Object data) {
		return _data.put(key, data);
	}
	
	/**
	 * Get data from this object.
	 * 
	 * @param key
	 *            The key
	 * @return The value.
	 */
	public Object getData(String key) {
		return _data.get(key);
	}
	
	/**
	 * Get all the data from this Object.
	 * 
	 * @return The data.
	 */
	public Map<String, Object> getData() {
		return _data;
	}
	
}
