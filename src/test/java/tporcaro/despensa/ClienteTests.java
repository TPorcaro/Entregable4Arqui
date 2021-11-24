package tporcaro.despensa;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import tporcaro.despensa.DTO.ClienteConCompras;
import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.entities.Compra;
import tporcaro.despensa.entities.Pedido;
import tporcaro.despensa.entities.Producto;
import tporcaro.despensa.services.ClienteService;
import tporcaro.despensa.services.CompraService;
import tporcaro.despensa.services.PedidoService;
import tporcaro.despensa.services.ProductoService;

/**
 * The Class ClienteTests.
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ClienteTests {

	/** The producto service. */
	@Autowired
	private ProductoService productoService;
	
	/** The compra service. */
	@Autowired
	private CompraService compraService;
	
	/** The pedido service. */
	@Autowired
	private PedidoService pedidoService;
	
	/** The cliente service. */
	@Autowired
	private ClienteService clienteService;
	
	/**
	 * Test the Cliente by ID.
	 */
	@Test
	@Order(1)
	void getClienteByID() {
		Assertions.assertEquals(clienteService.getById(3).get().getId(), 3);
	}
	
	/**
	 * Test Update Cliente.
	 */
	@Test
	@Order(2)
	void updateCliente() {
		Cliente cliente = new Cliente("test nombre","test apellido");
		cliente.setId(3);
		Assertions.assertEquals(clienteService.updateCliente(cliente), true);
		Assertions.assertEquals(clienteService.getById(3).get(), cliente);
	}
	
	/**
	 * Test Get all Cliente.
	 */
	@Test
	@Order(3)
	void getAll() {
		Page<Cliente> page = clienteService.getAll(0, 10);
		List<Cliente> clientes = page.getContent();
		for (Cliente cliente : clientes) {
			Assertions.assertEquals(cliente.getClass(), Cliente.class);
		}
	}
	
	/**
	 * Test Add Cliente.
	 */
	@Test
	@Order(4)
	void addCliente() {
		Cliente cliente = new Cliente("nombre cliente nuevo","apellido cliente nuevo");
		Assertions.assertEquals(clienteService.addCliente(cliente), true);
		Assertions.assertEquals(clienteService.getByName(cliente.getNombre()).getNombre(), cliente.getNombre());
	}
	
	/**
	 * Test Delete cliente.
	 */
	@Test
	@Order(5)
	void deleteCliente() {
		Cliente aBorrarCliente = clienteService.getById(30).get();
		Assertions.assertEquals(clienteService.removeCliente(aBorrarCliente), true);
	}
	
	/**
	 * Test Reporte cliente.
	 */
	@Test
	@Order(6)
	void reporteCliente() {
		compraService.vaciarCompra();
		pedidoService.vaciarPedido();
		Producto producto1 = productoService.getById(1).get();
		Producto producto2 = productoService.getById(4).get();
		Pedido pedido1 = new Pedido(producto1, 50);
		List<Pedido> listPedido1 = new ArrayList<Pedido>(); 
		listPedido1.add(pedido1);
		Pedido pedido2 = new Pedido(producto2, 5);
		List<Pedido> listPedido2 = new ArrayList<Pedido>(); 
		listPedido2.add(pedido2);
		pedidoService.addPedido(pedido1);
		pedidoService.addPedido(pedido2);
		Cliente cliente1 = clienteService.getById(3).get();
		Cliente cliente2 = clienteService.getById(6).get();
		Long max =0L;
        Long min =100000000000L;
        Random r = new Random();
        Long randomLong=(r.nextLong() % (max - min)) + min;
        Date dt =new Date(randomLong);
		Compra compra1 = new Compra(cliente1,dt, listPedido1);
		Compra compra2 = new Compra(cliente2,dt, listPedido2);
		compraService.addCompra(compra1);
		compraService.addCompra(compra2);
		List<ClienteConCompras> reporteClienteConCompras = clienteService.generarReporteCliente();
		Assertions.assertEquals(reporteClienteConCompras.get(0).getCliente(), compra1.getCliente());
		Assertions.assertEquals(reporteClienteConCompras.get(0).getMontoTotalCompras(), compra1.getPrecio_total());
		Assertions.assertEquals(reporteClienteConCompras.get(1).getCliente(), compra2.getCliente());
		Assertions.assertEquals(reporteClienteConCompras.get(1).getMontoTotalCompras(), compra2.getPrecio_total());
	}
}
