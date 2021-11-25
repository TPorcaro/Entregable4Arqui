let CLIENTES = [];
let COMPRAS = [];
let PRODUCTOS = [];
let CLIENTESTOTALES=[];
let limitePaginadoCliente=false
let limitePaginadoProducto=false

const baseUrl = "http://localhost:8080";
document.addEventListener("DOMContentLoaded", async () => {
  
  const getClientesTotales = async (page, size,name="") => {
    try {
      // console.log(name)
      limitePaginadoCliente=false
      let response = await fetch(`${baseUrl}/clientes?page=${page}&size=${size}`);
      // console.log("response", response);
      let json = await response.json();
      CLIENTESTOTALES = json.elements;
      cargarClientes();
      cargarOpciones("selectCliente", CLIENTESTOTALES);
      // console.log(CLIENTES)
    } catch (error) {
      console.log(error);
      limitePaginadoCliente=true;
    }
  };
  getClientesTotales(0, 1000,"a");


  const getClientes = async (page, size,name="") => {
    try {
      // console.log(name)
      limitePaginadoCliente=false
      let response = await fetch(`${baseUrl}/clientes?page=${page}&size=${size}`);
      // console.log("response", response);
      let json = await response.json();
      CLIENTES = json.elements;
      cargarClientes();
      cargarOpciones("selectCliente", CLIENTES);
      // console.log(CLIENTES)
    } catch (error) {
      console.log(error);
      limitePaginadoCliente=true;
    }
  };

  const getProductos = async (page, size) => {
    try {
      limitePaginadoProducto=false
      let response = await fetch(`${baseUrl}/productos?page=${page}&size=${size}`);
      let responseJson = await response.json();
      PRODUCTOS = responseJson.elements;
      cargarProductos();
      cargarAcordion();
    } catch (error) {
      console.log(error);
      limitePaginadoProducto=true
    }
  };

  let cargarAcordion = () => {
    let lugar = document.querySelector(".acordionProductos");
    lugar.innerHTML=""
    PRODUCTOS.forEach((p) => {
      // console.log(PRODUCTOS)
      // let input = document.createElement("input");
      // let inputNumber = document.createElement("input");
      // let label = document.createElement("label");
      // let div = document.createElement("div");
      // input.type = "checkbox";
      // inputNumber.type = "number";
      // inputNumber.placeholder = "cantidad";
      // inputNumber.id = p.id;
      // input.value = p.id;
      // label.innerHTML += `${p.nombre}   $ ${p.precio}   Stock:${p.stock}`;
      // div.appendChild(input);
      // div.appendChild(label);
      // div.appendChild(inputNumber);
      // lugar.appendChild(div);
      ////////////////////////////////////////////////////////////////
      let tr = document.createElement("tr");
      let td = document.createElement("td");
      let input = document.createElement("input");
      
      input.type = "checkbox";
      input.value = p.id;
      td.appendChild(input);
      tr.appendChild(td);
      for (const key in p) {
        if(key!=="id"){
          let td = document.createElement("td");
          td.innerText = p[key];
          tr.appendChild(td);
        }
      }
      let inputNumber = document.createElement("input");
      inputNumber.type = "number";
      inputNumber.id = p.id;
      let td2 = document.createElement("td");
      td2.appendChild(inputNumber)
      tr.appendChild(td2);
      lugar.appendChild(tr)
    });
  };

  // let cargarDatos = document.querySelector("#btnCargarDatos");
  // cargarDatos.addEventListener("click", async () => {
  //   let page = document.querySelector("#paginaDatos").value;
  //   let size = document.querySelector("#cantidadDatos").value;
  //   getClientes(page, size);
  //   getProductos(page, size);
  // });
  


  getClientes(0, 10,"a");
  getProductos(0, 10);
 


  let btnAltaCLiente = document.getElementById("btnAltaCliente");
  // console.log(btnAltaCLiente);
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
      getClientesTotales(0, 1000,"a");//actualiza el select de compras
      reporteClienteConCompras();
      console.log(responseJson);
    } catch (error) {
      console.log(error);
   
    }
    

  });

  let btnAltaProducto = document.getElementById("btnAltaProducto");
  // console.log(btnAltaProducto);
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
    let cliente = CLIENTESTOTALES.find((cliente) => cliente.id === Number(idCliente));

    if(cliente) {
      productosSeleccionados.forEach((p) => {
        console.log(p)
        let producto = PRODUCTOS.find((producto) =>  producto.id == p.value);
        arrayValues.push(producto);
      });
      let arrayPedidos = [];
      arrayValues.forEach(async (v) => {
        console.log(v)
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
        
      });
    }
  });

  let parsearCliente = (cliente) => {
    return {
      id: `${cliente.id}`,
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
    console.log(clientesConComprasJson)
  
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

  //////////////////////////////////////////////
  const tabs= document.querySelectorAll('[data-tab-target]')
  const tabContents=document.querySelectorAll('[data-tab-content]');
//  console.log(tabContents[0])
  tabs.forEach(tab=>{
    tab.addEventListener('click',()=>{ 
      tabContents.forEach(tabContent=>{
        
        tabContent.classList.remove('visible')
      })
      const target=document.querySelector(tab.dataset.tabTarget)
      target.classList.add('visible');
    })
  })

  let next=document.getElementById("next")
  let previous=document.getElementById("previous")
  let paginaCliente=0;
  
  next.addEventListener('click',(e)=>{
    e.preventDefault();
    
    if(limitePaginadoCliente!=true){
      paginaCliente++
      paginarClientes(paginaCliente)
       if(limitePaginadoCliente){
        
        paginaCliente-1
        console.log("aaaa")
      }
      console.log(paginaCliente)
      }
  })
  previous.addEventListener('click',(e)=>{
    e.preventDefault();
    limitePaginadoCliente=false
    if(paginaCliente>0){
      paginaCliente--
      paginarClientes(paginaCliente)
     
      console.log(paginaCliente)
    }
  })
  ////////////////////////////////////////////
  let nextP=document.getElementById("nextP")
  let previousP=document.getElementById("previousP")
  let paginaProducto=0;
  
  nextP.addEventListener('click',(e)=>{
    e.preventDefault();
    
    if(limitePaginadoProducto!=true){
      paginaProducto++
      paginarProducto(paginaProducto)
       if(limitePaginadoProducto){
        
        paginaProducto-1
        console.log("aaaa")
      }
      console.log(paginaProducto)
      }
  })
  previousP.addEventListener('click',(e)=>{
    e.preventDefault();
    limitePaginadoProducto=false
    if(paginaProducto>0){
      paginaProducto--
      paginarProducto(paginaProducto)
     
      console.log(paginaProducto)
    }
  })
  ////////////////////////////////////////////////////////////////////////////////////////////////
  let nextPcompras=document.getElementById("nextPcompras")
  let previousPcompras=document.getElementById("previousPcompras")
  let paginaProductoCompras=0;

  nextPcompras.addEventListener('click',(e)=>{
    console.log(paginaProductoCompras)
    e.preventDefault();

    if(limitePaginadoProducto!=true){
      paginaProductoCompras++
      paginarProducto(paginaProductoCompras)
       if(limitePaginadoProducto){
        
        paginaProductoCompras-1
        console.log("aaaa")
      }
      console.log(paginaProductoCompras)
      }
    
  })
  previousPcompras.addEventListener('click',(e)=>{
    e.preventDefault();
    limitePaginadoProducto=false
    if(paginaProductoCompras>0){
      paginaProductoCompras--
      paginarProducto(paginaProductoCompras)
     
      console.log(paginaProducto)
    }
  })

  function paginarClientes(pag){
    if(getClientes(pag, 10)){
      return true
    }
    return false

  }
  function paginarProducto(pag){
    if(getProductos(pag, 10)){
      return true
    }
    return false
    ;
  }
  
  
});
