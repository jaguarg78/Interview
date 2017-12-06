package com.oasis.exercise.domain.interview;

import java.util.List;

public class Principal extends User {
	private List<User> relatives = null;
	
	public Principal(String name, String id) {
		super(name, id);
		this.isPrincipal = true;
	}
	
	public Principal(String name, String id, List<User> relatives) throws Exception {
		this(name, id);
		
		if (!areRelativesOk(relatives)) {
			throw new Exception("Relatives are NOT OK");
		}
		
		this.relatives = relatives;
	}
	
	public void addRelative(User relative) throws Exception {
		if (relative.isPrincipal()) {
			throw new Exception("Relative is Principal");
		}
		
		relatives.add(relative);
	}
	
	public void addRelatives(List<User> relatives) throws Exception {
		if (!areRelativesOk(relatives)) {
			throw new Exception("Relatives are NOT OK");
		}
		
		this.relatives = relatives;
	}
	
	public void resetRelatives() {
		this.relatives.clear();
	}
	
	public int relativeCount() {
		return this.relatives.size();
	}
	
	public boolean areRelativesOk(List<User> relatives) {
		for (User user : relatives) {
			if (user.isPrincipal()) {
				return false;
			}
		}
		
		return true;
	}
	
	public void stopRental(int userId, int id) throws Exception {
		relatives.get(userId).stopRental(id);
	}
	
	public void stopRentals(int userId) throws Exception {
		relatives.get(userId).stopRentals();
	}
}
