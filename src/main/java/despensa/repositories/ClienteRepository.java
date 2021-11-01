package despensa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import despensa.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
