package br.com.aisdigital.araujofelipe.api.web.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aisdigital.araujofelipe.api.repository.entity.Project;
import br.com.aisdigital.araujofelipe.api.repository.entity.Record;
import br.com.aisdigital.araujofelipe.api.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectResource {

	private final ProjectService service;
	
	public ProjectResource(ProjectService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Project>> all() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@PostMapping("/{projectId}/allocate")
	public ResponseEntity<Project> allocateRecords(Principal principal, 
				@PathVariable(name = "projectId") Long projectId, 
				@RequestBody List<Record> records) {
		return ResponseEntity.ok(service.alocate(projectId, records));
	}
}
