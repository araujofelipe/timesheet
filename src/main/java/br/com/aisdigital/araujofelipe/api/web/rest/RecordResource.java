package br.com.aisdigital.araujofelipe.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aisdigital.araujofelipe.api.repository.entity.Record;
import br.com.aisdigital.araujofelipe.api.service.RecordService;

@RestController
@RequestMapping("/api/records")
public class RecordResource {
	
	private final RecordService service;
	
	public RecordResource(RecordService service) {
		this.service = service;
	}
	
	@PostMapping("/stamp")
	public ResponseEntity<Record> stamp(@Validated @RequestBody Record record) {
		return ResponseEntity.ok(service.stamp(record));
	}
}
