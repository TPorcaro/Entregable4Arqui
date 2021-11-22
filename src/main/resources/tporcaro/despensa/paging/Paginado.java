package tporcaro.despensa.paging;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Paginado<T> {
	@ApiModelProperty(notes = "Lista de elementos",name = "elements")
	private List<T> elements;
	@ApiModelProperty(notes = "Numero de la pagina",name = "page")
	private int page;
	@ApiModelProperty(notes = "Cantidad maxima de paginas",name = "maxPage")
	private int maxPage;
	@ApiModelProperty(notes = "Tamaño de la pagina",name = "size")
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
