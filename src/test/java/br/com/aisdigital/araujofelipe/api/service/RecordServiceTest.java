package br.com.aisdigital.araujofelipe.api.service;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecordServiceTest {
	private final RecordService service;
	
	public RecordServiceTest(RecordService service) {
		this.service = service;
	}
	
	void validateThatStartTimeIsNotAfterEndTime() {
		
	}
	
}
