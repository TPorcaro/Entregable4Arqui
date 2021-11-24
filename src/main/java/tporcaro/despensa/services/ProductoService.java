package tporcaro.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tporcaro.despensa.entities.Producto;
import tporcaro.despensa.repositories.ProductoRepository;

/**
 * The Class ProductoService.
 */
@Service
public class ProductoService {
	
	/** The productoRepo. */
	@Autowired
	private ProductoRepository productoRepo;

	/**
	 * Update Producto.
	 *
	 * @param c Producto
	 * @return true, if successful
	 */
	public boolean updateProducto(Producto c) {
		Producto producto = this.productoRepo.getById(c.getId());
		producto.setNombre(c.getNombre());
		producto.setPrecio(c.getPrecio());
		producto.setStock(c.getStock());
		Producto persistedproducto = this.productoRepo.save(producto);
		if(persistedproducto != null) {
			return true;
		}
		return false;
	}

	/**
	 * Adds a Producto.
	 *
	 * @param c Producto
	 * @return true, if successful
	 */
	public boolean addProducto(Producto c) {
		return this.productoRepo.save(c) != null;
	}

	/**
	 * Gets Producto by id.
	 *
	 * @param id the id
	 * @return Producto by id
	 */
	public Optional<Producto> getById(int id) {
		return this.productoRepo.findById(id);
	}

	/**
	 * Gets all Producto.
	 *
	 * @param page the page
	 * @param size the size
	 * @return page of Producto
	 */
	public Page<Producto> getAll(int page, int size) {
		return this.productoRepo.findAll(PageRequest.of(page, size));
	}

	/**
	 * Gets the most selled Producto.
	 *
	 * @return the most selled Producto
	 */
	public Producto getProductoMasVendido() {
		List<Producto> List = this.productoRepo.getProductoMasVendido();
		return List.get(0);
	}
	
	/**
	 * Gets Producto by name.
	 *
	 * @param name the name
	 * @return Producto by name
	 */
	public Producto getByName(String name) {
		return this.productoRepo.getByName(name);
	}
	
	/**
	 * Removes a Producto.
	 *
	 * @param c Producto
	 * @return true, if successful
	 */
	public boolean removeProducto(Producto c) {
		 this.productoRepo.delete(c);
		 return !this.productoRepo.existsById(c.getId());
	}

}
