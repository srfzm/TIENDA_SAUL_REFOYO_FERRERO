package curso.java.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.DetallesPedido;
import curso.java.tienda.repository.DetallesPedidoRepository;

@Service
public class DetallesPedidoService {

	@Autowired
	private DetallesPedidoRepository pr;
	
	public void guardarDPedido(DetallesPedido dp) {
		pr.save(dp);
	}
	
	public void borrarPorPedido(int idPedido) {		
		pr.deleteByIdPedido(idPedido);
	}
	
	public void borrarLinea(int idLinea) {
		DetallesPedido det = pr.findById(idLinea);
		pr.delete(det);
	}
	
	public List<DetallesPedido> buscarLineasPedido(int idPedido) {		
		return pr.findByIdPedido(idPedido);
	}
	
	public DetallesPedido buscarLinea(int id) {		
		return pr.findById(id);
	}
}
