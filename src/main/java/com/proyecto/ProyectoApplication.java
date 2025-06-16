package com.proyecto;

import com.proyecto.entity.Cita;
import com.proyecto.repository.CitaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);


	}
	@Bean
	CommandLineRunner init(CitaRepository citaRepository) {
		return args -> {
			List<Cita> citasInvalidas = citaRepository.findAll().stream()
					.filter(c -> c.getUsuario() == null || c.getNombreCliente() == null || c.getCorreo() == null || c.getTelefono() == null)
					.collect(Collectors.toList());

			citaRepository.deleteAll(citasInvalidas);
			System.out.println("⛔ Se eliminaron " + citasInvalidas.size() + " citas inválidas.");
		};
	}
}
