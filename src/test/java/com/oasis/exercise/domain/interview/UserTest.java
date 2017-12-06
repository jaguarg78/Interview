package com.oasis.exercise.domain.interview;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class UserTest {
	private static Properties properties = null;
	private User user1 = new User("Gianni", "12000000");      
	private User user2 = new User("Alberto", "12000001"); 
	private User user3 = new User("Agudelo", "12000002");
	private User user4 = new User("Arbelaez", "12000003");
	private Principal principal1 = new Principal("Astor", "12000004");
	private Principal principal2 = new Principal("Pantaleon", "12000005");
	
	private static double priceByHour;
	private static double priceByDay;
	private static double priceByWeek;
	private static double discount;
			
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	// Se cargan los valores que están en el archivo de Properties
	@BeforeClass           
	public static void setProperties() {
		properties = new Properties();
		try {
			properties.load(UserTest.class.getResourceAsStream("/properties"));
			priceByHour = Double.parseDouble(properties.getProperty("rental.price.by.hour"));
			priceByDay = Double.parseDouble(properties.getProperty("rental.price.by.day"));
			priceByWeek = Double.parseDouble(properties.getProperty("rental.price.by.week"));
			discount = Double.parseDouble(properties.getProperty("rental.price.promotional.discuount"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Se validan los valores que se obtienen del archivo de Properties
	@Test
	public void propertiesTest() {
		assertEquals(3.0, priceByHour, 0.01);
		assertEquals(5.0, priceByDay, 0.01);
		assertEquals(12.0, priceByWeek, 0.01);
		assertEquals(0.3, discount, 0.01);
	}
	
	// Se valida el comportamiento si no se ha iniciado la renta
	@Test
	public void RentalsStartExceptionsShouldBeThrown() throws Exception {
		long delta = 0; 
		RentalByHour rentHour = new RentalByHour(priceByHour);
		RentalByDay rentDay = new RentalByDay(priceByDay);
		RentalByWeek rentWeek = new RentalByWeek(priceByWeek);
		
		thrown.expect(Exception.class);
		thrown.expectMessage("Rental Not Initialized");
		rentHour.getPartial(0.0, LocalDateTime.now().plusHours(delta));
		rentDay.getPartial(0.0, LocalDateTime.now().plusDays(delta));
		rentWeek.getPartial(0.0, LocalDateTime.now().plusWeeks(delta));
		rentHour.getInvoice(0.0);
		rentDay.getInvoice(0.0);
		rentWeek.getInvoice(0.0);
	}
	
	// Se valida el comportamiento si no se ha terminado la renta
	@Test
	public void RentalsIStopExceptionsNoStartShouldBeThrown() throws Exception {
		long delta = 0; 
		RentalByHour rentHour = new RentalByHour(priceByHour);
		RentalByDay rentDay = new RentalByDay(priceByDay);
		RentalByWeek rentWeek = new RentalByWeek(priceByWeek);
		
		thrown.expect(Exception.class);
		thrown.expectMessage("Rental times NOT Set");
		rentHour.getInvoice(0.0);
		rentDay.getInvoice(0.0);
		rentWeek.getInvoice(0.0);
	}

	@Test
	public void RentalsIStopExceptionsNoStopShouldBeThrown() throws Exception {
		long delta = 0; 
		RentalByHour rentHour = new RentalByHour(priceByHour);
		RentalByDay rentDay = new RentalByDay(priceByDay);
		RentalByWeek rentWeek = new RentalByWeek(priceByWeek);

		rentHour.startRental();
		rentDay.startRental();
		rentWeek.startRental();
		
		thrown.expect(Exception.class);
		thrown.expectMessage("Rental times NOT Set");
		rentHour.getInvoice(0.0);
		rentDay.getInvoice(0.0);
		rentWeek.getInvoice(0.0);
	}
	
	// Validación de valores parciales
	@Test
	public void RentalsShouldReturnCorrectPartialValues() throws Exception {
		long delta = 0; 
		RentalByHour rentHour = new RentalByHour(priceByHour);
		RentalByDay rentDay = new RentalByDay(priceByDay);
		RentalByWeek rentWeek = new RentalByWeek(priceByWeek);
		
		rentHour.startRental();
		rentDay.startRental();
		rentWeek.startRental();
		
		for (int i = 0; i < 5; i++) {
			delta += i;
			assertEquals((priceByHour * (delta + 1)), rentHour.getPartial(0.0, LocalDateTime.now().plusHours(delta)), 0.01);
			assertEquals((priceByDay * (delta + 1)), rentDay.getPartial(0.0, LocalDateTime.now().plusDays(delta)), 0.01);
			assertEquals((priceByWeek * (delta + 1)), rentWeek.getPartial(0.0, LocalDateTime.now().plusWeeks(delta)), 0.01);
		}
	}
	
	// Validación de valores finales
	@Test
	public void RentalsShouldReturnCorrectInvoiceValues() throws Exception {
		long delta = 0; 
		RentalByHour rentHour = new RentalByHour(priceByHour);
		RentalByDay rentDay = new RentalByDay(priceByDay);
		RentalByWeek rentWeek = new RentalByWeek(priceByWeek);
		
		rentHour.startRental();
		rentDay.startRental();
		rentWeek.startRental();
		rentHour.stopRental();
		rentDay.stopRental();
		rentWeek.stopRental();
		
		assertEquals((priceByHour * (delta + 1)), rentHour.getInvoice(0.0), 0.01);
		assertEquals((priceByDay * (delta + 1)), rentDay.getPartial(0.0), 0.01);
		assertEquals((priceByWeek * (delta + 1)), rentWeek.getPartial(0.0), 0.01);
	}
	
	// Creación de Usuario Principal incluyendole otro Principal 
	@Test
	public void PrincipalShouldNotHavePrincipalRelatives() throws Exception {
		List<User> relatives = new ArrayList<>();
		relatives.add(user1);
		relatives.add(user2);
		relatives.add(user3);
		relatives.add(user4);
		
		for (User user : relatives) {
			assertFalse(user.isPrincipal());
		}
		
		principal2.addRelatives(relatives);
		
		relatives.add(principal1);
		thrown.expect(Exception.class);
		thrown.expectMessage("Relatives are NOT OK");
		principal2.addRelatives(relatives);
	}
	
	// Se intenta agregar mas de 5 rentas a una promoción
	@Test
	public void PromotionAddRentalExceptionExceededTest() throws Exception {
		RentalPromotion rentalPromotion = new RentalPromotion();
		rentalPromotion.addRental(new RentalByHour(priceByHour));
		rentalPromotion.addRental(new RentalByHour(priceByHour));
		rentalPromotion.addRental(new RentalByHour(priceByHour));
		rentalPromotion.addRental(new RentalByHour(priceByHour));
		rentalPromotion.addRental(new RentalByDay(priceByDay));
		
		thrown.expect(Exception.class);
		thrown.expectMessage("Promotion Rentals exceeded");
		rentalPromotion.addRental(new RentalByWeek(priceByWeek));
	}
	
	// Se calculan los invoice de una Promoción que utiliza todos los tipos de renta
	// Por lo que tambien sirve para validar la generación del valor final para una renta
	// de cualquier otro tipo.
	@Test
	public void PromotionRentalShouldGiveCorrectValuesTest() throws Exception {
		RentalPromotion rentalPromotion = new RentalPromotion();
		rentalPromotion.addRental(new RentalByHour(priceByHour));
		rentalPromotion.addRental(new RentalByHour(priceByHour));
		rentalPromotion.addRental(new RentalByHour(priceByHour));
		rentalPromotion.addRental(new RentalByWeek(priceByWeek));
		rentalPromotion.addRental(new RentalByDay(priceByDay));
		
		rentalPromotion.startRental();
		rentalPromotion.stopRental();
		
		// (3 + 3 + 3 + 12 + 5) * 0.7 = 18.2
		double expected = ((3 * (priceByHour)) +  priceByWeek + priceByDay) * (1.0 - discount);
		assertEquals(expected, rentalPromotion.getInvoice(discount), 0.01);
	}
}
