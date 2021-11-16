package curso.java.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.entity.Pedidos;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Integer>{
	
	Pedidos findById(int id);
	
	List<Pedidos> findByEstado(String estado);
	
	List<Pedidos> findByIdUsuario(int idUsuario);
}
