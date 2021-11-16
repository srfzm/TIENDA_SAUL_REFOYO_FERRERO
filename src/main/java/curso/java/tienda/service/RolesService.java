package curso.java.tienda.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.Roles;
import curso.java.tienda.repository.RolesRepository;

@Service
public class RolesService {

	@Autowired
	private RolesRepository rr;
	
	@PostConstruct
	public void cargarRoles() {
		Roles r = new Roles("Administrador");
		rr.save(r);
		r = new Roles("Empleado");
		rr.save(r);
		r = new Roles("Cliente");
		rr.save(r);
		r = new Roles("Anonimo");
		rr.save(r);
	}
	
	public List<Roles> listadoRoles() {
		return rr.findAll();
	}
	
	public Roles buscarRol(int id) {
		return rr.findById(id);
	}
	
	public Roles buscarRolNombre(String nombre) {
		return rr.findByRol(nombre);
	}
}
