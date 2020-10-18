package br.com.aisdigital.araujofelipe.api.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.aisdigital.araujofelipe.api.repository.entity.Record;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
	
	public static String HAS_MORNING_OPENED_RECORD = "SELECT CASE WHEN EXISTS ( SELECT * FROM RECORD r  WHERE r.period = 'MORNING' AND r.end = null AND r.date = :date ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END";	
	
	Record findByUserAndDateAndEndIsNull(User user, LocalDate date);
	
	@Query(value = HAS_MORNING_OPENED_RECORD, nativeQuery = true)
	boolean hasAMorningPeriodOpenedByDate(LocalDate date);
	
}
