package br.com.aisdigital.araujofelipe.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.aisdigital.araujofelipe.api.repository.entity.Period;
import br.com.aisdigital.araujofelipe.api.repository.entity.Record;
import br.com.aisdigital.araujofelipe.api.repository.entity.User;

@SpringBootTest
class RecordServiceTest {
	
	@Autowired
	RecordService service;
	
	private final static LocalDate MONDAY = LocalDate.parse( "2020-10-19");
	private final static LocalDate SUNDAY = LocalDate.parse("2020-10-18");
	private final static LocalTime [] VALID_MORNING_PERIOD = {LocalTime.parse("08:00"), LocalTime.parse("12:00")};
	private final static LocalTime [] INVALID_PERIOD = {LocalTime.parse("12:00"), LocalTime.parse("08:00")};
	private final static LocalTime [] VALID_PERIOD_WITHOUT_LUNCH = {LocalTime.parse("08:00"), LocalTime.parse("22:00")};

	@Test
	void validateThatStartTimeIsAfterEndTime() throws Exception {
		assertThatThrownBy(() -> 
			service.stamp(Mockito.mock(User.class), Record
				.builder()
				.date(MONDAY)
				.start(INVALID_PERIOD[0])
				.end(INVALID_PERIOD[1])
				.build())
		).hasMessage("The time interval is invalid.");
	}
	
	@Test
	void validateThatDateIsAWeekDay() {
		assertThatThrownBy(() -> 
			service.stamp(Mockito.mock(User.class), Record
				.builder()
				.date(SUNDAY)
				.start(VALID_MORNING_PERIOD[0])
				.build())
		).hasMessage("Only week days are permited.");
	}
	
	@Test
	void validateThatUserNeedALunchTime() {
		assertThatThrownBy(() -> 
			service.stamp(Mockito.mock(User.class), Record
				.builder()
				.date(MONDAY)
				.start(VALID_PERIOD_WITHOUT_LUNCH[0])
				.end(VALID_PERIOD_WITHOUT_LUNCH[1])
				.build())
		).hasMessage("Please, have a lunch time!");
	}
	
	@Test
	void validateThatPeriodIsCorrectlySet() {
		assertThatThrownBy(() -> 
			service.stamp(new User(1L), Record
				.builder()
				.date(MONDAY)
				.start(VALID_MORNING_PERIOD[0])
				.end(VALID_MORNING_PERIOD[1])
				.period(Period.EVENING)
				.build())
		).hasMessage("The period is invalid.");
	}
	
	@Test
	void validateThatSaveARecordSuccessFully() throws Exception {
		Record record = service.stamp(new User(1L),Record.builder()
				.date(MONDAY)
				.start(VALID_MORNING_PERIOD[0])
				.end(VALID_MORNING_PERIOD[1])
				.period(Period.MORNING)
				.build());
		assertThat(record.getId()).isNotNull();
	}
	
	@Test
	void shouldBeCloseAOpenedRecordWhenStartsANewRecord() throws Exception {
		User user = new User(1L);
		Record opened; 
		opened = service.stamp(user, Record.builder()
				.date(MONDAY)
				.start(VALID_MORNING_PERIOD[0])
				.build());
		assertThat(opened.getEnd()).isNull();
		final Long openedId = opened.getId();
		Record newRecord = Record.builder()
				.date(MONDAY)
				.start(VALID_MORNING_PERIOD[0].plusHours(1))
				.build();
		service.stamp(user, newRecord);
		List<Record> recordsUser = service.fetchAllBy(user);
		opened = recordsUser.stream().filter(r -> r.getId().equals(openedId)).findFirst().get();
		assertThat(opened.getEnd()).isNotNull();
	}
}
