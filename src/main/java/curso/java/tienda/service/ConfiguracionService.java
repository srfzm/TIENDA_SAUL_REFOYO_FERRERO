package curso.java.tienda.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.Configuracion;
import curso.java.tienda.repository.ConfiguracionRepository;

@Service
public class ConfiguracionService {

	@Autowired
	private ConfiguracionRepository confr;
/*
	@PostConstruct
	public void cargarConfiguracion() {
		Configuracion conf = new Configuracion("direccion", "Calle La Mentira", "texto");
		confr.save(conf);
		conf = new Configuracion("cif", "4939", "numero");
		confr.save(conf);
		conf = new Configuracion("nombreTienda", "Universal Games", "texto");
		confr.save(conf);
		conf = new Configuracion("pais", "España", "texto");
		confr.save(conf);
		conf = new Configuracion("numFactura", "0", "numero");
		confr.save(conf);
	}
*/	
	public HashMap<String, String> mapaConfiguracion() {
		List<Configuracion> lista = confr.findAll();
		HashMap<String, String> mapa = new HashMap<String, String>();
		
		for (Configuracion configuracion : lista) {
			mapa.put(configuracion.getClave(), configuracion.getValor());
		}
		
		return mapa;
	}
	
	public String getNumFactura() {
		
		Configuracion c = confr.findByClave("numFactura");
		
		return Integer.toString(Integer.valueOf(c.getValor())+1);
		
	}
	
	public void setNumFactura() {
		Configuracion c = confr.findByClave("numFactura");
		c.setValor(Integer.toString(Integer.valueOf(c.getValor())+1));
		confr.save(c);
	}
}
