package com.swiftrails.SWIFTRAILS.services;


import org.springframework.stereotype.Service;

import com.swiftrails.SWIFTRAILS.entities.Booking;



public interface BookingService {
	
	public Float salesTax(int price);
	
	public Float serviceTax(int price);
	
	public Booking findById(String bookingId);
	
	public String addBooking(Booking booking);
	
	public Booking cancelBooking(Long bookingId);

}