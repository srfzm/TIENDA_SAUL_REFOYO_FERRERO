package curso.java.tienda.task;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import curso.java.tienda.entity.Pedidos;
import curso.java.tienda.service.ConfiguracionService;
import curso.java.tienda.service.PedidosService;

@Component
public class ProcesarPedidosTask {
	
    private static final Logger logger = LogManager.getLogger(ProcesarPedidosTask.class);
    @Autowired
    private PedidosService ps;
    @Autowired
    private ConfiguracionService confs;


	@Scheduled(fixedRate = 60000, initialDelay = 60000)
	public void principal() {
		logger.info("Ejecutando task para procesar pedidos.");
		
		List<Pedidos> pendientes = ps.listadoPendientes();
		
		if(pendientes!=null) {
			
			for (Pedidos pedidos : pendientes) {
				
				pedidos.setEstado("enviado");
				pedidos.setNumFactura(confs.getNumFactura());
				ps.modificarPedido(pedidos);
				confs.setNumFactura();
				
			}
		}
	}
}
