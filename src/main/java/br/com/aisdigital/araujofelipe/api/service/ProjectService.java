package br.com.aisdigital.araujofelipe.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.aisdigital.araujofelipe.api.repository.ProjectRepository;
import br.com.aisdigital.araujofelipe.api.repository.RecordRepository;
import br.com.aisdigital.araujofelipe.api.repository.entity.Project;
import br.com.aisdigital.araujofelipe.api.repository.entity.Record;

@Service
public class ProjectService {

	private final ProjectRepository repository;
	private final RecordRepository recordRepository;
	public ProjectService(ProjectRepository repository, RecordRepository recordRepository) {
		this.repository = repository;
		this.recordRepository = recordRepository;
	}
	
	public List<Project> getAll() {
		return repository.findAll();
	}
	
	public Project alocate(Long id, List<Record> records) {
		Project project = repository.getOne(id);
		List<Record> loadedRecords = records
				.stream()
				.map(record -> recordRepository.getOne(record.getId()))
				.collect(Collectors.toList());
		project.setRecords(loadedRecords.stream().collect(Collectors.toSet()));
		return repository.save(project);
	}
}
