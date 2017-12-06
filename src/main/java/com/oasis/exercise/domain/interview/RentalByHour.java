package com.oasis.exercise.domain.interview;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RentalByHour extends Rental {
	public RentalByHour(double price) {
		super(price);
	}
	
	public RentalByHour(double price, LocalDateTime from) throws Exception {
		super(from);
	}

	@Override
	public double getPrice(double discount, LocalDateTime to) throws Exception {
		return getPrice(discount, ChronoUnit.HOURS, to);
	}
}
