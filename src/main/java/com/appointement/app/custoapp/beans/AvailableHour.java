package com.appointement.app.custoapp.beans;

import java.util.Objects;

public class AvailableHour {

	private int startHour;
	private int endHour;

	public AvailableHour(int startHour, int endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}

	public int getStartHour() {
		return startHour;
	}

	@Override
	public String toString() {
		if(startHour < 10) return String.format("0%d:00", startHour);
		return String.format("%d:00", startHour);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AvailableHour that = (AvailableHour) o;
		return startHour == that.startHour &&
				endHour == that.endHour;
	}

	@Override
	public int hashCode() {
		return Objects.hash(startHour, endHour);
	}
}
