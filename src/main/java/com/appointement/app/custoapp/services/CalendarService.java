package com.appointement.app.custoapp.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.appointement.app.custoapp.beans.CalendarDate;
import com.appointement.app.custoapp.beans.AvailableHour;
import com.appointement.app.custoapp.services.db.AppointmentDB;

import org.springframework.stereotype.Service;

@Service
public class CalendarService {

	private AppointmentDB appointmentDB;

	public CalendarService(AppointmentDB appointmentDB) {
		this.appointmentDB = appointmentDB;
	}

	/**
	 * Find where to start the first day of the current month
	 * @return
	 */
	public List<Integer> blankedDay() {
		YearMonth yearMonthObject = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());
		return IntStream
				.range(1, yearMonthObject.atDay(1).getDayOfWeek().getValue())
				.boxed()
				.collect(Collectors.toList());
	}

	/**
	 * Get the number of day in the current month
	 * @return
	 */
	public List<CalendarDate> numberOfDayInCurrentMonth() {
		YearMonth yearMonthObject = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

		return IntStream
				.rangeClosed(1, yearMonthObject.lengthOfMonth())
				.boxed()
				.map(day -> new CalendarDate(day))
				.collect(Collectors.toList());
	}

	public List<AvailableHour> getAvailableHour(String date) {
		int workingDayHourStart = 8;
		int workingDayHourEnd = 18;
		List<AvailableHour> result = new ArrayList<>();

		LocalDateTime dateTime = LocalDateTime.parse(date + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		if(dateTime.getDayOfMonth() == LocalDate.now().getDayOfMonth()){
			workingDayHourStart = LocalDateTime.now().getHour();
		}

		Set<Integer> bookedHourByDay = appointmentDB.findBookedHourByDay(dateTime);

		for(int i = workingDayHourStart; i < workingDayHourEnd; i+=2) {
			if(!bookedHourByDay.contains(i)){
				result.add(new AvailableHour(i, i + 2));
			}
		}
		return result;
	}

}
