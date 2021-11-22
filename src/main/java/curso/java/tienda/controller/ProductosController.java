package curso.java.tienda.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import curso.java.tienda.entity.DetallesPedido;
import curso.java.tienda.entity.Pedidos;
import curso.java.tienda.entity.Productos;
import curso.java.tienda.service.CategoriasService;
import curso.java.tienda.service.DetallesPedidoService;
import curso.java.tienda.service.MetodosPagoService;
import curso.java.tienda.service.PedidosService;
import curso.java.tienda.service.ProductosService;
import curso.java.tienda.utilidades.Utilidades;

@Controller
@RequestMapping("/productos")
public class ProductosController {

	//private ArrayList<Productos> lista = getLista();
	@Autowired
	private ProductosService ps;
	@Autowired
	private PedidosService peds;
	@Autowired
	private DetallesPedidoService dpeds;
	@Autowired
	private Utilidades ut;
	@Autowired
	private CategoriasService cs;
	@Autowired
	private MetodosPagoService ms;
	private static final Logger logger = LogManager.getLogger(ProductosController.class);
	
	@RequestMapping("")
	public String vistaProductos(Model model, HttpSession sesion, @RequestParam(required = false) String categoria, @RequestParam(required = false) String orden) {
		
		model.addAttribute("categoria", (categoria==null) ? 0 : categoria);
		model.addAttribute("orden", (orden==null) ? 0 : orden);
		//model.addAttribute("productos", ps.listaProductos());
		model.addAttribute("productos", ps.listaProductosFiltrada(categoria, orden));
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("categorias",cs.mapaCategorias());
		return "productos/listadoproductos";
	}
	
	@GetMapping("/form")
	public String vistaFormulario(Model model, HttpSession sesion) {		
		
		model.addAttribute("producto",new Productos());
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("modo","crear");
		model.addAttribute("categorias",cs.mapaCategorias());
		model.addAttribute("titulo","Crear Nuevo Producto");
		return "productos/productoform";
	}
	
	@GetMapping("/modificar/{id}")
	public String vistaModificar(Model model, HttpSession sesion, @PathVariable("id") int id) {		
		Productos prod = ps.cargarProducto(id);
		if(prod==null) {
			return "redirect:/productos";
		}
		
		
		model.addAttribute("producto",prod);
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("modo","modificar");
		model.addAttribute("idProducto",id);
		model.addAttribute("categorias",cs.mapaCategorias());
		model.addAttribute("titulo","Modificar Producto");
		return "productos/productoform";
	}
	
