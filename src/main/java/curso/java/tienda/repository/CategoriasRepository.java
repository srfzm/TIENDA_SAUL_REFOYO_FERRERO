package curso.java.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curso.java.tienda.entity.Categorias;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Integer> {

	Categorias findById(int id);
}
