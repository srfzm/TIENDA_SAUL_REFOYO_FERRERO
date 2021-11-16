package curso.java.tienda.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.Categorias;
import curso.java.tienda.repository.CategoriasRepository;

@Service
public class CategoriasService {
	
	@Autowired
	private CategoriasRepository cr;
	
	@PostConstruct
	public void cargarCategorias() {
		Categorias c = new Categorias("Accion","Descripcion");
		cr.save(c);
		c = new Categorias("Aventuras","Descripcion");
		cr.save(c);
		c = new Categorias("Plataformas","Descripcion");
		cr.save(c);
	}
	
	public String buscarCategoriaNombre(int id) {
		return cr.findById(id).getNombre();
	}
	
	public void guardarCategoria(Categorias c) {
		
		cr.save(c);
	}
	
	public HashMap<Integer, String> mapaCategorias() {
		HashMap<Integer, String> mapa = new HashMap<Integer, String>();
		List<Categorias> lista = cr.findAll();
		for (Categorias categorias : lista) {
			mapa.put(categorias.getId(), categorias.getNombre());
		}
		
		return mapa;
	}
}
