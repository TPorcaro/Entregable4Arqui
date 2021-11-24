package tporcaro.despensa.DTO;

import io.swagger.annotations.ApiModelProperty;
import tporcaro.despensa.entities.Cliente;

/**
 * The Class ClienteConCompras.
 */
public class ClienteConCompras {

	/** The cliente. */
	@ApiModelProperty(notes = "Cliente",name = "cliente")
	Cliente cliente;
	
	/** The monto total compras. */
	@ApiModelProperty(notes = "Monto total de las compras del cliente",name = "montoTotalCompras")
	double montoTotalCompras;
	
	/**
	 * Instantiates a new cliente con compras.
	 */
	public ClienteConCompras() {super();}

	/**
	 * Instantiates a new cliente con compras.
	 *
	 * @param c the c
	 * @param total the total
	 */
	public ClienteConCompras(Cliente c, double total) {
		this.cliente = c;
		this.montoTotalCompras = total;
	}
	
	/**
	 * Gets the cliente.
	 *
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Sets the cliente.
	 *
	 * @param cliente the new cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * Gets the monto total compras.
	 *
	 * @return the monto total compras
	 */
	public double getMontoTotalCompras() {
		return montoTotalCompras;
	}

	/**
	 * Sets the monto total compras.
	 *
	 * @param montoTotalCompras the new monto total compras
	 */
	public void setMontoTotalCompras(double montoTotalCompras) {
		this.montoTotalCompras = montoTotalCompras;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ClienteConCompras [cliente=" + cliente + ", montoTotalCompras=" + montoTotalCompras + "]";
	}
	
	
	
}
