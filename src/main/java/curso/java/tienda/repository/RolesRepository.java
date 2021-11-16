package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

	public Roles findById(int id);
	
	public Roles findByRol(String rol);
}
