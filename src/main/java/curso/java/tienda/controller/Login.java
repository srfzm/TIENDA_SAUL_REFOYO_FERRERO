package curso.java.tienda.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.tienda.service.RolesService;
import curso.java.tienda.service.UsuariosService;
import curso.java.tienda.utilidades.Utilidades;
import curso.java.tienda.entity.Usuarios;



@Controller
@RequestMapping("/login")
public class Login {

	@Autowired
	UsuariosService us;
	@Autowired 
	Utilidades ut;
	@Autowired
	RolesService rs;
	private static final Logger logger = LogManager.getLogger(Login.class);
	
	@GetMapping("/form")
	public String vistaLogin(Model model, HttpSession sesion) {
		model.addAttribute("usuario",new Usuarios());
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		return "login/formlogin";
	}
	
	@GetMapping("/registroform")
	public String vistaRegistro(Model model, HttpSession sesion) {
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		model.addAttribute("usuarios",new Usuarios());
		model.addAttribute("usuario",new Usuarios());
		return "login/formregistro";
	}

	@PostMapping("/login")
	public String comprobarLoginUsuario(Model model, HttpSession sesion, @ModelAttribute Usuarios usuario) {
		Base64 base64 = new Base64();
		String pass = new String(base64.encode(usuario.getClave().getBytes()));
		usuario.setClave(pass);
		if(us.comprobarLogin(usuario)) {
			//sesion.setAttribute("usuario", usuario);
			logger.info("Acceso por login a la aplicación");
			Usuarios tmp = us.getUsuario(usuario);
			sesion.setAttribute("usuario", tmp.getId());
			sesion.setAttribute("rol", tmp.getIdRol());
			return "redirect:/productos";
		}
		
		//model.addAttribute("mensaje", "Login incorrecto");
		logger.info("Acceso incorrecto a la aplicación");
		model.addAttribute("opciones",ut.menu(sesion));
		model.addAttribute("configuracion",ut.mapaConf());
		return "login/formlogin";
	}
	
	@PostMapping("/registro")
	public String registrarse(@Valid @ModelAttribute Usuarios usuario, BindingResult bindingResult, Model model, HttpSession sesion) {		
		
		/*if(us.comprobarLogin(usuario)) {
			//sesion.setAttribute("usuario", usuario);
			Usuarios tmp = us.getUsuario(usuario);
			sesion.setAttribute("usuario", tmp.getId());
			sesion.setAttribute("rol", tmp.getIdRol());
			return "redirect:/productos";
		}*/
		
		if(bindingResult.hasErrors()) {
			logger.info("Intento de registro incorrecto");
			//model.addAttribute("usuario",usuario);
			model.addAttribute("opciones",ut.menu(sesion));
			model.addAttribute("configuracion",ut.mapaConf());
			return "login/formregistro";
		}
		
		Base64 base64 = new Base64();
		String pass = new String(base64.encode(usuario.getClave().getBytes()));
		usuario.setClave(pass);
		
		usuario.setIdRol(rs.buscarRolNombre("Cliente").getId());
		us.guardarUsuario(usuario);
		sesion.setAttribute("usuario", usuario.getId());
		sesion.setAttribute("rol", usuario.getIdRol());
		
		//model.addAttribute("mensaje", "Login incorrecto");
		//return "login/formlogin";
		logger.info("Registro de un cliente en la aplicación.");
		return "redirect:/productos";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession sesion) {		
		sesion.setAttribute("usuario", null);
		sesion.setAttribute("rol", null);
		//TODO: asignar rol anonimo
		return "redirect:/productos";
	}
}
