package tporcaro.despensa.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import io.swagger.annotations.ApiModelProperty;

/**
 * The Class Pedido.
 */
@Entity
public class Pedido {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Id del pedido",name = "id")
	private int id;
	
	/** The producto. */
	@ManyToOne
	@ApiModelProperty(notes = "Producto que tiene el pedido",name = "producto")
	private Producto producto;
	
	/** The cantidad. */
	@ApiModelProperty(notes = "Cantidad que tiene el pedido",name = "cantidad")
	private int cantidad;

	/**
	 * Instantiates a new pedido.
	 */
	public Pedido() {
		super();
	}

	/**
	 * Instantiates a new pedido.
	 *
	 * @param producto the producto
	 * @param cantidad the cantidad
	 */
	public Pedido(Producto producto, int cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}

	/**
	 * Gets the producto.
	 *
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * Sets the producto.
	 *
	 * @param producto the new producto
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * Gets the cantidad.
	 *
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Sets the cantidad.
	 *
	 * @param cantidad the new cantidad
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", producto=" + producto + ", cantidad=" + cantidad + "]";
	}

	/**
	 * Sets the id.
	 *
	 * @param i the new id
	 */
	public void setId(int i) {
		this.id = i;	
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return cantidad == other.cantidad && id == other.id && Objects.equals(producto, other.producto);
	}
	

}