	@PostMapping("/new")
	public String newProducto(Model model, HttpSession sesion, @ModelAttribute Productos producto, @RequestParam("file") MultipartFile file) {		
		try {
			if(!file.isEmpty()) {
				producto.setImagen(file.getOriginalFilename());				
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
			    URL appResourceURL = loader.getResource("static");
			    String dbConfigFileRoute = appResourceURL.getPath();
			    int separador = dbConfigFileRoute.lastIndexOf("/");
			    dbConfigFileRoute = dbConfigFileRoute.substring(1, separador);
			    String ruta = dbConfigFileRoute + "/static/img/" + file.getOriginalFilename();
			    //guardar en el fichero
			    Files.copy(file.getInputStream(), Paths.get(ruta));
			}
		} catch (IOException e) {
			logger.warn("Error al crear el fichero de imagen");
			System.out.println(e);
			//throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		} finally {
			SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:s dd-MM-yyyy");
		    Date date = new Date();
		    producto.setFechaAlta(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
			producto.setFechaBaja(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
			ps.guardarProducto(producto);
			logger.info("Creado un nuevo producto");
		}
		return "redirect:/productos/lista";
	}
	
	@PostMapping("/update/{id}")
	public String updateProducto(Model model, HttpSession sesion, @ModelAttribute Productos producto, @RequestParam("file") MultipartFile file, @PathVariable("id") int id) {		
		try {
			if(!file.isEmpty()) {
				producto.setImagen(file.getOriginalFilename());				
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
			    URL appResourceURL = loader.getResource("static");
			    String dbConfigFileRoute = appResourceURL.getPath();
			    int separador = dbConfigFileRoute.lastIndexOf("/");
			    dbConfigFileRoute = dbConfigFileRoute.substring(1, separador);
			    String ruta = dbConfigFileRoute + "/static/img/" + file.getOriginalFilename();
			    //guardar en el fichero
			    Files.copy(file.getInputStream(), Paths.get(ruta));
			}
		} catch (IOException e) {
			logger.warn("Error al modificar la imagen");
			System.out.println(e);
			//throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		} finally {
			ps.modificarProducto(producto, id);
			logger.info("Modificado un producto");
		}
		return "redirect:/productos/lista";
	}
	
	@GetMapping("/borrar/{id}")
	public String deleteUsuario(Model model, @PathVariable("id") int id) {
		ps.borrarProducto(id);
		logger.info("Se ha borrado un producto");
		return "redirect:/productos/lista";
	}
	
	@GetMapping("/lista")
	public String listaClientes(Model model, HttpSession sesion) {
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("listaProductos",ps.listaProductos());
		return "productos/lista";
	}
	
	@RequestMapping("/carrito")
	public String carrito(Model model, HttpSession sesion) {
		
		HashMap<Integer, Integer> carrito =(HashMap<Integer, Integer>) sesion.getAttribute("carrito");
		if(carrito==null)
		{
			//TODO:Mensaje carrito vacio
			//model.addAttribute("mensajeCompra","Ningun producto en la cesta.");
			model.addAttribute("opciones",ut.menu(sesion));
			model.addAttribute("configuracion",ut.mapaConf());
			model.addAttribute("metodosPago", ms.mapaPago());
			model.addAttribute("carritoProductos", null);
			//return "redirect:/productos";
			return "productos/carrito";
		}
		
		List<Productos> listaCarrito = ps.listaCarrito(carrito);
		//System.out.println(listaCarrito);
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("metodosPago", ms.mapaPago());
		model.addAttribute("carritoProductos", listaCarrito);
		model.addAttribute("total", ps.totalProductos(listaCarrito, carrito));
		return "productos/carrito";
	}
	
	@GetMapping("/setcarrito")
	public String llenarCarrito(HttpSession session, @RequestParam int id) {
		//Productos p = ps.cargarProducto(id);
		boolean existe = ps.existeProducto(id);
		//ArrayList<Productos> carrito = (ArrayList<Productos>) session.getAttribute("carrito");
		HashMap<Integer, Integer> carrito = (HashMap<Integer, Integer>) session.getAttribute("carrito");
		if(carrito==null)
		{
			//carrito=new ArrayList<Productos>();
			carrito = new HashMap<Integer, Integer>();
			session.setAttribute("carrito", carrito);
		}
		
		if(existe) {
			if(carrito.containsKey(id)) {
				//carrito.put(id, 1);
				carrito.replace(id, carrito.get(id)+1);
			} else {
				carrito.put(id, 1);
			}
		} else {
			//TODO:traza
		}
	
		//carrito.add(p);
		return "redirect:/productos";
	}
	
	@GetMapping("/comprar")
	public String comprar(Model model, HttpSession session, @RequestParam int idMetodo) {
		if(((Integer)session.getAttribute("usuario"))==null)
			return "redirect:/login/form";
		
		//ArrayList<Productos> carrito = (ArrayList<Productos>) session.getAttribute("carrito");
		HashMap<Integer, Integer> carrito = (HashMap<Integer, Integer>) session.getAttribute("carrito");
		if(carrito==null)
		{
			model.addAttribute("mensajeCompra","Ningun producto en la cesta.");
			return "redirect:/productos";
		}
		List<Productos> listaCarrito = ps.listaCarrito(carrito);
		//System.out.println(listaCarrito.toString());
		//System.out.println(carrito.toString());
		crearPedido(listaCarrito, session, carrito, ms.buscarMetodo(idMetodo));
		//carrito=null;
		session.setAttribute("carrito", null);
		//System.out.println("compra realizada");
		model.addAttribute("mensajeCompra","compra realizada");
		logger.info("Se ha realizado una compra");
		return "redirect:/pedidos/pedido";
	}
	
	@GetMapping("/vaciar")
	public String vaciarCarrito(HttpSession session) {
		session.setAttribute("carrito", null);
		return "redirect:/productos";
	}
	
	@RequestMapping("/producto/{id}")
	public String verProducto(Model model,@PathVariable("id") int id, HttpSession sesion) {
		
		Productos p = ps.cargarProducto(id);
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("producto", p);
		model.addAttribute("categoria", cs.buscarCategoriaNombre(p.getIdCategoria()));
		return "productos/producto";
	}
	
	private void crearPedido(List<Productos> lista, HttpSession session, HashMap<Integer, Integer> carrito, String metodo) {
		
		double totalLinea = 0;
		double totalPedido = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime fecha = LocalDateTime.now(); 
		Instant instant = fecha.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		DetallesPedido dped = null;
		Pedidos ped = new Pedidos((Integer)session.getAttribute("usuario"), date, metodo, "pendiente", "", 0.0);
		ped = peds.guardarPedido(ped);
		for (Productos productos : lista) {
			totalLinea= productos.getPrecio() * carrito.get(productos.getId());
			totalLinea+= totalLinea*(productos.getImpuesto()/100);
			totalLinea=Math.round(totalLinea*100)/100;
			dped = new DetallesPedido(ped.getId(), productos.getId(), productos.getPrecio().floatValue(), carrito.get(productos.getId()), productos.getImpuesto(), totalLinea);
			totalPedido+=totalLinea;
			dpeds.guardarDPedido(dped);
			productos.setStock(productos.getStock()-carrito.get(productos.getId()));
			ps.guardarProducto(productos);
			
		}
		
		ped.setTotal(totalPedido);
		peds.guardarPedido(ped);
	}
}
