package tporcaro.despensa.filler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import tporcaro.despensa.entities.Cliente;
import tporcaro.despensa.entities.Compra;
import tporcaro.despensa.entities.Pedido;
import tporcaro.despensa.entities.Producto;
import tporcaro.despensa.services.ClienteService;
import tporcaro.despensa.services.CompraService;
import tporcaro.despensa.services.PedidoService;
import tporcaro.despensa.services.ProductoService;

@Configuration
public class DbFiller {

	private List<Producto> productos;
	private List<Cliente> clientes;
	private List<Pedido> pedidos;
	@Bean
	public CommandLineRunner initDb(CompraService compra, ClienteService cliente, PedidoService pedido, ProductoService producto) {
		return args-> {
			productos = new ArrayList<>();
			clientes = new ArrayList<>();
			pedidos = new ArrayList<>();
			IntStream.range(0, 50).forEach(i->{
				Producto p = new Producto("nombre"+i,(float) Math.random()*100,(int) Math.floor(Math.random()*50));
				Pedido pd = new Pedido(p,(int) Math.floor(Math.random()*100 +1));
				Cliente c = new Cliente("nombre"+i,"apellido"+i);
				producto.addProducto(p);
				pedido.addPedido(pd);
				cliente.addCliente(c);
				productos.add(p);
				pedidos.add(pd);
				clientes.add(c);
			});
			IntStream.range(0, 100).forEach(i->{
				int page = (int) Math.floor(Math.random()*i);
				int randomNumber = 1 + (int)(Math.random() * ((15 - 1) + 1));
				Page<Pedido> pedidosPage = pedido.getAll(page, randomNumber);
				Cliente c = new Cliente("nombre"+i,"apellido"+i);
				cliente.addCliente(c);
				Long max =0L;
		        Long min =100000000000L;
	            Random r = new Random();
	            Long randomLong=(r.nextLong() % (max - min)) + min;
	            Date dt =new Date(randomLong);
				Compra cmpra = new Compra(c,dt,pedidosPage.getContent());
				compra.addCompra(cmpra);
			});
		};
	}
}
