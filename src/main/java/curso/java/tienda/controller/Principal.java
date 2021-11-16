package curso.java.tienda.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class Principal {
	
	private static Logger logger = LogManager.getLogger(Principal.class);
	
	@RequestMapping("")
	public String vistaLogin(Model model, HttpSession sesion) {
		logger.info("Accediendo a la aplicaion en la raiz.");
		sesion.setAttribute("usuario", null);
		sesion.setAttribute("rol", 4);
		return "redirect:/productos";
	}
}
