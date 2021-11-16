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

	@PostConstruct
	public void cargarUsuarios() {
		Base64 base64 = new Base64();
		String pass = new String(base64.encode("1234".getBytes()));
		Usuarios u = new Usuarios(1,"Admin","admin@admin.com", pass);
		ur.save(u);
		u = new Usuarios(2,"Ramon", "ramon@correo.com", pass);
		ur.save(u);
	}
	
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
