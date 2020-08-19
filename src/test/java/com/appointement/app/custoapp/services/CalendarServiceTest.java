package com.appointement.app.custoapp.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.appointement.app.custoapp.beans.AvailableHour;
import com.appointement.app.custoapp.services.db.AppointmentDB;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalendarServiceTest {

	@Mock
	private AppointmentDB appointmentDB;

	@InjectMocks
	private CalendarService calendarService;

	@Test
	public void isShouldReturnTheAvailableHourForGivenDay() {

		String date = "2020-02-15";
		Set<Integer> notAvailableAppointmentStartHour = Set.of(9,15);
		when(appointmentDB.findBookedHourByDay(LocalDateTime.of(2020,02,15,0,0,0)))
				.thenReturn(notAvailableAppointmentStartHour);

		List<AvailableHour> actualAvailableHours = calendarService.getAvailableHour(date);

		assertThat(actualAvailableHours, hasSize(8));
		assertThat(actualAvailableHours, contains(new AvailableHour(8, 9), new AvailableHour(10, 11),
				new AvailableHour(11, 12), new AvailableHour(12, 13), new AvailableHour(13, 14),
				new AvailableHour(14, 15), new AvailableHour(16, 17), new AvailableHour(17, 18)));


	}

}
