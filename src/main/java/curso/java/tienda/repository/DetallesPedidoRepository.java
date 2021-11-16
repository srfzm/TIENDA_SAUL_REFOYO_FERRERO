package curso.java.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.entity.DetallesPedido;

@Repository
public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Integer> {

	List<DetallesPedido> findByIdPedido(int idPedido);
	
    Long deleteByIdPedido(int idPedido);
    
    DetallesPedido findById(int id);

}
