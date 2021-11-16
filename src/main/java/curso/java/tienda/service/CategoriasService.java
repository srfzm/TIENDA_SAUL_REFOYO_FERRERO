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
		Categorias c = new Categorias("Accion","Género en el que el jugador debe usar su velocidad, destreza y tiempo de reacción.");
		cr.save(c);
		c = new Categorias("Aventuras","Género de videojuegos, caracterizados por la investigación, exploración, la solución de rompecabezas, la interacción con personajes del videojuego, y un enfoque en el relato en vez de desafíos basados en reflejos .");
		cr.save(c);
		c = new Categorias("Plataformas"," Género de videojuegos que se caracterizan por tener que caminar, correr, saltar o escalar sobre una serie de plataformas y acantilados.");
		cr.save(c);
		c = new Categorias("Acción-Aventura","Género que combina elementos del género aventura con elementos del género acción.");
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
