package curso.java.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import curso.java.tienda.entity.Usuarios;
//import org.springframework.data.repository.CrudRepository;


@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios,Integer> {

	//Usuarios findByNombre(String nombre);
	Usuarios findByEmail(String email);
	Usuarios findById(int id);
	List<Usuarios> findByIdRol(int idRol);
	
	@Query(value="select * from usuarios where email=?1 and clave=?2", nativeQuery=true)
	Usuarios buscarUsuarioLogin(String email, String clave);
}
