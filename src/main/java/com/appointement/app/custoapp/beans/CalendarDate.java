package com.appointement.app.custoapp.beans;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalendarDate {

	private int day;
	private LocalDate date;

	public CalendarDate(int day) {
		this.day = day;
		date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);
	}

	public boolean isToday(){
		return date.isEqual(LocalDate.now());
	}

	public boolean isBeforeToday() {
		return date.isBefore(LocalDate.now());
	}

	/**
	 * Get some rest sunday
	 * @return
	 */
	public boolean isNoWorkingDay() {
		return date.getDayOfWeek().equals(DayOfWeek.SUNDAY) ;
	}

	public int getDay() {
		return day;
	}
}
