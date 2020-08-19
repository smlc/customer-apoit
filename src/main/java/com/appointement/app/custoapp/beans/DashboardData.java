package com.appointement.app.custoapp.beans;

public class DashboardData {

	private String start;
	private String end;
	private String phone;
	private String email;
	private String services;
	private String status;
	private String place;

	public DashboardData(String start, String end, String phone, String email,
			String service, String status, String place) {
		this.start = start;
		this.end = end;
		this.phone = phone;
		this.email = email;
		this.services = service;
		this.status = status;
		this.place = place;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getServices() {
		return services;
	}

	public String getStatus() {
		return status;
	}

	public String getPlace() {
		return place;
	}
}
