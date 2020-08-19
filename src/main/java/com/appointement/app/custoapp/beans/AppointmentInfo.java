package com.appointement.app.custoapp.beans;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Informations on appointment send by the user.
 */
public class AppointmentInfo {

	private String selectedDate;
	private String selectedHour;
	private String selectedService;
	private String userPhone;
	private String userEmail;
	private String place;
	private String start;
	private String end;

	public AppointmentInfo(String selectedDate, String selectedHour, String selectedService,
			String userPhone, String userEmail, String place) {
		this.selectedDate = selectedDate;
		this.selectedHour = selectedHour;
		this.selectedService = selectedService;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.place = place;
	}



	public String getSelectedDate() {
		return selectedDate;
	}

	public String getSelectedHour() {
		return selectedHour;
	}

	public String getSelectedService() {
		return selectedService;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getPlace() {
		return place;
	}

	@Override
	public String toString() {
		return "AppointmentInfo{" +
				"selectedDate='" + selectedDate + '\'' +
				", selectedHour='" + selectedHour + '\'' +
				", selectedService='" + selectedService + '\'' +
				", userPhone='" + userPhone + '\'' +
				", userEmail='" + userEmail + '\'' +
				", place='" + place + '\'' +
				'}';
	}
}
