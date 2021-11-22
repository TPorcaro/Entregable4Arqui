package tporcaro.despensa.controller;



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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import tporcaro.despensa.entities.Producto;
import tporcaro.despensa.paging.Paginado;
import tporcaro.despensa.services.ProductoService;



@RestController
@RequestMapping("/productos")
@Api(value = "ProductoController", description = "Controlador REST del producto")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	private static Logger LOG = LoggerFactory.getLogger(ProductoController.class);
	
	@ApiOperation(value = "Obtiene un paginado de productos", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("")
	public ResponseEntity<Paginado<Producto>> getAll(
			@RequestParam(name="page", defaultValue="0")int page,
			@RequestParam(name="size", defaultValue="10")int size){
		try {
			Page<Producto> productos = this.productoService.getAll(page,size);
			if(!productos.isEmpty()) {
				Paginado<Producto> paginadoCompras = new Paginado<Producto>(productos.getContent(), page, productos.getSize(), size);
				return new ResponseEntity<Paginado<Producto>>(paginadoCompras ,HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value = "Obtiene un producto por su id", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("/{id}")
	public ResponseEntity<Producto> getProducto(@PathVariable("id") int id){
		try {
			Optional<Producto> producto = this.productoService.getById(id);
			if(producto.isPresent()) {
				return new ResponseEntity<Producto>(producto.get(),HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value = "Crea un producto", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@PostMapping("")
	public ResponseEntity<Producto> addProducto(@RequestBody Producto c){
		try {
			boolean ok = this.productoService.addProducto(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value = "Actualiza un producto", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 406, message = "Not Acceptable"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@PutMapping("")
	public ResponseEntity<Producto> updateProducto(@RequestBody Producto c){
		try {
			boolean ok = this.productoService.updateProducto(c);
			if(!ok) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value = "Obtiene el producto mas vendido", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 200, message = "Internal Server Error")			
	})
	@GetMapping("/mas-vendido")
	public ResponseEntity<Producto> getProductoMasVendido(){
		try {
			Producto p = this.productoService.getProductoMasVendido();
			if(p == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(p, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
