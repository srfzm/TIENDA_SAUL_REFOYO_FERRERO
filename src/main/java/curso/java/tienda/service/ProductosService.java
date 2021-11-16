package curso.java.tienda.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.Productos;
import curso.java.tienda.repository.ProductosRepository;

@Service
public class ProductosService {

	@Autowired
	private ProductosRepository pr;
	
	@PostConstruct
	public void cargarProductos() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:s dd-MM-yyyy");
		try {
			Productos p = new Productos(1, "Videojuego", "Descripcion produzto", 5.9, 10, formatter.parse("12:30:40 12-09-2016"), formatter.parse("14:32:07 12-09-2019"), (float) 21, null);
			pr.save(p);
			p = new Productos(1, "Videojuego2", "Descripcion producto", 5.9, 10, formatter.parse("12:30:40 12-09-2016"), formatter.parse("14:32:07 12-09-2019"), (float) 21, null);
			pr.save(p);
			p = new Productos(3, "Videojuego3", "Descripcion produzto", 5.9, 10, formatter.parse("12:30:40 12-09-2016"), formatter.parse("14:32:07 12-09-2019"), (float) 21, null);
			pr.save(p);
			p = new Productos(3, "Videojuego4", "Descripcion produzto", 5.9, 10, formatter.parse("12:30:40 12-09-2016"), formatter.parse("14:32:07 12-09-2019"), (float) 21, null);
			pr.save(p);
			p = new Productos(2, "Videojuego5", "Descripcion produzto", 5.9, 10, formatter.parse("12:30:40 12-09-2016"), formatter.parse("14:32:07 12-09-2019"), (float) 21, null);
			pr.save(p);
			p = new Productos(2, "Videojuego6", "Descripcion produzto", 5.9, 10, formatter.parse("12:30:40 12-09-2016"), formatter.parse("14:32:07 12-09-2019"), (float) 21, null);
			pr.save(p);
			p = new Productos(1, "Videojuego7", "Descripcion produzto", 5.9, 10, formatter.parse("12:30:40 12-09-2016"), formatter.parse("14:32:07 12-09-2019"), (float) 21, null);
			pr.save(p);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Productos> listaProductos() {
		return pr.findAll();
	}
	
	public List<Productos> listaProductosFiltrada(String categoria, String orden) {
		int ord = (orden==null) ? 0 : Integer.valueOf(orden);
		int cat = (categoria==null) ? 0 : Integer.valueOf(categoria);
		List<Productos> dev = null;
		switch (ord) {
		case 1:
			if(cat!=0) {
				dev = pr.findByIdCategoriaOrderByNombreAsc(cat);
			} else {
				dev = pr.findAllByOrderByNombreAsc();
			}
			
			break;
		case 2:
			if(cat!=0) {
				dev = pr.findByIdCategoriaOrderByNombreDesc(cat);
			} else {
				dev = pr.findAllByOrderByNombreDesc();
			}
			break;
		case 3:
			if(cat!=0) {
				dev = pr.findByIdCategoriaOrderByPrecioAsc(cat);
			} else {
				dev = pr.findAllByOrderByPrecioAsc();
			}
			break;
		case 4:
			if(cat!=0) {
				dev = pr.findByIdCategoriaOrderByPrecioDesc(cat);
			} else {
				dev = pr.findAllByOrderByPrecioDesc();
			}
			break;

		default:
			if(cat!=0) {
				dev = pr.findByIdCategoria(cat);
			} else {
				dev = pr.findAll();
			}
			break;
		}
		return dev;
	}
	
	public List<Productos> listaCarrito(HashMap<Integer, Integer> carrito) {
		return pr.findByIdIn(carrito.keySet());
	}
	
	public HashMap<Integer, String> mapaProductos(HashMap<Integer, String> ids) {
		
		List<Productos> lista =  pr.findByIdIn(ids.keySet());
		
		for (Productos productos : lista) {
			ids.put(productos.getId(), productos.getNombre());
		}
		
		return ids;
	}
	
	public Double totalProductos(List<Productos> lista, HashMap<Integer, Integer> cantidades) {
		Double total=0d;
		for (Productos productos : lista) {
			total+=productos.getPrecio()*cantidades.get(productos.getId());
		}
		
		return total;
	}
	
	public Productos cargarProducto(int id) {
		return pr.findById(id);
	}
	
	public void guardarProducto(Productos p) {
		pr.save(p);
	}
	
	public void borrarProducto(int id) {
		Productos p = cargarProducto(id);
		if(p!=null) {
			pr.delete(p);
		}
	}
	
	public void modificarProducto(Productos p, int id) {
		Productos prod = cargarProducto(id);
		if(prod!=null) {
			p.setId(id);
			p.setFechaAlta(prod.getFechaAlta());
			p.setFechaBaja(prod.getFechaBaja());
			if(p.getImagen()==null)
				p.setImagen(prod.getImagen());
			pr.save(p);
		}
	}
	
	public boolean existeProducto(int id) {
		return pr.existsById(id);
	}
}
