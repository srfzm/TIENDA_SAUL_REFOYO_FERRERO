package curso.java.tienda.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.java.tienda.entity.MetodosPago;
import curso.java.tienda.repository.MetodosPagoRepository;

@Service
public class MetodosPagoService {

	@Autowired
	private MetodosPagoRepository mr;
/*
	@PostConstruct
	public void cargarMetodosPago() {
		MetodosPago mt = new MetodosPago("tarjeta");
		mr.save(mt);
		mt = new MetodosPago("paypal");
		mr.save(mt);
	}
*/	
	public String buscarMetodo(int idMetdod) {
		return mr.findById(idMetdod).getMetodoPago();
	}
	
	public HashMap<Integer, String> mapaPago() {
		HashMap<Integer, String> mapa = new HashMap<Integer, String>();
		
		List<MetodosPago> lista = mr.findAll();
		for (MetodosPago metodosPago : lista) {
			mapa.put(metodosPago.getId(), metodosPago.getMetodoPago());
		}
		
		return mapa;
	}
}
