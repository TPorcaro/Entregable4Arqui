package tporcaro.despensa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tporcaro.despensa.DTO.ClienteConCompras;
import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.repositories.ClienteRepository;

/*
 * The Class ClienteService.
 */
@Service
public class ClienteService {

	/** The clienteRepo. */
	@Autowired
	private ClienteRepository clientesRepo;

	/**
	 * Gets all Cliente.
	 *
	 * @param page the page
	 * @param size the size
	 * @return page of Cliente
	 */
	public Page<Cliente> getAll(int page, int size) {
		return this.clientesRepo.findAll(PageRequest.of(page, size));
	}

	/**
	 * Gets by id.
	 *
	 * @param id the id
	 * @return Cliente by id
	 */
	public Optional<Cliente> getById(int id) {
		return this.clientesRepo.findById(id);
	}

	/**
	 * Adds a Cliente.
	 *
	 * @param c Cliente
	 * @return true, if successful
	 */
	public boolean addCliente(Cliente c) {
		Cliente cliente = this.clientesRepo.save(c);
		if(cliente != null) {
			return true;
		}
		return false;
	}

	/**
	 * Update Cliente.
	 *
	 * @param c Cliente
	 * @return true, if successful
	 */
	public boolean updateCliente(Cliente c) {
		Cliente cliente = this.clientesRepo.getById(c.getId());
		cliente.setNombre(c.getNombre());
		cliente.setApellido(c.getApellido());
		Cliente persistedCliente = this.clientesRepo.save(cliente);
		if(persistedCliente != null) {
			return true;
		}
		return false;
	}

	/**
	 * Generar reporte cliente.
	 *
	 * @return list of ClienteConCompra
	 */
	public List<ClienteConCompras> generarReporteCliente() {
		List<ClienteConCompras> list = this.clientesRepo.generarReporteCliente();
		return list;
	}

	/**
	 * Gets Cliente by name.
	 *
	 * @param nombre of Cliente
	 * @return Cliente
	 */
	public Cliente getByName(String nombre) {
		return this.clientesRepo.getByName(nombre);
	}
	
	/**
	 * Removes Cliente.
	 *
	 * @param c Cliente
	 * @return true, if successful
	 */
	public boolean removeCliente(Cliente c) {
		 this.clientesRepo.delete(c);
		 return !this.clientesRepo.existsById(c.getId());
	}
	
}
