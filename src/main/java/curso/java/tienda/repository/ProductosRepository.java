package curso.java.tienda.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.entity.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {

	Productos findById(int id);
	
	List<Productos> findByIdIn(Set<Integer> ids);
	
	List<Productos> findByIdIn(ArrayList<Integer> ids);
	
	List<Productos> findByIdCategoria(int idCategoria);
	
	List<Productos> findAllByOrderByPrecioAsc();
	
	List<Productos> findByIdCategoriaOrderByPrecioAsc(int idCategoria);
	
	List<Productos> findAllByOrderByPrecioDesc();
	
	List<Productos> findByIdCategoriaOrderByPrecioDesc(int idCategoria);
	
	List<Productos> findAllByOrderByNombreAsc();
	
	List<Productos> findByIdCategoriaOrderByNombreAsc(int idCategoria);
	
	List<Productos> findAllByOrderByNombreDesc();
	
	List<Productos> findByIdCategoriaOrderByNombreDesc(int idCategoria);
}
