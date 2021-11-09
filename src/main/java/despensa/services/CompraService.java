package despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import despensa.DTO.Venta;
import despensa.entities.Cliente;
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
		Cliente clienteDueñoCompra = c.getClient();	
		if(clientePuedeSeguirComprando(clienteDueñoCompra)) {
			return this.compraRepo.save(c) != null;
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
	private boolean clientePuedeSeguirComprando(Cliente c){
		List<Compra> comprasCliente = this.compraRepo.getComprasByCliente(c.getId());
		if(comprasCliente.size() >= 3) {
			return false;
		}
		return true;
	}

	public List<Venta> generarReporteVentas() {
		return this.compraRepo.generarReporteVentas();
	}
	
}
