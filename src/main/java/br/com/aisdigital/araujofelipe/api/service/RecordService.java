package br.com.aisdigital.araujofelipe.api.service;

import org.springframework.stereotype.Service;

import br.com.aisdigital.araujofelipe.api.repository.RecordRepository;
import br.com.aisdigital.araujofelipe.api.repository.TimeCardRepository;
import br.com.aisdigital.araujofelipe.api.repository.entity.Record;
import br.com.aisdigital.araujofelipe.api.repository.entity.TimeCard;

@Service
public class RecordService {
	private final RecordRepository repository;
	private final TimeCardRepository timeCardRepository;
	
	public RecordService(RecordRepository repository, TimeCardRepository timeCardRepository) {
		this.repository = repository;
		this.timeCardRepository = timeCardRepository;
	}
	
	public Record stamp(Record record) {
		TimeCard timeCard = timeCardRepository.findByMonthAndUser(record.getDate().getMonth(), record.getUser());
		record.setTimeCard(timeCard);
		return repository.save(record);
	}

}
