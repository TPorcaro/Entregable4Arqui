package tporcaro.despensa.entities;


import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class Cliente.
 */
@Entity
public class Cliente {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "ID del cliente",name = "id")
	private int id;
	
	/** The nombre. */
	@ApiModelProperty(notes = "Nombre del cliente",name = "nombre")
	private String nombre;
	
	/** The apellido. */
	@ApiModelProperty(notes = "Apellido del cliente",name = "apellido")
	private String apellido;

	/**
	 * Instantiates a new cliente.
	 */
	public Cliente() {
		super();
	}

	/**
	 * Instantiates a new cliente.
	 *
	 * @param nombre the nombre
	 * @param apellido the apellido
	 */
	public Cliente(String nombre, String apellido) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the apellido.
	 *
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Sets the apellido.
	 *
	 * @param apellido the new apellido
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
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
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido +  "]";
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
		Cliente other = (Cliente) obj;
		return Objects.equals(apellido, other.apellido) && id == other.id && Objects.equals(nombre, other.nombre);
	}
	

}
