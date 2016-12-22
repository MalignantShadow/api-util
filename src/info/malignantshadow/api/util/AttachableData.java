package info.malignantshadow.api.util;

import java.util.HashMap;
import java.util.Map;

public class AttachableData {
	
	private Map<String, Object> _data = new HashMap<String, Object>();
	
	public Object setData(String key, Object data) {
		return _data.put(key, data);
	}
	
	public Object getData(String key) {
		return _data.get(key);
	}
	
	public Map<String, Object> getData() {
		return _data;
	}
	
}
