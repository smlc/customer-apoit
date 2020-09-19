package com.appointement.app.custoapp.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.appointement.app.custoapp.beans.AppointmentInfo;
import com.appointement.app.custoapp.services.db.AppointmentDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class AppointmentValidationService {

	private final Logger log = LoggerFactory.getLogger(AppointmentValidationService.class);

	private AppointmentDB appointmentDB;

	public AppointmentValidationService(AppointmentDB appointmentDB) {
		this.appointmentDB = appointmentDB;
	}

	public boolean validateAppointment(AppointmentInfo appointmentInfo) {

		log.info("Ask validation of appointment : {}, {}, {}, {}", appointmentInfo.getSelectedDate(),
				appointmentInfo.getSelectedHour(), appointmentInfo.getSelectedService(), appointmentInfo.getPlace());

		//test if the date is valide, not weekend etc
		LocalDateTime dateTime = LocalDateTime.parse(appointmentInfo.getSelectedDate() + " " + appointmentInfo.getSelectedHour(),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		if (dateTime.toLocalDate().isBefore(LocalDate.now()) || dateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) return false;

		//test if still valide in DB
		if(!appointmentDB.isAvailable(dateTime)) return false;

		log.info("Going to save a new appointment in DB");
		return appointmentDB.saveAppointment(dateTime, appointmentInfo.getSelectedService(),
				 appointmentInfo.getUserEmail(), appointmentInfo.getUserPhone(), "A VENIR", appointmentInfo.getPlace());
	}




}
