package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.entity.Usuarios;
import curso.java.tienda.service.RolesService;
import curso.java.tienda.service.UsuariosService;
import curso.java.tienda.utilidades.Utilidades;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private UsuariosService us;
	@Autowired
	private Utilidades ut;
	@Autowired
	private RolesService rs;
	
	@GetMapping("/clientes")
	public String listaClientes(Model model, HttpSession sesion) {
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("listaUsuarios",us.getUsuarioRol(rs.buscarRolNombre("Cliente").getId()));
		return "usuarios/lista";
	}
	
	@GetMapping("/empleados")
	public String listaEmpleados(Model model, HttpSession sesion) {
		
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("listaUsuarios",us.getUsuarioRol(rs.buscarRolNombre("Empleado").getId()));
		return "usuarios/lista";
	}
	
	@GetMapping("/perfil")
	public String verPerfil(Model model, HttpSession sesion) {		
		
		model.addAttribute("usuario",us.getUsuario((int)sesion.getAttribute("usuario")));
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		return "usuarios/perfil";
	}
	
	@GetMapping("/form/cliente")
	public String vistaFormularioCliente(Model model, HttpSession sesion) {		
		
		model.addAttribute("usuario",new Usuarios());
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("modo","cliente");
		return "usuarios/usuario";
	}
	
	@GetMapping("/modificar/{id}")
	public String vistaModificarCliente(Model model, HttpSession sesion, @PathVariable("id") int id) {		
		
		Usuarios usuario = us.getUsuario(id);
		if(usuario==null) {
			return "redirect:/productos";
		}
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("modo","modificar");
		model.addAttribute("idUsuario",id);
		return "usuarios/usuario";
	}
	
	@GetMapping("/form/empleado")
	public String vistaFormularioEmpleado(Model model, HttpSession sesion) {		
		
		model.addAttribute("usuario",new Usuarios());
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("modo","empleado");
		return "usuarios/usuario";
	}
	
	@PostMapping("/new/empleado")
	public String newUsuarioEmpleado(Model model, HttpSession sesion, @ModelAttribute Usuarios usuario) {
		Base64 base64 = new Base64();
		String pass = new String(base64.encode(usuario.getClave().getBytes()));
		usuario.setClave(pass);
		usuario.setIdRol(rs.buscarRolNombre("Empleado").getId());
		us.guardarUsuario(usuario);
		return "redirect:/productos";
	}
	
	@PostMapping("/new/cliente")
	public String newUsuarioCliente(Model model, HttpSession sesion, @ModelAttribute Usuarios usuario) {
		Base64 base64 = new Base64();
		String pass = new String(base64.encode(usuario.getClave().getBytes()));
		usuario.setClave(pass);
		usuario.setIdRol(rs.buscarRolNombre("Cliente").getId());
		us.guardarUsuario(usuario);
		return "redirect:/productos";
	}
	
	@GetMapping("/borrar/{id}")
	public String deleteUsuario(Model model, @PathVariable("id") int id) {
		us.borrarUsuario(id);
		return "redirect:/productos";
	}
	
	@PostMapping("/update/{id}")
	public String updateUsuario(Model model, HttpSession sesion, @PathVariable("id") int id, @ModelAttribute Usuarios usuario) {
		us.actualizarUsuario(usuario, id);
		return "redirect:/productos";
	}
	
	@PostMapping("/registro")
	public String comprobarLoginUsuario(HttpSession sesion, @ModelAttribute Usuarios usuario) {		
		//TODO:Comprobaciones de usuario repetido
		usuario = us.guardarUsuario(usuario);
		sesion.setAttribute("usuario", usuario.getId());
		
		return "redirect:/productos";
	}
}
