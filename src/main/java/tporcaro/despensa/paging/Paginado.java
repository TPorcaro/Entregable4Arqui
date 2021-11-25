package tporcaro.despensa.paging;

import java.util.List;

/**
 * The Class Paginado.
 *
 * @param <T> the generic type
 */
public class Paginado<T> {
	
	/** The elements. */
	private List<T> elements;
	
	/** The page. */
	private int page;
	
	/** The max page. */
	private int maxPage;
	
	/** The size. */
	private int size;
	
	/**
	 * Instantiates a new Paginado.
	 *
	 * @param elements the elements
	 * @param page the page
	 * @param maxPage the max page
	 * @param size the size
	 */
	public Paginado(List<T> elements, int page, int maxPage, int size) {
		super();
		this.elements = elements;
		this.page = page;
		this.maxPage = maxPage;
		this.size = size;
	}
	
	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public List<T> getElements() {
		return elements;
	}
	
	/**
	 * Sets the elements.
	 *
	 * @param elements the new elements
	 */
	public void setElements(List<T> elements) {
		this.elements = elements;
	}
	
	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * Sets the page.
	 *
	 * @param page the new page
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * Gets the max page.
	 *
	 * @return the max page
	 */
	public int getMaxPage() {
		return maxPage;
	}
	
	/**
	 * Sets the max page.
	 *
	 * @param maxPage the new max page
	 */
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
