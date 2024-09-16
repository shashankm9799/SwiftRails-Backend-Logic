package com.swiftrails.SWIFTRAILS.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiftrails.SWIFTRAILS.entities.User;
import com.swiftrails.SWIFTRAILS.entities.Booking;
import com.swiftrails.SWIFTRAILS.entities.Stations;
import com.swiftrails.SWIFTRAILS.impl.TrainsSceduleAPIServiceImpl;
import com.swiftrails.SWIFTRAILS.impl.UserServiceImpl;
import com.swiftrails.SWIFTRAILS.services.EmailSenderService;
import com.swiftrails.SWIFTRAILS.impl.BookingServiceImpl;
import com.swiftrails.SWIFTRAILS.impl.StationServiceImpl;
import com.swiftrails.SWIFTRAILS.dto.TaxResponse;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

	@Autowired
	public UserServiceImpl userservice;
	@Autowired
	public BookingServiceImpl bookingservice;
	@Autowired
	public StationServiceImpl stationservice;
	@Autowired
	public EmailSenderService emailsenderservice;

	@Autowired
	public TrainsSceduleAPIServiceImpl trainssceduleapiserviceimpl;

	@GetMapping("/")
	public ResponseEntity<String> visited() {
		return ResponseEntity.ok("hi");
	}

	@PostMapping("/registerUser")
	public ResponseEntity<String> registerUser(@RequestParam String email, @RequestParam String password,
			@RequestParam String firstName, @RequestParam String lastName, @RequestParam String address,
			@RequestParam String phoneNumber) {
		String res = userservice.registerUser(email,password,firstName,lastName,address,phoneNumber);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/findEmail")
	public User findEmail(@RequestParam String email) {
		User u = userservice.findByEmail(email);
		return u;
	}

	@PostMapping("/validateOTP")
	public ResponseEntity<String> validateOTP(@RequestParam String email,@RequestParam String otp) {
//		String email = user.getEmail();
//		String otp = user.getOtp();
		try {
			String result = userservice.validateUser(email, otp);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		}
	}

	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam String email) {
		String res = userservice.deleteUser(email);
		return ResponseEntity.ok(res);
	}

	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestParam String email, @RequestParam String password,
			@RequestParam String firstName, @RequestParam String lastName, @RequestParam String address,
			@RequestParam String phoneNumber) {
		String res = userservice.updateUser(email, password, firstName, lastName, address, phoneNumber);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/stationCodes")
	public String fetchTrainStationsCodes(@RequestParam String fromStationCode, @RequestParam String toStationCode,
			@RequestParam String dateOfJourney) {
		String stationCodes = trainssceduleapiserviceimpl.fetchTrainStationsCodes(fromStationCode, toStationCode,
				dateOfJourney);

		return stationCodes;
	}

	@PostMapping("/getRoutes")
	public List<Stations> fetchAllRoutes() {
		List<Stations> routes = stationservice.getAllStations();
		return routes;
	}

	@PostMapping("/bookTicket")
	public String bookTicket(@RequestParam String userEmail, @RequestParam String origin,
			@RequestParam String destination, @RequestParam String startDate, @RequestParam String returnDate,
			@RequestParam String DiscountType, @RequestParam String DiscountCode) {
		System.out.println("started");
		Stations route1 = stationservice.Getbyorg_dest(origin, destination);
		Stations route2 = stationservice.Getbyorg_dest(destination, origin);

		int price = 0;
		int tax = 0;

		if (route1 == null && route2 == null) {
			return "Failed to add booking 1";
		}
		if (route1 == null) {
			return "Failed to add booking 2";
		}
		price += route1.getPrice();
		tax += route1.getTax();
		if (route2 != null) {
			price += route2.getPrice();
			tax += route2.getTax();
		}
		Booking booking = new Booking();
		booking.setUserEmail(userEmail);
		booking.setOrigin(origin);
		booking.setDestination(destination);
		booking.setToDate(startDate);
		booking.setFroDate(returnDate);
		booking.setPrice(price);
		booking.setTax(tax);

		String s = bookingservice.addBooking(booking);

		return s;

	}

	@PostMapping("/CancelTicket")
	public String CancelTicket(@RequestParam Long bookingId) {
		Booking booking = bookingservice.cancelBooking(bookingId);
		String userEmail = booking.getUserEmail();
		User user = userservice.findByEmail(userEmail);
		System.out.println(userEmail);
		String text = "Dear " + user.getFname() + " " + user.getLname() + " , \n" + "Your Booking with BookingId "
				+ booking.getId() + " has been Cancelled \n A refund of " + booking.getPrice()
				+ " has been debited to your account with enging digits 1234";
		try {
			emailsenderservice.sendEmail(userEmail, "Cancellation of SWIFTRAILS ticket", text);
			return "Your booking has been succesfully cancelled";
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "something went wrong";
		}
	}

	@PostMapping("/taxation")
	public TaxResponse sendTaxes(@RequestParam int price) {

		TaxResponse res = new TaxResponse();

		res.salesTax = bookingservice.salesTax(price);
		res.serviceTax = bookingservice.serviceTax(price);
		res.totalPrice = price + res.salesTax + res.serviceTax;

		return res;
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestParam String email,@RequestParam String password) {
//	    String email = request.get("email");
//	    String password = request.get("password");
	    User u = userservice.loginUser(email, password);
	    if (u != null) {
	        return ResponseEntity.ok(u);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }
	}
	
	@PostMapping("/promotions")
	public String discount(@RequestParam String mailId,@RequestParam String promotionCode, @RequestParam int amount) {
		String amountAfterDiscount=userservice.calculateDiscount(mailId,promotionCode,amount);
		return amountAfterDiscount;
	}
	
	@GetMapping("/getLoylaty")
	public int getLoyalty(@RequestParam String mailId) {
		int result=userservice.displayLoyalty(mailId);
		return result;
	}

}
