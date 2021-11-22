let CLIENTES = [];
let COMPRAS = [];
let PRODUCTOS = [];
const baseUrl = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", async () => {
  
  const getClientes = async (page, size) => {
    try {
      let response = await fetch(`${baseUrl}/clientes?page=${page}&size=${size}`);
      let json = await response.json();
      CLIENTES = json.elements;
      cargarClientes();
      cargarOpciones("selectCliente", CLIENTES);
    } catch (error) {
      console.log(error);
    }
  };

  const getProductos = async (page, size) => {
    try {
      let response = await fetch(`${baseUrl}/productos?page=${page}&size=${size}`);
      let responseJson = await response.json();
      PRODUCTOS = responseJson.elements;
      cargarProductos();
      cargarAcordion();
    } catch (error) {
      console.log(error);
    }
  };

  let cargarAcordion = () => {
    let lugar = document.querySelector(".acordionProductos");
    PRODUCTOS.forEach((p) => {
      let input = document.createElement("input");
      let inputNumber = document.createElement("input");
      let label = document.createElement("label");
      let div = document.createElement("div");
      input.type = "checkbox";
      inputNumber.type = "number";
      inputNumber.placeholder = "cantidad";
      inputNumber.id = p.id;
      input.value = p.id;
      label.innerHTML += `${p.nombre}   $ ${p.precio}   Stock:${p.stock}`;
      div.classList.add("m-2", "d-flex", "justify-content-between", "align-items-center");
      div.appendChild(input);
      div.appendChild(label);
      div.appendChild(inputNumber);
      lugar.appendChild(div);
    });
  };

  let cargarDatos = document.querySelector("#btnCargarDatos");
  cargarDatos.addEventListener("click", async () => {
    let page = document.querySelector("#paginaDatos").value;
    let size = document.querySelector("#cantidadDatos").value;
    getClientes(page, size);
    getProductos(page, size);
  });

  let btnAltaCLiente = document.getElementById("btnAltaCliente");
  btnAltaCLiente.addEventListener("click", async (e) => {
    e.preventDefault();
    let name = document.querySelector("#nombreCliente").value
    let apellido = document.querySelector("#apellidoCliente").value
    let cliente = {
      nombre: name,
      apellido: apellido
    };
    try {
      let response = await fetch(`${baseUrl}/clientes`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-type": "application/json",
        },
        body: JSON.stringify(cliente),
      });
      let responseJson = await response.json();
    } catch (error) {
      console.log(error);
    }
  });

  let btnAltaProducto = document.getElementById("btnAltaProducto");
  btnAltaProducto.addEventListener("click", async (e) => {
    let inputs = document.querySelectorAll(".inputToProducto");
    e.preventDefault();
    let producto = {

    };
    for (const elem of inputs) {
      if (elem.id || elem.id !== "") producto[elem.id] = elem.value;
    }

    try {
      let response = await fetch(`${baseUrl}/productos`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-type": "application/json",
        },
        body: JSON.stringify(producto),
      });
      let responseJson = await response.json();
    } catch (error) {
      console.log(error);
    }
  });

  let cargarOpciones = (idSelect, arrayOpciones) => {
    let select = document.getElementById(idSelect);
    arrayOpciones.forEach((opcion) => {
      let option = document.createElement("option");
      option.value = opcion.id;
      option.innerText = opcion.nombre;
      select.appendChild(option);
    });
  };

  let realizarCompra = document.getElementById("realizarCompra");
  realizarCompra.addEventListener("click", async (e) => {
    e.preventDefault();
    let productosSeleccionados = document.querySelectorAll("input:checked");
    let arrayValues = [];
    let idCliente = document.getElementById("selectCliente").value;
    let cliente = CLIENTES.find((cliente) => cliente.id === Number(idCliente));

    if(cliente) {
      productosSeleccionados.forEach((p) => {
        let producto = PRODUCTOS.find((producto) =>  producto.id == p.value);
        arrayValues.push(producto);
      });
      let arrayPedidos = [];
      arrayValues.forEach(async (v) => {
        let pedido = {
          producto: v,
          cantidad: document.getElementById(`${v.id}`).value,
        };
        try {
          let response = await fetch(`${baseUrl}/pedidos`, {
            method: "POST",
            headers: {
              Accept: "application/json",
              "Content-type": "application/json",
            },
            body: JSON.stringify(pedido),
          });
          let responseJson = await response.json();
          // Manejar respuesta pedido
          arrayPedidos.push(responseJson);
        } catch (error) {
          console.log(error);
        }
      });

      let compra = {
        cliente: cliente,
        fecha_compra: Date.now(),
        pedidos: arrayPedidos,
      };

      fetch(`${baseUrl}/compras`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(compra),
      }).then((res) => {
        if(res.ok) {
          alert("Compra realizada");
          reimprimirReportes();
        } else {
          alert("Error al realizar la compra");
        }
      });
    }
  });

  let reimprimirReportes = () => {
    reporteClienteConCompras();
    reporteVentasPorDia();
    obtenerProductoMasVendido();
  };

  let parsearCliente = (cliente) => {
    return {
      nombre: `${cliente.nombre}`,
      apellido: `${cliente.apellido}`,
    };
  };

  let cargarClientes = async () => {
    let tabla = document.getElementById("listaClientes");
    tabla.innerHTML = "";
    for (let cliente of CLIENTES) {
      cliente = parsearCliente(cliente);
      let tr = document.createElement("tr");
      for (const key in cliente) {
        let td = document.createElement("td");
        td.innerText = cliente[key];
        tr.appendChild(td);
      }
      tabla.appendChild(tr);
    }
  };

  let cargarProductos = async () => {
    let tabla = document.getElementById("listaProductos");
    tabla.innerHTML = "";
    for (let producto of PRODUCTOS) {
      let tr = document.createElement("tr");
      for (const key in producto) {
        let td = document.createElement("td");
        td.innerText = producto[key];
        tr.appendChild(td);
      }
      tabla.appendChild(tr);
    }
  };

  let reporteClienteConCompras = async () => {
    let tabla = document.getElementById("listaReporteCliente");
    tabla.innerHTML = "";
    let clientesConCompras = await fetch(`${baseUrl}/clientes/reporte`, {mode: 'cors'});
    let clientesConComprasJson = await clientesConCompras.json();
    clientesConComprasJson.forEach((cliente) => {
      let tr = document.createElement("tr");
      for (const key in cliente) {
        let td = document.createElement("td");
        if (typeof cliente[key] === 'object') {
          for (const key2 in cliente[key]) {
            let td2 = document.createElement("td");
            td2.innerText = cliente[key][key2];            
            tr.appendChild(td2);
          }
        } else {
          if(typeof cliente[key] === 'number') {
            td.innerText = cliente[key].toFixed(2);
          } else {
            td.innerText = cliente[key];
          }
          tr.appendChild(td);
        }
      }
      tabla.appendChild(tr);
    });
  };
  reporteClienteConCompras();

  let reporteVentasPorDia = async () => {
    let tabla = document.getElementById("listaReporteVentas");
    tabla.innerHTML = "";
    let ventasPorDia = await fetch(`${baseUrl}/compras/reporte`);
    let ventasPorDiaJson = await ventasPorDia.json();
    ventasPorDiaJson.forEach((venta) => {
      let tr = document.createElement("tr");
      for (const key in venta) {
        let td = document.createElement("td");
        if(typeof venta[key] === 'number') {
          td.innerText = venta[key].toFixed(2);
        } else {
          td.innerText = venta[key];
        }
        tr.appendChild(td);
      }
      tabla.appendChild(tr);
    });
  }
  reporteVentasPorDia();

  let obtenerProductoMasVendido = async () => {
    let div = document.getElementById("productoMasVendido");
    div.innerHTML = "";
    let productoMasVendido = await fetch(`${baseUrl}/productos/mas-vendido`);
    let productoMasVendidoJson = await productoMasVendido.json();
    for (const key in productoMasVendidoJson) {
      if(key!=='id') {
        let span = document.createElement("span");
        span.classList.add("col");
        span.innerText = `${key.toUpperCase()}: ${typeof productoMasVendidoJson[key] === 'number' ? productoMasVendidoJson[key].toFixed(2) : productoMasVendidoJson[key]}`;
        div.appendChild(span);
      }
    }
  }
  obtenerProductoMasVendido()
});
