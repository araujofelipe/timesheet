package br.com.aisdigital.araujofelipe;

import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.aisdigital.araujofelipe.api.repository.ProjectRepository;
import br.com.aisdigital.araujofelipe.api.repository.UserRepository;
import br.com.aisdigital.araujofelipe.api.repository.entity.Project;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;

@SpringBootApplication
public class TimesheetApplication {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TimesheetApplication.class, args);
	}
	
	
	@PostConstruct
	public void feedUsers() {
		Arrays.asList(Month.values());
		List<User> users = Stream.of(
				new User(1L, "Pernaloga", "velhocoelho"),
				new User(2L, "Patolino", "opatolino")
				).collect(Collectors.toList());
		userRepository.saveAll(users);
	}
	
	@PostConstruct
	public void feedProjects() {
		List<Project> projects = Stream.of(
				new Project(1L, "Fábrica de Memes"),
				new Project(2L, "Gerador de Lero Lero")
		).collect(Collectors.toList());
		projectRepository.saveAll(projects);
	}

}
