package curso.java.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.entity.OpcionesMenu;

@Repository
public interface OpcionesMenuRepository extends JpaRepository<OpcionesMenu, Integer> {

	List<OpcionesMenu> findByIdRol(int idRol);
}
