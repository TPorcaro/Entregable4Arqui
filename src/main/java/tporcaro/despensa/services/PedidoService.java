package tporcaro.despensa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tporcaro.despensa.entities.Pedido;
import tporcaro.despensa.repositories.PedidoRepository;

/**
 * The Class PedidoService.
 */
@Service
public class PedidoService {

	/** The pedidoRepo. */
	@Autowired
	private PedidoRepository pedidoRepo;

	/**
	 * Gets all Pedido.
	 *
	 * @param page the page
	 * @param size the size
	 * @return page of Pedido
	 */
	public Page<Pedido> getAll(int page, int size) {
		return this.pedidoRepo.findAll(PageRequest.of(page, size));
	}

	/**
	 * Gets Pedido by id.
	 *
	 * @param id the id
	 * @return Pedido by id
	 */
	public Optional<Pedido> getById(int id) {
		return this.pedidoRepo.findById(id);
	}

	/**
	 * Adds a Pedido.
	 *
	 * @param p Pedido
	 * @return true, if successful
	 */
	public boolean addPedido(Pedido p) {
		Pedido pedido = this.pedidoRepo.save(p);
		if (pedido != null) {
			return true;
		}
		return false;
	}

	/**
	 * Update Pedido.
	 *
	 * @param p Pedido
	 * @return true, if successful
	 */
	public boolean updatePedido(Pedido p) {
		Pedido pedido = this.pedidoRepo.getById(p.getId());
		pedido.setCantidad(p.getCantidad());
		pedido.setProducto(p.getProducto());
		Pedido persistedPedido = this.pedidoRepo.save(pedido);
		if (persistedPedido != null) {
			return true;
		}
		return false;
	}

	/**
	 * Empty the table Pedido.
	 */
	public void vaciarPedido() {
		this.pedidoRepo.deleteAll();
	}

}
