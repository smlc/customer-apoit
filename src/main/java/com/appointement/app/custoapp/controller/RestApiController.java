package com.appointement.app.custoapp.controller;

import java.util.List;

import com.appointement.app.custoapp.beans.DashboardData;
import com.appointement.app.custoapp.services.db.AppointmentDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {

	private final Logger log = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	AppointmentDB appointmentDB;

	@GetMapping("/appointment")
	public List<DashboardData> appointment() {
		log.info("Dashboard get all appointment");
		return appointmentDB.findAll();
	}
}
