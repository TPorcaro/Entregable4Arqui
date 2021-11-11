package tporcaro.despensa.paging;

import java.util.List;

public class Paginado<T> {
	private List<T> elements;
	private int page;
	private int maxPage;
	private int size;
	
	public Paginado(List<T> elements, int page, int maxPage, int size) {
		super();
		this.elements = elements;
		this.page = page;
		this.maxPage = maxPage;
		this.size = size;
	}
	public List<T> getElements() {
		return elements;
	}
	public void setElements(List<T> elements) {
		this.elements = elements;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
