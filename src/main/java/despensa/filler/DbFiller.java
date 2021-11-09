package despensa.filler;

import java.sql.Timestamp;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import despensa.entities.Cliente;
import despensa.entities.Compra;
import despensa.entities.Pedido;
import despensa.entities.Producto;
import despensa.repositories.ClienteRepository;
import despensa.repositories.CompraRepository;
import despensa.repositories.PedidoRepository;
import despensa.repositories.ProductoRepository;

@Configuration
public class DbFiller {

	@Bean
	public CommandLineRunner initDb(CompraRepository compra, ClienteRepository cliente, PedidoRepository pedido, ProductoRepository producto) {
		return args-> {
			IntStream.range(0, 100).forEach(i->{
				Producto p = new Producto("nombre"+i,(float) Math.random()*100,(int)Math.random()*50);
				Pedido pd = new Pedido(p,(int)Math.random()*10);
				producto.save(p);
				pedido.save(pd);
			});
			IntStream.range(0, 100).forEach(i->{
				int page = (int) Math.random()*i;
				Page<Pedido> pedidosPage = pedido.findAll(PageRequest.of(page, 10));
				Cliente c = new Cliente("nombre"+i,"apellido"+i);
				cliente.save(c);
				long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
				long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
				long diff = end - offset + 1;
				Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
				Compra cmpra = new Compra(c,rand,pedidosPage.getContent());
				compra.save(cmpra);
			});
		};
	}
}
