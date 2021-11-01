package despensa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import despensa.entities.Compra;
import despensa.repositories.CompraRepository;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepo;
	
	public Page<Compra> getAll(int page, int size) {
		return this.compraRepo.findAll(PageRequest.of(page, size));
	}

	public Optional<Compra> getById(int id) {
		return this.compraRepo.findById(id);
	}

	public boolean addCompra(Compra c) {
		Compra compra = this.compraRepo.save(c);
		if(compra != null) {
			return true;
		}
		return false;
	}

	public boolean updateCompra(Compra c) {
		Compra compra = this.compraRepo.getById(c.getId());
		compra.setClient(c.getClient());
		compra.setFecha_compra(c.getFecha_compra());
		compra.setPrecio_total(c.getPrecio_total());
		Compra persistedCompra = this.compraRepo.save(compra);
		if(persistedCompra != null) {
			return true;
		}
		return false;
	}
	
}
