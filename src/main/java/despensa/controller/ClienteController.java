package despensa.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

import despensa.entities.Cliente;
import despensa.paging.Paginado;
import despensa.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	private static Logger LOG = LoggerFactory.getLogger(ClienteController.class);
	@GetMapping("")
	public ResponseEntity<Paginado<EntityModel<Cliente>>> getAll(
			@RequestParam(name="page", defaultValue="0")int page,
			@RequestParam(name="size", defaultValue="10")int size){
		try {
			Page<Cliente> clientes = this.clienteService.getAll(page,size);
			if(!clientes.isEmpty()) {
				Paginado<EntityModel<Cliente>> paginadoClientes = new Paginado<EntityModel<Cliente>>(clientes.getContent().stream().
					map(c->getEm(c)).collect(Collectors.toList()), page, clientes.getSize(), size);
				return new ResponseEntity<Paginado<EntityModel<Cliente>>>(paginadoClientes ,HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Cliente>> getCliente(@PathVariable("id") int id){
		try {
			Optional<Cliente> cliente = this.clienteService.getById(id);
			if(cliente.isPresent()) {
				return new ResponseEntity<EntityModel<Cliente>>(getEm(cliente.get()),HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("")
	public ResponseEntity<EntityModel<Cliente>> addCliente(@RequestBody Cliente c){
		try {
			boolean ok = this.clienteService.addCliente(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(getEm(c), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("")
	public ResponseEntity<EntityModel<Cliente>> updateCliente(@RequestBody Cliente c){
		try {
			boolean ok = this.clienteService.updateCliente(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(getEm(c), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	private EntityModel<Cliente> getEm(Cliente c) {
		EntityModel<Cliente> em = EntityModel.of(c);
		List<Link> links = c.getCompras().
				stream().
				map(a -> linkTo(methodOn(CompraController.class).
						getCompra(a.getId())).withRel("compras")).
				collect(Collectors.toList());
		em.add(links);
		return em;
	}
	
}
