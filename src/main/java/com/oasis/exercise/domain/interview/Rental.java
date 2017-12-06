package com.oasis.exercise.domain.interview;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

public abstract class Rental {
	private double price;
	
	private LocalDateTime from = null;
	private LocalDateTime to = null;
	
	public Rental() {
	}
	
	public Rental(double price) {
		this.price = price;
	}
	
	public Rental(double price, LocalDateTime from) throws Exception {
		this(price);
		startRental(from);
	}
	
	public Rental(LocalDateTime from) throws Exception {
		startRental(from);
	}
	
	public void startRental(LocalDateTime from) throws Exception {
		if (this.from == null) {
			this.from = from;
		}
	}
	
	public void startRental() throws Exception {
		startRental(LocalDateTime.now());
	}
	
	public void stopRental(LocalDateTime to) throws Exception {
		if (this.from == null) {
			throw new Exception("Rental Not Initialized");
		}
		
		if (this.to != null) {
			throw new Exception("Rental Already Finalized");
		}
		
		this.to = to;
	}
	
	public void stopRental() throws Exception {
		stopRental(LocalDateTime.now());
	}
	
	public void restart() throws Exception {
		stopRental();
		startRental();
	}
	
	public LocalDateTime getFrom() {
		return from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public double getPrice() {
		return this.price;
	}
	
	public double getPartial() throws Exception {	
		return getPartial(0.0);
	}
	
	public double getPartial(double discount) throws Exception {		
		return getPartial(discount, null);
	} 
	
	public double getPartial(double discount, LocalDateTime to) throws Exception {
		if (this.from == null) {
			throw new Exception("Rental Not Initialized");
		}
			
		return getPrice(discount, to);
	} 
	
	public double getInvoice() throws Exception {
		return getInvoice(1.0);
	}
	
	public double getInvoice(double discount) throws Exception {
		if (this.from == null ||
			this.to == null) {
			throw new Exception("Rental times NOT Set");
		}
		
		return getPrice(discount, this.to);
	}
	
	protected double getPrice(double discount, TemporalUnit unit) throws Exception {
		return getPrice(discount, unit, null);
	}
	
	protected double getPrice(double discount, TemporalUnit unit, LocalDateTime to) throws Exception {
		LocalDateTime tempDate = LocalDateTime.from(this.from);
		long amount = tempDate.until((to == null ? this.to :  to), unit);
		
		return (price * (amount + 1) * (1.0 - discount));
	}
	
	public abstract double getPrice(double discount, LocalDateTime to) throws Exception;
}
