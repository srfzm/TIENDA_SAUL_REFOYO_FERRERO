package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;

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
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("pedidos", ps.listadoPedidos((int)sesion.getAttribute("usuario")));
		return "pedidos/lista";
	}
	
	@GetMapping("/cancelar")
	public String listadoEnCancelacion(Model model, HttpSession sesion) {
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("pedidos", ps.listadoCancelados());
		return "pedidos/encancelacion";
	}
	
	@GetMapping("/cancelar/{id}")
	public String pedidoBorrar(Model model,@PathVariable("id") int id) {
		
		Pedidos p = ps.buscarPedido(id);
		p.setEstado("cancelado");
		ps.modificarPedido(p);
		
		return "redirect:/pedidos/cancelar";
	}
	
	@GetMapping("/solicitarcancelar/{id}")
	public String pedidoCancelacion(Model model,@PathVariable("id") int id) {
		
		Pedidos p = ps.buscarPedido(id);
		p.setEstado("solicitada cancelacion");
		ps.modificarPedido(p);
		
		return "redirect:/pedidos/pedido";
	}
	
	@GetMapping("/procesar")
	public String listadoPendientes(Model model, HttpSession sesion) {
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("pedidos", ps.listadoPendientes());
		return "pedidos/pendientes";
	}
	
	@GetMapping("/procesar/{id}")
	public String procesarPendiente(@PathVariable("id") int id) {
		
		Pedidos p = ps.buscarPedido(id);
		p.setEstado("enviado");
		p.setNumFactura(confs.getNumFactura());
		ps.modificarPedido(p);
		confs.setNumFactura();
		
		return "redirect:/pedidos/procesar";
	}
}
