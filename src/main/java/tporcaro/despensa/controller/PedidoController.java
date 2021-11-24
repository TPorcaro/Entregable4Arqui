package tporcaro.despensa.controller;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import tporcaro.despensa.entities.Pedido;
import tporcaro.despensa.paging.Paginado;
import tporcaro.despensa.services.PedidoService;


/**
 * The Class PedidoController.
 */
@RestController
@RequestMapping("/pedidos")
@Api(value = "PedidoController", description = "Controlador REST del Pedido")
public class PedidoController {
	
	/** The pedido service. */
	@Autowired
	private PedidoService pedidoService;
	
	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(PedidoController.class);
	
	/**
	 * Gets all Pedido.
	 *
	 * @param page the page
	 * @param size the size
	 * @return all Pedido
	 */
	@ApiOperation(value = "Obtiene un paginado de Pedidos", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
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
	
	/**
	 * Gets a pedido.
	 *
	 * @param id the id
	 * @return the Pedido
	 */
	@ApiOperation(value = "Obtiene un pedido por su id", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
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
	
	/**
	 * Adds a Pedido.
	 *
	 * @param p Pedido
	 * @return added Pedido
	 */
	@ApiOperation(value = "Crea un pedido", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
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
	
	/**
	 * Update Pedido.
	 *
	 * @param p Pedido
	 * @return updated Pedido
	 */
	@ApiOperation(value = "Actualiza un pedido", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
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
