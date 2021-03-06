package br.com.aisdigital.araujofelipe.api.web.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aisdigital.araujofelipe.api.repository.entity.Record;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;
import br.com.aisdigital.araujofelipe.api.service.RecordService;
import br.com.aisdigital.araujofelipe.api.service.UserService;

@RestController
@RequestMapping("/api/records")
public class RecordResource {
	
	private final RecordService service;
	private final UserService userService;
	
	public RecordResource(RecordService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<Record>> records(Principal principal) {
		User user = this.userService.findByLogin(principal.getName());
		return ResponseEntity.ok(service.fetchAllBy(user));
	}
	
	@PostMapping("/stamp")
	public ResponseEntity<Record> stamp(Principal principal, @Validated @RequestBody Record record) throws Exception {
		User user = this.userService.findByLogin(principal.getName());
		return ResponseEntity.ok(service.stamp(user, record));
	}
}
