package curso.java.tienda.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.Usuarios;
import curso.java.tienda.repository.UsuariosRepository;



@Service
public class UsuariosService {
	
	@Autowired
	private UsuariosRepository ur;
/*
	@PostConstruct
	public void cargarUsuarios() {
		Base64 base64 = new Base64();
		String pass = new String(base64.encode("1234".getBytes()));
		Usuarios u = new Usuarios(1,"Admin","admin@admin.com", pass);
		ur.save(u);
		u = new Usuarios(2, "ramon@correo.com", pass, "Ramón", "Garcia", "Marquez", "Llano Ponte", "Aviles", "Aviles", "648542876", "65638534N");
		ur.save(u);
		u = new Usuarios(2, "mario@correo.com", pass, "Mario", "Lopez", "Labrador", "Conde Ponte", "Gijon", "Gijon", "866482876", "86465534N");
		ur.save(u);
		u = new Usuarios(2, "marta@correo.com", pass, "Marta", "Perez", "Ferrero", "San Miguel", "Madrid", "Madrid", "648875428", "63859834N");
		ur.save(u);
		u = new Usuarios(3, "manuel@correo.com", pass, "Manuel", "Zapatero", "Castaño", "Alfonso XX", "Cartagena", "Murcia", "985648428", "97466385N");
		ur.save(u);
		u = new Usuarios(3, "rodri@correo.com", pass, "Rodriguez", "Valle", "Puertas", "San Vitero", "Madrid", "Madrid", "648435428", "76859834N");
		ur.save(u);
	}
*/	
	public boolean comprobarLogin(Usuarios usuario) {
		boolean result = false;
		
		if(ur.buscarUsuarioLogin(usuario.getEmail(), usuario.getClave())!=null) {
			result = true;
		}
		return result;
	}
	
	public Usuarios getUsuario(int id) {
		Usuarios uret = ur.findById(id);
		return uret;
	}
	
	public List<Usuarios> getUsuarioRol(int idRol) {
		return ur.findByIdRol(idRol);
	}
	
	public Usuarios getUsuario(Usuarios usuario) {
		Usuarios uret = ur.buscarUsuarioLogin(usuario.getEmail(), usuario.getClave());
		return uret;
	}
	
	
	public Usuarios guardarUsuario(Usuarios u) {
		Usuarios uret = ur.save(u);
		return uret;
	}
	
	public void borrarUsuario(int id) {
		Usuarios u = ur.findById(id);
		ur.delete(u);
	}
	
	public void actualizarUsuario(Usuarios u, int id) {
		Usuarios tmp = ur.findById(id);
		if(tmp!=null) {
			u.setId(tmp.getId());
			u.setClave(tmp.getClave());
			u.setIdRol(tmp.getIdRol());
			ur.save(u);	
		}
	}
}
