package curso.java.tienda.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.OpcionesMenu;
import curso.java.tienda.entity.Roles;
import curso.java.tienda.repository.OpcionesMenuRepository;

@Service
public class OpcionesMenuService {

	@Autowired
	private OpcionesMenuRepository mr;
	@Autowired
	private RolesService rs;
	private HashMap<Integer, HashMap<String, ArrayList<OpcionesMenu>>> menu;
	
	public List<OpcionesMenu> menu(int rolId) {
		return mr.findByIdRol(rolId);
	}
	
	@PostConstruct
	public void cargarOpciones() {
		OpcionesMenu op = new OpcionesMenu(1, "Listado", "Pedidos", "pedidos/pedido");
		mr.save(op);
		op = new OpcionesMenu(1, "Cancelar", "Pedidos", "pedidos/cancelar");
		mr.save(op);
		op = new OpcionesMenu(1, "Confirmar", "Pedidos", "pedidos/procesar");
		mr.save(op);
		op = new OpcionesMenu(1, "Catalogo", "Productos", "productos/");
		mr.save(op);
		op = new OpcionesMenu(1, "Listado Clientes", "Usuarios", "usuarios/clientes");
		mr.save(op);
		op = new OpcionesMenu(1, "Listado Empleados", "Usuarios", "usuarios/empleados");
		mr.save(op);
		op = new OpcionesMenu(1, "Crear Empleado", "Usuarios", "usuarios/form/empleado");
		mr.save(op);
		op = new OpcionesMenu(1, "Crear Cliente", "Usuarios", "usuarios/form/cliente");
		mr.save(op);
		op = new OpcionesMenu(1, "Lista", "Productos", "productos/lista");
		mr.save(op);
		op = new OpcionesMenu(1, "Perfil", "Usuarios", "usuarios/perfil");
		mr.save(op);
		
		op = new OpcionesMenu(2, "Listado", "Pedidos", "pedidos/pedido");
		mr.save(op);
		op = new OpcionesMenu(2, "Confirmar", "Pedidos", "pedidos/procesar");
		mr.save(op);
		op = new OpcionesMenu(2, "Catalogo", "Productos", "productos/");
		mr.save(op);
		op = new OpcionesMenu(2, "Listado Clientes", "Usuarios", "usuarios/clientes");
		mr.save(op);
		op = new OpcionesMenu(2, "Crear Cliente", "Usuarios", "usuarios/form/cliente");
		mr.save(op);
		op = new OpcionesMenu(2, "Lista", "Productos", "productos/lista");
		mr.save(op);
		op = new OpcionesMenu(2, "Perfil", "Usuarios", "usuarios/perfil");
		mr.save(op);
		
		
		op = new OpcionesMenu(3, "Listado", "Pedidos", "pedidos/pedido");
		mr.save(op);
		op = new OpcionesMenu(3, "Catalogo", "Productos", "productos/");
		mr.save(op);
		op = new OpcionesMenu(3, "Perfil", "Usuarios", "usuarios/perfil");
		mr.save(op);
		
		op = new OpcionesMenu(4, "Catalogo", "Productos", "productos/");
		mr.save(op);
	}
	
	public HashMap<Integer, HashMap<String, ArrayList<OpcionesMenu>>> cargarMenu() {
		
		List<Roles> lista = rs.listadoRoles();
		menu = new HashMap<Integer, HashMap<String, ArrayList<OpcionesMenu>>>();
		for (Roles roles : lista) {
			menu.put(roles.getId(), menuAgrupado(roles.getId()));
		}
		
		return menu;
	}
	
	public HashMap<String, ArrayList<OpcionesMenu>> menuAgrupado(int idRol) {
		List<OpcionesMenu> lista = menu(idRol);
		ArrayList<OpcionesMenu> array = null;
		HashMap<String, ArrayList<OpcionesMenu>> ret = new HashMap<String, ArrayList<OpcionesMenu>>();
		
		for (OpcionesMenu opcionesMenu : lista) {
			
			if(!ret.containsKey(opcionesMenu.getGrupoOpcion())) {
				array = new ArrayList<OpcionesMenu>();
				array.add(opcionesMenu);
				ret.put(opcionesMenu.getGrupoOpcion(), array);
			} else {
				array = ret.get(opcionesMenu.getGrupoOpcion());
				array.add(opcionesMenu);
			}
		}
		
		return ret;
	}
}
