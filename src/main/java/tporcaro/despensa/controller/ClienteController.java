package tporcaro.despensa.controller;

import java.util.List;
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

import tporcaro.despensa.DTO.ClienteConCompras;
import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.paging.Paginado;
import tporcaro.despensa.services.ClienteService;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	private static Logger LOG = LoggerFactory.getLogger(ClienteController.class);
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
	@PostMapping("")
	public ResponseEntity<Cliente> addCliente(@RequestBody Cliente c){
		try {
			boolean ok = this.clienteService.addCliente(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
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
	@GetMapping("/reporte")
	public ResponseEntity<List<ClienteConCompras>> generarReporteCliente(){
		try {
			List<ClienteConCompras> listaClienteConCompras = this.clienteService.generarReporteCliente();
			//if(!listaClienteConCompras.isEmpty()) {
				return new ResponseEntity<>(listaClienteConCompras, HttpStatus.OK);
			//}
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
