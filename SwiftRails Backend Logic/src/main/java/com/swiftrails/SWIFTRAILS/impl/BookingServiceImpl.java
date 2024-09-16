package com.swiftrails.SWIFTRAILS.impl;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import com.swiftrails.SWIFTRAILS.DBConnection.DBconnection;
import com.swiftrails.SWIFTRAILS.entities.User;
import com.swiftrails.SWIFTRAILS.entities.Booking;
import com.swiftrails.SWIFTRAILS.services.EmailSenderService;
import com.swiftrails.SWIFTRAILS.services.BookingService;
import javax.mail.MessagingException;
import javax.swing.text.DateFormatter;

import org.springframework.context.event.EventListener;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	public DBconnection dbconnection;
	
	public Float salesTax(int price) {
		return (float) price*5/100;
	}
	
	public Float serviceTax(int price) {
		return (float) price*10/100;
	}

	@Override
	public Booking findById(String bookingId) {
        Booking booking = new Booking();
        String query = "SELECT * FROM bookings WHERE id=?";
        try {
            Connection con = dbconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	
                booking.setId(rs.getLong("id"));
                booking.setOrigin(rs.getString("origin"));
                booking.setDestination(rs.getString("destination"));
//                booking.setToDate(rs.getTimestamp("to_date").toLocalDateTime());
//                booking.setFroDate(rs.getTimestamp("fro_date").toLocalDateTime());
                booking.setPrice(rs.getInt("price"));
                booking.setTax(rs.getInt("tax"));
                booking.setCancelled(rs.getBoolean("is_cancelled"));
            } else {
                throw new Exception("Booking with id " + bookingId + " not found");
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booking;
    }

	@Override
    public String addBooking(Booking booking) {
        String query = "INSERT INTO bookings (origin, destination, toDate, froDate, price, tax, is_cancelled,userEmail) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try {
            Connection con = dbconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            Date todate = new SimpleDateFormat("yyyy-mm-dd").parse(booking.getToDate());
            Date frodate = new SimpleDateFormat("yyyy-mm-dd").parse(booking.getFroDate());
//            try {
//            	
//            }
//            catch(NullPointerException e) {
//            	System.out.println(e);
//            }
            
            
            ps.setString(1, booking.getOrigin());
            ps.setString(2, booking.getDestination());
            ps.setDate(3,new java.sql.Date(todate.getTime()));
            ps.setDate(4,new java.sql.Date(frodate.getTime()));
            ps.setInt(5, booking.getPrice());
            ps.setInt(6, booking.getTax());
            ps.setBoolean(7, booking.isCancelled());
            ps.setString(8, booking.getUserEmail());
            ps.executeUpdate();
            ps.close();
        }
        catch(NullPointerException e) {
        	System.out.println(e);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Failed to add booking";
        }
        return "Booking added successfully";
    }

	@Override
	public Booking cancelBooking(Long bookingId) {
	    Booking booking = null;
	    String selectQuery = "SELECT * FROM bookings WHERE id = ?";
	    String updateQuery = "UPDATE bookings SET is_cancelled = ? WHERE id = ?";
	    try {
	        Connection con = dbconnection.getConnection();
	        // Retrieve the booking details
	        PreparedStatement selectPs = con.prepareStatement(selectQuery);
	        selectPs.setLong(1, bookingId);
	        ResultSet rs = selectPs.executeQuery();
	        if (rs.next()) {
	            booking = new Booking();
	            booking.setId(rs.getLong("id"));
	            booking.setOrigin(rs.getString("origin"));
	            booking.setDestination(rs.getString("destination"));
	            booking.setUserEmail(rs.getString("userEmail"));
//	            booking.setToDate(rs.getTimestamp("toDate").toLocalDateTime());
//	            booking.setFroDate(rs.getTimestamp("froDate").toLocalDateTime());
	            booking.setPrice(rs.getInt("price"));
	            booking.setTax(rs.getInt("tax"));
	            booking.setCancelled(rs.getBoolean("is_cancelled"));
	        } else {
	            throw new Exception("Booking with id " + bookingId + " not found");
	        }
	        selectPs.close();

	        // Update the booking to set it as cancelled
	        if (booking != null) {
	            PreparedStatement updatePs = con.prepareStatement(updateQuery);
	            updatePs.setBoolean(1, true);
	            updatePs.setLong(2, bookingId);
	            int rowsAffected = updatePs.executeUpdate();
	            updatePs.close();

	            if (rowsAffected > 0) {
	                booking.setCancelled(true); // Update the booking object to reflect the cancellation
	            } else {
	                throw new Exception("Failed to cancel booking with id " + bookingId);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return booking; // Return the updated booking object
	}
	
}