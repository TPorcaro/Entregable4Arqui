package despensa.filler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import despensa.entities.Autor;
import despensa.entities.Libro;
import despensa.entities.Usuario;
import despensa.repositories.LibroRepository;
import despensa.repositories.UsuarioRepository;
import despensa.utils.PasswordUtils;

@Configuration
public class DbFiller {

	@Bean
	public CommandLineRunner initDb(LibroRepository libros, UsuarioRepository usuario) {
		return args-> {
			IntStream.range(0, 100).forEach(i->{
				List<Autor> a = new ArrayList<>();
				a.add(new Autor("A "+i));
				Libro l = new Libro("L "+i, a);
				libros.save(l);
			});
			Usuario u = new Usuario("user", PasswordUtils.hashPassword("password"));
			usuario.save(u);
		};
	}
}
