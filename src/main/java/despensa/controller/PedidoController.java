package despensa.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import despensa.entities.Pedido;
import despensa.paging.Paginado;
import despensa.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	private static Logger LOG = LoggerFactory.getLogger(PedidoController.class);
	
	@GetMapping("")
	public ResponseEntity<Paginado<Pedido>> getAll(
			@RequestParam(name="page", defaultValue="0")int page,
			@RequestParam(name="size", defaultValue="10")int size){
		try {
			Page<Pedido> pedidos = this.pedidoService.getAll(page,size);
			if(!pedidos.isEmpty()) {
				Paginado<Pedido> paginadoCompras = new Paginado<Pedido>(pedidos.getContent(), page, pedidos.getSize(), size);
				return new ResponseEntity<Paginado<Pedido>>(paginadoCompras ,HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> getPedido(@PathVariable("id") int id){
		try {
			Optional<Pedido> pedido = this.pedidoService.getById(id);
			if(pedido.isPresent()) {
				return new ResponseEntity<Pedido>(pedido.get(),HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("")
	public ResponseEntity<Pedido> addPedido(@RequestBody Pedido p){
		try {
			boolean ok = this.pedidoService.addPedido(p);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(p, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("")
	public ResponseEntity<Pedido> updatePedido(@RequestBody Pedido p){
		try {
			boolean ok = this.pedidoService.updatePedido(p);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(p, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
