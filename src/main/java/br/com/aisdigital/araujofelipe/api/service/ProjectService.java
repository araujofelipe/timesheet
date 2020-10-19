package br.com.aisdigital.araujofelipe.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.aisdigital.araujofelipe.api.repository.ProjectRepository;
import br.com.aisdigital.araujofelipe.api.repository.entity.Project;
import br.com.aisdigital.araujofelipe.api.repository.entity.Record;

@Service
public class ProjectService {

	private final ProjectRepository repository;
	
	public ProjectService(ProjectRepository repository) {
		this.repository = repository;
	}
	
	public List<Project> getAll() {
		return repository.findAll();
	}
	
	public Project alocate(Long id, List<Record> records) {
		Project project = repository.getOne(id);
		project.setRecords(records.stream().collect(Collectors.toSet()));
		return repository.save(project);
	}
}
