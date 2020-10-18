package br.com.aisdigital.araujofelipe.api.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.NotNull;

import br.com.aisdigital.araujofelipe.api.repository.entity.Period;

public class TimeAndDateUtil {

	public static Period getPeriodByLocalTime(@NotNull LocalTime timeLocal) {
		if(timeLocal.getHour() < LocalTime.NOON.getHour()) {
			return Period.MORNING;
		}
		return Period.EVENING;
	}
	
	
	public static boolean isValidInterval(LocalTime start, LocalTime end) {
		if(end == null && start != null) {
			return true;
		}
		
		if(end != null && start == null) {
			return false;
		}
		
		if(start != null) {
			return end.isAfter(start);
		}
		
		return false;
	}
	
	
	public static boolean isAWeekDay(LocalDate date) {
		return date.getDayOfWeek().getValue() > 5;
	}
	
	public static long totalHours(LocalTime start, LocalTime end) {
		return start.until(end, ChronoUnit.HOURS);
	}
}
