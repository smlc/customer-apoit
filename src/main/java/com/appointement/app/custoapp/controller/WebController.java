package com.appointement.app.custoapp.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.appointement.app.custoapp.beans.AppointmentInfo;
import com.appointement.app.custoapp.enums.ServiceProvided;
import com.appointement.app.custoapp.services.AppointmentValidationService;
import com.appointement.app.custoapp.services.CalendarService;
import com.appointement.app.custoapp.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

	private final Logger log = LoggerFactory.getLogger(WebController.class);

	@Autowired
	CalendarService calendarService;

	@Autowired
	AppointmentValidationService appointmentValidationService;

	@Autowired
	EmailService emailService;

	final List<String> DAYS = Arrays.asList("Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di");
	final List<String> MONTH = Arrays.asList("Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet","Août", "September", "Octobre", "Novembre", "Décembre");

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		return "dashboard";
	}

	@GetMapping("/loginDash")
	public String login(Model model) {
		return "loginDash";
	}

	@GetMapping("/register-step-1")
	public String getRest(Model model) {
		log.info("Start register");

		model.addAttribute("MONTH", MONTH.get(LocalDate.now().getMonthValue() - 1));
		model.addAttribute("YEAR", LocalDate.now().getYear());
		model.addAttribute("DAYS", DAYS);
		model.addAttribute("blankdays", calendarService.blankedDay());
		model.addAttribute("numberOfDaysInCurrentMonth", calendarService.numberOfDayInCurrentMonth());
		return "register-step-1";
	}

	@PostMapping("/register-step-2")
	public String getChosenDay(Model model, @ModelAttribute("selectedDate")String selectedDate) {
		model.addAttribute("availableHour", calendarService.getAvailableHour(selectedDate));
		model.addAttribute("serviceProvided", ServiceProvided.values());
		return "register-step-2";
	}

	@PostMapping("/register-step-3")
	public String getChosenHour(Model model, @ModelAttribute("selectedHour")String selectedHour, @ModelAttribute("selectedService")String selectedService) {

		return "register-step-3";
	}

	@PostMapping("/validate")
	public String validae(Model model, @ModelAttribute("appointmentData") AppointmentInfo appointmentInfo) {


		model.addAttribute("selectedDate", appointmentInfo.getSelectedDate());
		model.addAttribute("selectedHour", appointmentInfo.getSelectedHour());
		model.addAttribute("selectedService", appointmentInfo.getSelectedService());

		appointmentValidationService.validateAppointment(appointmentInfo.getSelectedDate(),
				appointmentInfo.getSelectedHour(), appointmentInfo.getSelectedService(),
				appointmentInfo.getUserEmail(), appointmentInfo.getUserPhone(), appointmentInfo.getPlace());

		emailService.notifyNewAppointment(appointmentInfo);

		log.info("Validate register");

		return "resume";
	}
}
