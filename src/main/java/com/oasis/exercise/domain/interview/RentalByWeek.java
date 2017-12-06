package com.oasis.exercise.domain.interview;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RentalByWeek extends Rental {
	public RentalByWeek(double price) {
		super(price);
	}
	
	public RentalByWeek(double price, LocalDateTime from) throws Exception {
		super(price, from);
	}
	
	@Override
	public double getPrice(double discount, LocalDateTime to) throws Exception {
		return getPrice(discount, ChronoUnit.WEEKS, to);
	}
}
