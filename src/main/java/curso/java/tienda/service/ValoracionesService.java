package curso.java.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;

import curso.java.tienda.repository.ValoracionesRepository;

public class ValoracionesService {

	@Autowired
	private ValoracionesRepository vr;
}
