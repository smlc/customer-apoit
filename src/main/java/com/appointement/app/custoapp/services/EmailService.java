package com.appointement.app.custoapp.services;

import com.appointement.app.custoapp.beans.AppointmentInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Value("${email.notify.recipient}")
	private String recipient;

	@Value("${email.notify.sender}")
	private String sender;

	@Async
	public void notifyNewAppointment(AppointmentInfo appointmentInfo) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(sender);
		message.setTo(recipient);
		message.setSubject("Carwashdiff - Notification nouveau rendez-vous");
		message.setText(String.format(
				"Un nouveau rendez-vous à été enregistré. %n"
				+"Date : %s %n"
				+"Heure : %s %n"
				+"Services : %s %n"
				+"Telephone : %s %n"
				+"Email : %s %n"
				+"Lieu : %s %n",
				appointmentInfo.getSelectedDate(), appointmentInfo.getSelectedHour(),
				appointmentInfo.getSelectedService(), appointmentInfo.getUserPhone(),
				appointmentInfo.getUserEmail(), appointmentInfo.getPlace()
		));
		emailSender.send(message);

	}
}
