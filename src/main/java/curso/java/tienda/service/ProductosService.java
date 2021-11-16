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
/*	
	@PostConstruct
	public void cargarProductos() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:s dd-MM-yyyy");
		try {
			Productos p = new Productos(4, "Metroid Dread", "Únete a la cazarrecompensas Samus Aran mientras escapa de un mortífero planeta alienígena infestado de amenazas mecánicas.", 60.0, 60, formatter.parse("12:30:40 12-09-2021"), formatter.parse("12:30:40 12-09-2021"), (float) 21, "dread.jpg");
			pr.save(p);
			p = new Productos(4, "Hollow Knight", "Explora cavernas tortuosas, ciudades antiguas y páramos mortales. Combate contra criaturas corrompidas, haz amistad con extraños insectos y resuelve los antiguos misterios que yacen en el corazón de reino.", 50d, 30, formatter.parse("15:50:40 03-07-2019"), formatter.parse("15:50:40 03-07-2019"), (float) 21, "hollow.png");
			pr.save(p);
			p = new Productos(1, "Marvel’s Spider-Man", "Ponte en la piel de un curtido experimentado y maduro Peter Parker y consigue salvar Manhattan de criminales y villanos todo esto compaginándolo con su vida personal. ", 30d, 80, formatter.parse("17:00:20 15-09-2018"), formatter.parse("17:00:20 15-09-2018"), (float) 21, "spider.jpg");
			pr.save(p);
			p = new Productos(1, "God of War", "Kratos, que ha dejado atrás el mundo de los dioses, deberá adaptarse a tierras que no le son familiares, afrontar peligros inesperados y aprovechar una segunda oportunidad de ejercer como padre. ", 30d, 60, formatter.parse("18:10:20 28-09-2018"), formatter.parse("18:10:20 28-09-2018"), (float) 21, "god.gif");
			pr.save(p);
			p = new Productos(1, "Devil May Cry 5", "Han pasado varios años en Devil May Cry 5 y la amenaza del poder demoníaco, desde hace mucho tiempo olvidado, ha vuelto a amenazar al mundo una vez más.", 40d, 33, formatter.parse("14:10:42 08-03-2019"), formatter.parse("14:10:42 08-03-2019"), (float) 21, "dmc.jpg");
			pr.save(p);
			p = new Productos(2, "Red Dead Redemption", "Cuando unos agentes federales amenazan a su familia, el exforajido John Marston recorre la frontera americana para ayudar a imponer la ley. ", 10d, 8, formatter.parse("19:13:49 21-05-2010"), formatter.parse("19:13:49 21-05-2010"), (float) 21, "reddead.jpg");
			pr.save(p);
			p = new Productos(2, "Assassin's Creed", "Assasin’s Creed coloca al jugador en el papel de Altair, un presunto miembro de un despiadado grupo de asesinos.", 5d, 10, formatter.parse("14:57:26 13-09-2007"), formatter.parse("14:57:26 13-09-2007"), (float) 21, "assassin.jpg");
			pr.save(p);
			p = new Productos(3, "Super Mario Odyssey", "Explora enormes reinos 3D llenos de secretos y sorpresas, incluidos atuendos para Mario y montones de maneras diferentes de interactuar con el entorno.", 40d, 40, formatter.parse("14:57:26 27-10-2017"), formatter.parse("14:57:26 27-10-2017"), (float) 21, "mario.jpg");
			pr.save(p);
			p = new Productos(3, "Crash Bandicoot 4: It's About Time", " Crash se lanza de lleno con tus marsupiales favoritos a una aventura temporal que se cae a pedazos.", 50d, 60, formatter.parse("17:37:16 16-09-2020"), formatter.parse("17:37:16 16-09-2020"), (float) 21, "crash.jpg");
			pr.save(p);
			p = new Productos(3, "Cuphead", "Cuphead es un juego de acción clásico que se centra en combates contra  jefes.", 20d, 70, formatter.parse("13:16:48 29-09-2017"), formatter.parse("13:16:48 29-09-2017"), (float) 21, "cup.jpg");
			pr.save(p);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/	
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
