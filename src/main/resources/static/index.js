let CLIENTES = [];
let COMPRAS = [];
let PRODUCTOS = [];
const baseUrl = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", async () => {
  
  const getClientes = async (page, size) => {
    try {
      let response = await fetch(`${baseUrl}/clientes?page=${page}&size=${size}`);
      console.log("response", response);
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
  console.log(btnAltaCLiente);
  btnAltaCLiente.addEventListener("click", async (e) => {
    e.preventDefault();
    console.log(e)
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
      console.log(responseJson);
    } catch (error) {
      console.log(error);
    }
  });

  let btnAltaProducto = document.getElementById("btnAltaProducto");
  console.log(btnAltaProducto);
  btnAltaProducto.addEventListener("click", async (e) => {
    let inputs = document.querySelectorAll(".inputToProducto");
    e.preventDefault();
    let producto = {

    };
    for (const elem of inputs) {
      if (elem.id || elem.id !== "") producto[elem.id] = elem.value;
    }
    console.log(producto);

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
      console.log(responseJson);
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
          console.log(responseJson);
          // Manejar respuesta pedido
          arrayPedidos.push(responseJson.data)
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
        // que se maneje
      });
    }
  });

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
    let clientesConCompras = await fetch(`${baseUrl}/clientes/reporte`);
    let clientesConComprasJson = await clientesConCompras.json();
    /* let clientesConComprasJson = [
      {
        cliente: {
          nombre: "Juan",
          apellido: "Perez",
        },
        montoTotal: "100",
      },
      {
        cliente: {
          nombre: "Juan",
          apellido: "Carlos",
        },
        montoTotal: "345",
      },
    ] */
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
          td.innerText = cliente[key];
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
    /* let ventasPorDiaJson = [
      {
        fecha: "2020-05-01",
        montoTotal: "100",
      },
      {
        fecha: "2020-05-02",
        montoTotal: "345",
      },
    ] */
    ventasPorDiaJson.forEach((venta) => {
      let tr = document.createElement("tr");
      for (const key in venta) {
        let td = document.createElement("td");
        td.innerText = venta[key];
        tr.appendChild(td);
      }
      tabla.appendChild(tr);
    });
  }
  reporteVentasPorDia();

  let obtenerProductoMasVendido = async () => {
    let div = document.getElementById("productoMasVendido");
    let productoMasVendido = await fetch(`${baseUrl}/productos/mas-vendido`);
    let productoMasVendidoJson = await productoMasVendido.json();
    /* let productoMasVendidoJson = {
      producto: {
        nombre: "Leche",
        precio: "100",
      },
      cantidad: "10",
    }; */
    for (const key in productoMasVendidoJson) {
      let span = document.createElement("span");
      span.classList.add("col");
      if (typeof productoMasVendidoJson[key] === 'object') {
        for (const key2 in productoMasVendidoJson[key]) {
          span.innerText = productoMasVendidoJson[key].nombre;            
          div.appendChild(span);
        }
      } else {
        span.innerText = productoMasVendidoJson[key];
        div.appendChild(span);
      }
    }
  }
  obtenerProductoMasVendido()
});
