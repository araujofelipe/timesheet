package br.com.aisdigital.araujofelipe.api.service;

import static br.com.aisdigital.araujofelipe.api.repository.entity.Period.EVENING;
import static br.com.aisdigital.araujofelipe.api.repository.entity.Period.MORNING;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.aisdigital.araujofelipe.api.repository.RecordRepository;
import br.com.aisdigital.araujofelipe.api.repository.TimeCardRepository;
import br.com.aisdigital.araujofelipe.api.repository.entity.Record;
import br.com.aisdigital.araujofelipe.api.repository.entity.TimeCard;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;
import br.com.aisdigital.araujofelipe.api.util.TimeAndDateUtil;

@Service
public class RecordService {
	private final RecordRepository repository;
	private final TimeCardRepository timeCardRepository;
	
	public RecordService(RecordRepository repository, TimeCardRepository timeCardRepository) {
		this.repository = repository;
		this.timeCardRepository = timeCardRepository;
	}
	
	public Record stamp(User user, Record record) throws Exception {
		validateRecord(record);
		closeOpenedRecord(user, record);
		TimeCard timeCard = timeCardRepository.findByMonthAndUser(record.getDate().getMonth(), record.getUser());
		record.setTimeCard(timeCard);
		record.setUser(user);
		return repository.save(record);
	}
	
	private void closeOpenedRecord(User user, Record record) {
		Record openedRecord  = repository.findByUserAndDateAndEndIsNull(user, record.getDate());
		if(openedRecord != null) {
			openedRecord.setEnd(record.getStart());
			repository.save(openedRecord);
		}
	}

	private void validateRecord(Record record) throws Exception {
		
		if(TimeAndDateUtil.isAWeekDay(record.getDate())) {
			throw new Exception("Only week days are permited.");
		}
		
		if(!TimeAndDateUtil.isValidInterval(record.getStart(), record.getEnd())) {
			throw new Exception("The time interval is invalid.");
		}
		validateEvening(record);
		validateHoursWorked(record);
		
 	}
	
	private void validateHoursWorked(Record record) throws Exception {
		if(record.getStart() != null && record.getEnd() != null) {
			long totalHours = TimeAndDateUtil.totalHours(record.getStart(), record.getEnd());
			if(totalHours > 8) {
				throw new Exception("Please, have a lunch time!");
			}
		}	
	}

	private boolean validateEvening(Record record) throws Exception {
		if(record.getPeriod().equals(EVENING)) {
			if(TimeAndDateUtil.getPeriodByLocalTime(record.getStart()).equals(MORNING)) {
				throw new Exception("The period is invalid.");
			}
			if(repository.hasAMorningPeriodOpenedByDate(record.getDate())) {
				throw new Exception("The date has a not ended morning period");
			}
		}
		return true;
	}

	public List<Record> fetchAllBy(User user) {
		return repository.findByUser(user);
	}

}
