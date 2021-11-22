package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.entity.Pedidos;
import curso.java.tienda.service.ConfiguracionService;
import curso.java.tienda.service.PedidosService;
import curso.java.tienda.utilidades.Utilidades;

@Controller
@RequestMapping("/pedidos")
public class PedidosController {

	@Autowired
	private PedidosService ps;
	@Autowired
	private Utilidades ut;
	@Autowired
	private ConfiguracionService confs;
	private static final Logger logger = LogManager.getLogger(PedidosController.class);
	
	@GetMapping("/lista/{id}")
	public String listarPedidos(Model model,@PathVariable("id") int id, HttpSession sesion) {
		
		//TODO: Comprobar que id session sea id parametro
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("pedidos", ps.listadoPedidos(id));
		return "pedidos/lista";
	}
	
	@GetMapping("/pedido")
	public String listarPedidosUsuario(Model model, HttpSession sesion) {
		
		//TODO: Comprobar que id session sea id parametro
		logger.info("Acceso al listado de pedidos");
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("pedidos", ps.listadoPedidos((int)sesion.getAttribute("usuario")));
		model.addAttribute("titulo","Listado de Pedidos");
		return "pedidos/lista";
	}
	
	@GetMapping("/cancelar")
	public String listadoEnCancelacion(Model model, HttpSession sesion) {
		logger.info("Accesso al listado de pedidos por cancelar");
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("pedidos", ps.listadoCancelados());
		return "pedidos/encancelacion";
	}
	
	@GetMapping("/cancelar/{id}")
	public String pedidoBorrar(Model model,@PathVariable("id") int id) {
		logger.info("Se ha cancelado un pedido");
		Pedidos p = ps.buscarPedido(id);
		p.setEstado("cancelado");
		ps.modificarPedido(p);
		
		return "redirect:/pedidos/cancelar";
	}
	
	@GetMapping("/solicitarcancelar/{id}")
	public String pedidoCancelacion(Model model,@PathVariable("id") int id) {
		logger.info("Solicitada una cancelacion");
		Pedidos p = ps.buscarPedido(id);
		p.setEstado("solicitada cancelacion");
		ps.modificarPedido(p);
		
		return "redirect:/pedidos/pedido";
	}
	
	@GetMapping("/procesar")
	public String listadoPendientes(Model model, HttpSession sesion) {
		logger.info("Acceso al listado de pedidos por procesar");
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("pedidos", ps.listadoPendientes());
		return "pedidos/pendientes";
	}
	
	@GetMapping("/procesar/{id}")
	public String procesarPendiente(@PathVariable("id") int id) {
		logger.info("Se ha procesado un pedido");
		Pedidos p = ps.buscarPedido(id);
		p.setEstado("enviado");
		p.setNumFactura(confs.getNumFactura());
		ps.modificarPedido(p);
		confs.setNumFactura();
		
		return "redirect:/pedidos/procesar";
	}
}
