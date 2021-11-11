package tporcaro.despensa.DTO;

import tporcaro.despensa.entities.Cliente;

public class ClienteConCompras {

	Cliente cliente;
	double montoTotalCompras;
	
	public ClienteConCompras() {super();}

	public ClienteConCompras(Cliente c, double total) {
		this.cliente = c;
		this.montoTotalCompras = total;
	}
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getMontoTotalCompras() {
		return montoTotalCompras;
	}

	public void setMontoTotalCompras(double montoTotalCompras) {
		this.montoTotalCompras = montoTotalCompras;
	}

	@Override
	public String toString() {
		return "ClienteConCompras [cliente=" + cliente + ", montoTotalCompras=" + montoTotalCompras + "]";
	}
	
	
	
}
