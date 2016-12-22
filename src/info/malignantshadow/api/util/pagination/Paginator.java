package info.malignantshadow.api.util.pagination;

import java.util.ArrayList;
import java.util.List;

public class Paginator<T> {
	
	private List<T> _elements;
	private int _perPage;
	
	public Paginator() {
		this(5);
	}
	
	public Paginator(int perPage) {
		_elements = new ArrayList<T>();
		_perPage = perPage;
	}
	
	public List<T> getElements() {
		return _elements;
	}
	
	public void setElementsPerPage(int perPage) {
		_perPage = perPage;
	}
	
	public int getElementsPerPage() {
		return _perPage;
	}
	
	public List<T> getPage(int page) {
		if (page < 0)
			page = 0;
		else if (page > pages())
			page = pages();
		
		int start = (page - 1) * _perPage;
		List<T> pageList = new ArrayList<T>();
		for (int i = start; i < _elements.size(); i++) {
			if (i == start + _perPage)
				break;
			pageList.add(_elements.get(i));
		}
		return pageList;
	}
	
	public boolean hasPage(int page) {
		return page > 0 && page <= pages();
	}
	
	public int pages() {
		if (_elements.isEmpty())
			return 0;
		
		if (_elements.size() < _perPage)
			return 1;
		
		int pages = _elements.size() / _perPage;
		return pages + (_elements.size() % _perPage > 0 ? 1 : 0);
	}
}
