package curso.java.tienda.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import curso.java.tienda.entity.DetallesPedido;
import curso.java.tienda.entity.Pedidos;
import curso.java.tienda.service.DetallesPedidoService;
import curso.java.tienda.service.PedidosService;
import curso.java.tienda.service.ProductosService;
import curso.java.tienda.utilidades.Utilidades;

@Controller
@RequestMapping("/detallespedidos")
public class DetallesPedidosController {

	@Autowired
	private DetallesPedidoService dps;
	@Autowired
	private PedidosService ps;
	@Autowired
	private ProductosService prods;
	@Autowired
	private Utilidades ut;
	private static final Logger logger = LogManager.getLogger(DetallesPedidosController.class);
	
	@GetMapping("/{idPedido}")
	public String verPedidoDetallado(Model model, @PathVariable("idPedido") int idPedido, HttpSession sesion) {
		
		logger.info("Accediendo a los detalles del pedido.");
		
		List<DetallesPedido> lineas = dps.buscarLineasPedido(idPedido);
		HashMap<Integer, String> listaIds = new HashMap<Integer, String>();
		int total = 0;
		
		for (DetallesPedido detallesPedido : lineas) {
			total+=detallesPedido.getTotal();
			listaIds.put(detallesPedido.getIdProducto(), null);
		}
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("pedido", idPedido);
		model.addAttribute("productos", prods.mapaProductos(listaIds));
		model.addAttribute("estado", ps.buscarPedido(idPedido).getEstado());
		model.addAttribute("lineasPedido", lineas);
		model.addAttribute("total", total);
		return "detallesPedido/pedido";
	}
	
	@GetMapping("/{idPedido}/{idLinea}")
	public String eliminarLinea(Model model, @PathVariable("idPedido") int idPedido, @PathVariable("idLinea") int idLinea, HttpSession sesion) {
	
		logger.info("Eliminada linea del pedido.");
		
		Pedidos pedido = ps.buscarPedido(idPedido);
		if(!pedido.getEstado().equals("pendiente")) {
			return "redirect:/pedidos/pedido";
		}
		pedido.setTotal(pedido.getTotal()-dps.buscarLinea(idLinea).getTotal());
		ps.guardarPedido(pedido);
		dps.borrarLinea(idLinea);
		if(dps.buscarLineasPedido(idPedido).isEmpty()) {
			ps.borrarPedido(pedido);
			return "redirect:/pedidos/pedido";
		}
		return "redirect:/detallespedidos/"+idPedido;
	}
}
