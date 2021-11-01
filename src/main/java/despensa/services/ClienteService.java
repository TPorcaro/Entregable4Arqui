package despensa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import despensa.entities.Cliente;
import despensa.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientesRepo;

	public Page<Cliente> getAll(int page, int size) {
		return this.clientesRepo.findAll(PageRequest.of(page, size));
	}

	public Optional<Cliente> getById(int id) {
		return this.clientesRepo.findById(id);
	}

	public boolean addCliente(Cliente c) {
		Cliente cliente = this.clientesRepo.save(c);
		if(cliente != null) {
			return true;
		}
		return false;
	}

	public boolean updateCliente(Cliente c) {
		Cliente cliente = this.clientesRepo.getById(c.getId());
		cliente.setNombre(c.getNombre());
		cliente.setApellido(c.getApellido());
		cliente.setCompras(c.getCompras());
		Cliente persistedCliente = this.clientesRepo.save(cliente);
		if(persistedCliente != null) {
			return true;
		}
		return false;
	}
	
}
