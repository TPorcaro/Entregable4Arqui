package tporcaro.despensa.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import tporcaro.despensa.DTO.Venta;
import tporcaro.despensa.entities.Compra;
import tporcaro.despensa.paging.Paginado;
import tporcaro.despensa.services.CompraService;



/**
 * The Class CompraController.
 */
@RestController
@RequestMapping("/compras")
@Api(value = "CompraController", description = "Controlador REST de la Compra")
public class CompraController {

	/** The compra service. */
	@Autowired
	private CompraService compraService;
	
	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(CompraController.class);
	
	/**
	 * Gets all Compra.
	 *
	 * @param page the page
	 * @param size the size
	 * @return Paginado of Compra
	 */
	@ApiOperation(value = "Obtiene un paginado de compras", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("")
	public ResponseEntity<Paginado<EntityModel<Compra>>> getAll(
			@RequestParam(name="page", defaultValue="0")int page,
			@RequestParam(name="size", defaultValue="10")int size){
		try {
			Page<Compra> compras = this.compraService.getAll(page,size);
			if(!compras.isEmpty()) {
				Paginado<EntityModel<Compra>> paginadoCompras = new Paginado<EntityModel<Compra>>(compras.getContent().stream().
					map(c->getEm(c)).collect(Collectors.toList()), page, compras.getSize(), size);
				return new ResponseEntity<Paginado<EntityModel<Compra>>>(paginadoCompras ,HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Gets a Compra.
	 *
	 * @param id the id
	 * @return Compra
	 */
	@ApiOperation(value = "Obtiene una compra por su id", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Compra>> getCompra(@PathVariable("id") int id){
		try {
			Optional<Compra> compra = this.compraService.getById(id);
			if(compra.isPresent()) {
				return new ResponseEntity<EntityModel<Compra>>(getEm(compra.get()),HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Adds a compra.
	 *
	 * @param c Compra
	 * @return added Compra
	 */
	@ApiOperation(value = "Agrega una Compra", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@PostMapping("")
	public ResponseEntity<EntityModel<Compra>> addCompra(@RequestBody Compra c){
		try {
			boolean ok = this.compraService.addCompra(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(getEm(c), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Update a Compra.
	 *
	 * @param c Compra
	 * @return updated Compra
	 */
	@ApiOperation(value = "Actualiza una Compra", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@PutMapping("")
	public ResponseEntity<EntityModel<Compra>> updateCompra(@RequestBody Compra c){
		try {
			boolean ok = this.compraService.updateCompra(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(getEm(c), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Delete a Compra.
	 *
	 * @param c Compra
	 * @return deleted Compra
	 */
	@ApiOperation(value = "Borra una compra", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@DeleteMapping("")
	public ResponseEntity<Compra> deleteCompra(@RequestBody Compra c){
		try {
			boolean ok = this.compraService.removeCompra(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Generar reporte ventas.
	 *
	 * @return list of Venta
	 */
	@ApiOperation(value = "Obtiene un reporte de ventas por dias", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("/reporte")
	public ResponseEntity<List<Venta>> generarReporteVentas(){
		try {
			List<Venta> ventas = this.compraService.generarReporteVentas();
			if(!ventas.isEmpty()) {
				return new ResponseEntity<List<Venta>>(ventas, HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<List<Venta>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Gets the link of pedidos.
	 *
	 * @param c the c
	 * @return the link
	 */
	private EntityModel<Compra> getEm(Compra c) {
		EntityModel<Compra> em = EntityModel.of(c);
		List<Link> links = c.getPedidos().
				stream().
				map(a -> linkTo(methodOn(PedidoController.class).
						getPedido(a.getId())).withRel("pedidos")).
				collect(Collectors.toList());
		em.add(links);
		return em;
	}
	
}
