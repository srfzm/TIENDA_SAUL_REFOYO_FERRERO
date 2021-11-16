package curso.java.tienda.utilidades;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import curso.java.tienda.entity.OpcionesMenu;
import curso.java.tienda.service.ConfiguracionService;
import curso.java.tienda.service.OpcionesMenuService;

@Component
public class Utilidades {

	public HashMap<Integer, HashMap<String, ArrayList<OpcionesMenu>>> menu;
	public HashMap<String, String> configuracion;
	@Autowired
	private OpcionesMenuService ms;
	@Autowired
	private ConfiguracionService confs;
	
	@PostConstruct 
	public void cargarMenu() {
		menu = ms.cargarMenu();
		configuracion = confs.mapaConfiguracion();
		
	}
	
	public HashMap<String, ArrayList<OpcionesMenu>> menu(HttpSession sesion) {

		int idRol = (sesion.getAttribute("usuario")==null) ? 4 : (int)sesion.getAttribute("usuario");
		
		if(!menu.containsKey(idRol)) {
			return null;
		}
		
		return menu.get(idRol);
	}
	
	public HashMap<String, String> mapaConf() {

		return configuracion;
	}
}
