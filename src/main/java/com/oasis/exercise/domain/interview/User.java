package com.oasis.exercise.domain.interview;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String name;
	private String id;
	
	protected boolean isPrincipal = false;
	
	private List<Rental> rentals = new ArrayList<>();
	
	public User(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public boolean isPrincipal() {
		return this.isPrincipal;
	}
	
	public void addRental(Rental rental) {
		rentals.add(rental);
	}
	
	public void stopRental(int id) throws Exception {
		rentals.get(id).stopRental();
	}
	
	public void stopRentals() throws Exception {
		for (Rental rental : rentals) {
			rental.stopRental();
		}
	}
	
	public void startRental(int id) throws Exception {
		rentals.get(id).startRental();
	}
	
	public void startRentals() throws Exception {
		for (Rental rental : rentals) {
			rental.startRental();
		}
	}
	
	public double getInvoice(int id) throws Exception {
		return rentals.get(id).getInvoice();
	}
	
	public double getInvoices() throws Exception {
		double acc = 0.0;
		for (Rental rental : rentals) {
			acc += rental.getInvoice();
			System.out.println(acc);
		}
		
		return acc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
