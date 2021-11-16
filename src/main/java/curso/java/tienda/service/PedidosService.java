package curso.java.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.Pedidos;
import curso.java.tienda.repository.PedidosRepository;

@Service
public class PedidosService {

	@Autowired
	private PedidosRepository pr;
	
	public List<Pedidos> listadoPedidos(int idUsuario) {
		return pr.findByIdUsuario(idUsuario);
	}
	
	public List<Pedidos> listadoPendientes() {
		return pr.findByEstado("pendiente");
	}
	
	public List<Pedidos> listadoCancelados() {
		return pr.findByEstado("solicitada cancelacion");
	}
	
	public Pedidos buscarPedido(int id) {
		return pr.findById(id);
	}
	
	public Pedidos guardarPedido(Pedidos p) {
		return pr.save(p);
	}
	
	public void modificarPedido(Pedidos p) {
		pr.save(p);
	}
	
	public void borrarPedido(Pedidos p) {
		pr.delete(p);
	}
}
