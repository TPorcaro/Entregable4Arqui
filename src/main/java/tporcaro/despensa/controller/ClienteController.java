
package tporcaro.despensa.controller;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

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
import tporcaro.despensa.DTO.ClienteConCompras;
import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.paging.Paginado;
import tporcaro.despensa.services.ClienteService;


/**
 * The Class ClienteController.
 */
@RestController
@RequestMapping("/clientes")
@Api(value = "ClienteController", description = "Controlador REST del cliente")
public class ClienteController {

	/** The cliente service. */
	@Autowired
	private ClienteService clienteService;
	
	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(ClienteController.class);
	
	/**
	 * Gets all Cliente.
	 *
	 * @param page the page
	 * @param size the size
	 * @return Paginado of Cliente
	 */
	@ApiOperation(value = "Obtiene un paginado de clientes", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("")
	public ResponseEntity<Paginado<Cliente>> getAll(
			@RequestParam(name="page", defaultValue="0")int page,
			@RequestParam(name="size", defaultValue="10")int size){
		try {
			Page<Cliente> clientes = this.clienteService.getAll(page,size);
			if(!clientes.isEmpty()) {
				Paginado<Cliente> paginadoClientes = new Paginado<Cliente>(clientes.getContent(), page, clientes.getSize(), size);
				return new ResponseEntity<Paginado<Cliente>>(paginadoClientes ,HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Gets a Cliente.
	 *
	 * @param id the id
	 * @return Cliente
	 */
	@ApiOperation(value = "Obtiene un cliente por su id", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getCliente(@PathVariable("id") int id){
		try {
			Optional<Cliente> cliente = this.clienteService.getById(id);
			if(cliente.isPresent()) {
				return new ResponseEntity<Cliente>(cliente.get(),HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Adds a Cliente.
	 *
	 * @param c Cliente
	 * @return added Cliente
	 */
	@ApiOperation(value = "Crea un cliente", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@PostMapping("")
	public ResponseEntity<Cliente> addCliente(@RequestBody Cliente c){
		try {
			boolean ok = this.clienteService.addCliente(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(c, HttpStatus.CREATED);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Update cliente.
	 *
	 * @param c Cliente
	 * @return updated Cliente
	 */
	@ApiOperation(value = "Actualiza un cliente", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@PutMapping("")
	public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente c){
		try {
			boolean ok = this.clienteService.updateCliente(c);
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
	 * Delete cliente.
	 *
	 * @param c CLiente
	 * @return deleted Cliente
	 */
	@ApiOperation(value = "Borra un cliente", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@DeleteMapping("")
	public ResponseEntity<Cliente> deleteCliente(@RequestBody Cliente c){
		try {
			boolean ok = this.clienteService.removeCliente(c);
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
	 * Generar reporte cliente.
	 *
	 * @return list of ClienteConCompra 
	 */
	@ApiOperation(value = "Devuelve un reporte donde se obtiene los clientes con "
			+ "su cantidad total de compras"
			, response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("/reporte")
	public ResponseEntity<List<ClienteConCompras>> generarReporteCliente(){
		try {
			List<ClienteConCompras> listaClienteConCompras = this.clienteService.generarReporteCliente();
			if(!listaClienteConCompras.isEmpty()) {
				return new ResponseEntity<>(listaClienteConCompras, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
