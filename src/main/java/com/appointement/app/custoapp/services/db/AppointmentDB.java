package com.appointement.app.custoapp.services.db;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.appointement.app.custoapp.beans.DashboardData;
import com.appointement.app.custoapp.controller.RestApiController;
import com.appointement.app.custoapp.db.tables.records.AppointmentRecord;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import static com.appointement.app.custoapp.db.Tables.APPOINTMENT;
import static org.jooq.impl.DSL.day;
import static org.jooq.impl.DSL.hour;

@Component
public class AppointmentDB {

	public static final int DURATION_OF_APPOINTMENT = 2;

	private final Logger log = LoggerFactory.getLogger(AppointmentDB.class);

	DSLContext dslContext;

	public AppointmentDB(DSLContext dslContext) {
		this.dslContext = dslContext;
	}

	public Set<Integer> findBookedHourByDay(LocalDateTime day) {

		OffsetDateTime searchDate = day.atOffset(ZoneOffset.ofHoursMinutes(0, 00));
		return dslContext.selectFrom(APPOINTMENT)
				.where(day(APPOINTMENT.START_DATETIME_AT).eq(searchDate.getDayOfMonth()))
				.fetch(APPOINTMENT.START_DATETIME_AT)
				.stream()
				.map(startTime -> startTime.atZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.ofHoursMinutes(0, 00))))
				.map(startTime -> startTime.getHour())
				.collect(Collectors.toSet());

	}

	public boolean isAvailable(LocalDateTime day) {

		OffsetDateTime searchDate = day.atOffset(ZoneOffset.ofHoursMinutes(0, 00));
		return dslContext.selectFrom(APPOINTMENT)
				.where(hour(APPOINTMENT.START_DATETIME_AT).eq(searchDate.getHour()))
				.fetch().isEmpty();

	}

	public boolean saveAppointment(LocalDateTime day, String services, String email, String userPhone, String status, String place) {
		int numberOfRowInsert = dslContext.insertInto(APPOINTMENT)
				.set(APPOINTMENT.ID, UUID.randomUUID())
				.set(APPOINTMENT.START_DATETIME_AT, day.atOffset(ZoneOffset.ofHoursMinutes(0, 00)) )
				.set(APPOINTMENT.END_DATETIME_AT,day.plusHours(DURATION_OF_APPOINTMENT).atOffset(ZoneOffset.ofHoursMinutes(0, 00)))
				.set(APPOINTMENT.USER_EMAIL, email)
				.set(APPOINTMENT.USER_PHONE, userPhone)
				.set(APPOINTMENT.SERVICES, services)
				.set(APPOINTMENT.STATUS, status)
				.set(APPOINTMENT.PLACE, place)
				.execute();

		log.info("Save a new appointment");
		return numberOfRowInsert == 1;
	}

	public List<DashboardData> findAll() {
		return dslContext.selectFrom(APPOINTMENT)
				.fetch()
				.map(this::mapToDto);
	}

	private DashboardData mapToDto(AppointmentRecord appointmentRecord) {
		String dateFormat = "dd-MM-YYYY HH:mm";

		return new DashboardData(appointmentRecord.getStartDatetimeAt().atZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.ofHoursMinutes(0, 00))).format(DateTimeFormatter.ofPattern(dateFormat)),
				appointmentRecord.getEndDatetimeAt().atZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.ofHoursMinutes(0, 00))).format(DateTimeFormatter.ofPattern(dateFormat)),
				appointmentRecord.getUserPhone(),
				appointmentRecord.getUserEmail(),
				appointmentRecord.getServices(),
				appointmentRecord.getStatus(),
				appointmentRecord.getPlace());

	}
}
