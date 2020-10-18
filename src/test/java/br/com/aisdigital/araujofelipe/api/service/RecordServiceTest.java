package br.com.aisdigital.araujofelipe.api.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.aisdigital.araujofelipe.api.repository.entity.Record;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;

@SpringBootTest
class RecordServiceTest {
	
	@Autowired
	RecordService service;
	
	private final static String MONDAY = "2020-10-19";
	private final static String SUNDAY = "2020-10-18";
	private final static String [] VALID_PERIOD = {"08:00", "12:00"};
	private final static String [] INVALID_PERIOD = {"12:00", "08:00"};
	private final static String [] VALID_PERIOD_WITHOUT_LUNCH = {"08:00", "22:00"};

	

	
	@Test
	void validateThatStartTimeIsAfterEndTime() throws Exception {
		assertThatExceptionOfType(Exception.class)
		.isThrownBy(() -> 
			service.stamp(Mockito.mock(User.class), Record
				.builder()
				.date(LocalDate.parse(MONDAY))
				.start(LocalTime.parse(INVALID_PERIOD[0]))
				.end(LocalTime.parse(INVALID_PERIOD[1]))
				.build())
		);
	}
	
	@Test
	void validateThatDateIsAWeekDay() {
		assertThatExceptionOfType(Exception.class)
		.isThrownBy(() -> 
			service.stamp(Mockito.mock(User.class), Record
				.builder()
				.date(LocalDate.parse(SUNDAY))//sunday
				.start(LocalTime.parse(VALID_PERIOD[0]))
				.build())
		);
	}
	
	@Test
	void validateThatUserNeedALunchTime() {
		assertThatExceptionOfType(Exception.class)
		.isThrownBy(() -> 
			service.stamp(Mockito.mock(User.class), Record
				.builder()
				.date(LocalDate.parse(MONDAY))
				.start(LocalTime.parse(VALID_PERIOD_WITHOUT_LUNCH[0]))
				.end(LocalTime.parse(VALID_PERIOD_WITHOUT_LUNCH[1]))
				.build())
		);
	}
	
	@Test
	void validateThatPeriodIsCorrectlySet() {
		assertThatExceptionOfType(Exception.class)
		.isThrownBy(() -> 
			service.stamp(Mockito.mock(User.class), Record
				.builder()
				.date(LocalDate.parse(MONDAY))
				.start(LocalTime.parse(VALID_PERIOD[0]))
				.end(LocalTime.parse(VALID_PERIOD[1]))
				.build())
		);
	}
	
}
