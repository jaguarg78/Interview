package com.oasis.exercise.domain.interview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Interview {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		
		try {
			properties.load(Interview.class.getResourceAsStream("/properties"));
			
			System.out.println(properties.get("rental.price.by.hour"));
			System.out.println(properties.get("rental.price.by.day"));
			System.out.println(properties.get("rental.price.by.week"));
			System.out.println(properties.get("rental.price.promotional.discuount"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
