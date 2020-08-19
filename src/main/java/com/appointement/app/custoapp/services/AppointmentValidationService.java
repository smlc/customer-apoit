package com.appointement.app.custoapp.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.appointement.app.custoapp.services.db.AppointmentDB;

import org.springframework.stereotype.Service;

@Service
public class AppointmentValidationService {

	private AppointmentDB appointmentDB;

	public AppointmentValidationService(AppointmentDB appointmentDB) {
		this.appointmentDB = appointmentDB;
	}

	public boolean validateAppointment(String selectDate, String selectedHour,
			String services, String email, String userPhone, String place) {

		//test if the date is valide, not weekend etc
		LocalDateTime dateTime = LocalDateTime.parse(selectDate + " " + selectedHour, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		if (dateTime.toLocalDate().isBefore(LocalDate.now()) || dateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) return false;

		//test if still valide in DB
		if(!appointmentDB.isAvailable(dateTime)) return false;

		return appointmentDB.saveAppointment(dateTime, services, email, userPhone, "A VENIR", place);
	}




}
