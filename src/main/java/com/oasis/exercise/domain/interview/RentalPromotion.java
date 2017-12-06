package com.oasis.exercise.domain.interview;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RentalPromotion extends Rental {
	private List<Rental> rentals = new ArrayList<>();
	
	public RentalPromotion() throws Exception {
		super();
	}
	
	public RentalPromotion(LocalDateTime from) throws Exception {
		super(from);
	}
	
	public RentalPromotion(List<Rental> rentals) throws Exception {
		super();
		addRentals(rentals);
	}

	public void addRental(Rental rental) throws Exception {
		if (rentals.size() == 5) {
			throw new Exception("Promotion Rentals exceeded");
		}
		
		rentals.add(rental);
	}
	
	public void removeRental(int index) {
		rentals.remove(index);
	}
	
	public List<Rental> addRentals(List<Rental> rentals) {
		if (rentals.size() < 3) {
			return rentals;
		}
		
		Collections.sort(rentals,
				         (rental1, rental2) -> {
				        	 return Double.compare(rental1.getPrice(), rental2.getPrice());
				         });
		
		for (int i = 0; (i < (rentals.size() + i) && i < 5); i++) {
			this.rentals.add(rentals.remove(0));
		}
		
		rentals.add(this);
		
		return rentals;
	}
	
	@Override
	public void startRental(LocalDateTime from) throws Exception {
		for (Rental rental : rentals) {
			rental.startRental();
		}
	}
	
	@Override
	public void stopRental(LocalDateTime from) throws Exception {
		for (Rental rental : rentals) {
			rental.stopRental();
		}
	}
	
	@Override
	public double getInvoice(double discount) throws Exception {
		double acc = 0.0;
		for (Rental rental : rentals) {
			acc += rental.getInvoice(discount);
		}
		
		return acc;
	}
	
	@Override
	public double getPrice(double discount, LocalDateTime to) throws Exception {
		double acc = 0.0;
		for (Rental rental : rentals) {
			acc += rental.getPrice(discount, to);
		}
		
		return acc;
	}
}
