package despensa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import despensa.entities.Pedido;
import despensa.repositories.PedidoRepository;

public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepo;
	public Page<Pedido> getAll(int page, int size) {
		return this.pedidoRepo.findAll(PageRequest.of(page, size));
	}

	public Optional<Pedido> getById(int id) {
		return this.pedidoRepo.findById(id);
	}

	public boolean addPedido(Pedido p) {
		Pedido pedido = this.pedidoRepo.save(p);
		if(pedido != null) {
			return true;
		}
		return false;
	}

	public boolean updatePedido(Pedido p) {
		Pedido pedido = this.pedidoRepo.getById(p.getId());
		pedido.setCantidad(p.getCantidad());
		pedido.setProducto(p.getProducto());
		Pedido persistedPedido = this.pedidoRepo.save(pedido);
		if(persistedPedido != null) {
			return true;
		}
		return false;
	}

}