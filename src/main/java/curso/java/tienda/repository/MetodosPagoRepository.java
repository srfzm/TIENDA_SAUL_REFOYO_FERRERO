package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.entity.MetodosPago;

@Repository
public interface MetodosPagoRepository extends JpaRepository<MetodosPago, Integer> {

	public MetodosPago findById(int id);
}
