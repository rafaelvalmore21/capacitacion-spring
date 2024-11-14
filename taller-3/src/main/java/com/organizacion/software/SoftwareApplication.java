package com.organizacion.software;

import com.organizacion.software.model.Pedido;
import com.organizacion.software.model.Producto;
import com.organizacion.software.repository.PedidoRepository;
import com.organizacion.software.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwareApplication.class, args);
	}

	@Bean
	public CommandLineRunner example(ProductoRepository repository, PedidoRepository pedidoRepository) {
		return (args) -> {
			// save a few items
			repository.save(new Producto(1L,"Iphone","16 Pro 256Gb",3.500,10));
			repository.save(new Producto(2L, "Iphone", "14 Pro 128Gb", 2.500, 15));
			repository.save(new Producto(3L, "Iphone", "13 Pro 128Gb", 1.500, 8));
			repository.save(new Producto(4L, "Iphone", "12 Pro 128Gb", 1.000, 6));
			repository.save(new Producto(5L, "Iphone", "11 Pro 64Gb", 0.500, 9));
			// fetch all items
			log.info("Consulta todos los registros de Items");
			log.info("-------------------------------");
			for (Producto producto : repository.findAll()) {
				log.info(producto.toString());
			}
			log.info("-------------------------------");
			pedidoRepository.save(new Pedido(1L,"13/11/2024",23000.00,1L));
			pedidoRepository.save(new Pedido(1L,"13/11/2024",23000.00,2L));
			// fetch all items
			log.info("Consulta todos los registros de pedidos");
			log.info("-------------------------------");
			for (Pedido pedido : pedidoRepository.findAll()) {
				log.info(pedido.toString());
			}
			log.info("-------------------------------");
		};
	}
}
