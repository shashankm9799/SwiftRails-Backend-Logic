package com.swiftrails.SWIFTRAILS.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import com.swiftrails.SWIFTRAILS.DBConnection.DBconnection;
import com.swiftrails.SWIFTRAILS.entities.User;
import com.swiftrails.SWIFTRAILS.entities.Booking;
import com.swiftrails.SWIFTRAILS.entities.Stations;
import com.swiftrails.SWIFTRAILS.services.EmailSenderService;
import com.swiftrails.SWIFTRAILS.services.StationService;
import javax.mail.MessagingException;
import org.springframework.context.event.EventListener;

@Service
public class StationServiceImpl implements StationService{
	
	@Autowired
	public DBconnection dbconnection;

	@Override
    public Stations findById(String routeId) {
		Stations u = new Stations();
		String query = "SELECT * FROM stations WHERE id=?";
		try {
			Connection con = dbconnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, routeId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				u.setOrigin(rs.getString("origin"));
				u.setDestination(rs.getString("destination"));
				u.setPrice(rs.getInt("price"));
				u.setTax(rs.getInt("tax"));
			} else {
				throw new Exception("Route with id " + routeId + " not found");
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	@Override
	public Stations Getbyorg_dest(String origin, String destination) {
	    Stations u = new Stations();
	    String query = "SELECT * FROM stations WHERE origin=? AND destination=?";
	    try {
	        Connection con = dbconnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, origin);
	        ps.setString(2, destination);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            u.setOrigin(rs.getString("origin"));
	            u.setDestination(rs.getString("destination"));
	            u.setPrice(rs.getInt("price"));
	            u.setTax(rs.getInt("tax"));
	        } else {
	            throw new Exception("Route with origin " + origin + " and destination " + destination + " not found");
	        }
	        ps.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return u;
	}

	@Override
	public List<Stations> getAllStations() {
	    List<Stations> stations = new ArrayList<>();
	    String query = "SELECT * FROM stations";
	    try {
	        Connection con = dbconnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(query);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Stations u = new Stations();
	            u.setId(rs.getLong("id"));
	            u.setOrigin(rs.getString("origin"));
	            u.setDestination(rs.getString("destination"));
	            u.setPrice(rs.getInt("price"));
	            u.setTax(rs.getInt("tax"));
	            stations.add(u);
	        }
	        ps.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return stations;
	}


    
	
	
}