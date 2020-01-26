package model.services;

import entities.CarRental;
import entities.Invoice;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	
	private TaxService taxService;  // in the main program, the specific fee method will override that.

	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		super();
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	
	public void processInvoice(CarRental carRental) {
		
		long t1 = carRental.getStart().getTime();  // getTime = value in miliseconds
		long t2 = carRental.getFinish().getTime();
		
		double hours = (double)(t2-t1) / 1000 / 60 /60;
		
		double basicPayment;
		if (hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		
		double tax = taxService.tax(basicPayment);
		
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
		
	}
	
}
