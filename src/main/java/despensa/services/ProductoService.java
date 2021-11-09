package despensa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import despensa.entities.Producto;
import despensa.repositories.ProductoRepository;
@Service
public class ProductoService {
	@Autowired
	private ProductoRepository productoRepo;

	public boolean updateProducto(Producto c) {
		Producto producto = this.productoRepo.getById(c.getId());
		
		Producto persistedproducto = this.productoRepo.save(producto);
		if(persistedproducto != null) {
			return true;
		}
		return false;
	}

	public boolean addProducto(Producto c) {
		return this.productoRepo.save(c) != null;
	}

	public Optional<Producto> getById(int id) {
		return this.productoRepo.findById(id);
	}

	public Page<Producto> getAll(int page, int size) {
		return this.productoRepo.findAll(PageRequest.of(page, size));
	}

}
