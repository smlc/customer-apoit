package com.appointement.app.custoapp.controller;

import java.util.List;

import com.appointement.app.custoapp.beans.AppointmentInfo;
import com.appointement.app.custoapp.beans.DashboardData;
import com.appointement.app.custoapp.services.db.AppointmentDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class RestApiController {

	@Autowired
	AppointmentDB appointmentDB;

	@GetMapping("/appointment")
	public List<DashboardData> appointment() {
		return appointmentDB.findAll();
	}
}
