package info.malignantshadow.api.util.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * A class designed to aid in the pagination of elements. The Paginator object contains an {@link ArrayList} of objects that
 * is not manipulated by the paginator; the List is only read from when needed. Elements can be added to the list by get a reference
 * to the list via {@link #getElements()}
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 * @param <T>
 *            The type of object to paginate.
 */
public class Paginator<T> {
	
	private List<T> _elements;
	private int _perPage;
	
	/**
	 * Create a new {@link Paginator} with a default page size of {@code 7}
	 */
	public Paginator() {
		this(7);
	}
	
	/**
	 * Create a new {@link Paginator} with the given page size.
	 * 
	 * @param perPage
	 *            The page size
	 */
	public Paginator(int perPage) {
		_elements = new ArrayList<T>();
		_perPage = Math.max(1, perPage);
	}
	
	/**
	 * Get the backing {@link List} of this {@link Paginator}.
	 * 
	 * @return The list of elements
	 */
	public List<T> getElements() {
		return _elements;
	}
	
	/**
	 * Set the page size for this {@link Paginator}
	 * 
	 * @param perPage
	 *            The page size
	 */
	public void setElementsPerPage(int perPage) {
		_perPage = perPage;
	}
	
	/**
	 * Get this {@link Paginator}s page size
	 * 
	 * @return The page size
	 */
	public int getElementsPerPage() {
		return _perPage;
	}
	
	/**
	 * Get a page for this {@link Paginator}. {@code page} will be clamped to a number between {@code 1} and {@link #pages() pages()}.
	 * 
	 * @param page
	 *            The page to view
	 * @return A new list representing the page.
	 */
	public List<T> getPage(int page) {
		page = Math.max(1, Math.min(page, pages()));
		
		int start = (page - 1) * _perPage;
		List<T> pageList = new ArrayList<T>();
		for (int i = start; i < _elements.size(); i++) {
			if (i == start + _perPage)
				break;
			pageList.add(_elements.get(i));
		}
		return pageList;
	}
	
	/**
	 * Does this {@link Paginator} have the given page amount?
	 * 
	 * @param page
	 *            The page to test
	 * @return {@code true} if this Paginator has at least {@code page} pages to view
	 */
	public boolean hasPage(int page) {
		return page > 0 && page <= pages();
	}
	
	/**
	 * Get the amount of pages this {@link Paginator} holds.
	 * 
	 * @return The page amount
	 */
	public int pages() {
		if (_elements.isEmpty())
			return 0;
		
		if (_elements.size() < _perPage)
			return 1;
		
		return (_elements.size() / _perPage) + (_elements.size() % _perPage > 0 ? 1 : 0);
	}
}
