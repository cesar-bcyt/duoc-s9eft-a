package com.example.b1efta;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class B1eftaApplication {

	private static final Logger logger = LogManager.getLogger(B1eftaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(B1eftaApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logStartup() {
		logger.info("La aplicación se ha iniciado correctamente. Ejecutando script SQL para cargar datos de prueba.");
	}

}
