package tporcaro.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tporcaro.despensa.DTO.Venta;
import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.entities.Compra;
import tporcaro.despensa.repositories.CompraRepository;

/**
 * The Class CompraService.
 */
@Service
public class CompraService {

	/** The compraRepo. */
	@Autowired
	private CompraRepository compraRepo;
	
	/**
	 * Gets all Compra.
	 *
	 * @param page the page
	 * @param size the size
	 * @return page of Compra
	 */
	public Page<Compra> getAll(int page, int size) {
		return this.compraRepo.findAll(PageRequest.of(page, size));
	}

	/**
	 * Gets Compra by id.
	 *
	 * @param id the id
	 * @return Compra by id
	 */
	public Optional<Compra> getById(int id) {
		return this.compraRepo.findById(id);
	}

	/**
	 * Adds a Compra.
	 *
	 * @param c Compra
	 * @return true, if successful
	 */
	public boolean addCompra(Compra c) {
		Cliente clienteDueñoCompra = c.getCliente();
		c.reCalcularPrecio();
		if(clientePuedeSeguirComprando(clienteDueñoCompra)) {
			return this.compraRepo.save(c) != null;
		}
		return false;
	}

	/**
	 * Update Compra.
	 *
	 * @param c Compra
	 * @return true, if successful
	 */
	public boolean updateCompra(Compra c) {
		Compra compra = this.compraRepo.getById(c.getId());
		compra.setCliente(c.getCliente());
		compra.setFecha_compra(c.getFecha_compra());
		compra.setPrecio_total(c.getPrecio_total());
		Compra persistedCompra = this.compraRepo.save(compra);
		if(persistedCompra != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if Cliente can buy.
	 *
	 * @param c Cliente
	 * @return true, if successful
	 */
	private boolean clientePuedeSeguirComprando(Cliente c){
		List<Compra> comprasCliente = this.compraRepo.getComprasByCliente(c);
		if(comprasCliente.size() >= 3) {
			return false;
		}
		return true;
	}

	/**
	 * Generar reporte ventas.
	 *
	 * @return list of Venta
	 */
	public List<Venta> generarReporteVentas() {
		return this.compraRepo.generarReporteVentas();
	}
	
	/**
	 * Removes a compra.
	 *
	 * @param c Compra
	 * @return true, if successful
	 */
	public boolean removeCompra(Compra c) {
		 this.compraRepo.delete(c);
		 return !this.compraRepo.existsById(c.getId());
	}
	
	/**
	 * Empty the table Compra.
	 */
	public void vaciarCompra() {
		this.compraRepo.deleteAll();
	}
	
}
