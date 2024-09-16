package com.swiftrails.SWIFTRAILS.services;


import java.util.List; // Import List interface

import org.springframework.stereotype.Service;

import com.swiftrails.SWIFTRAILS.entities.Stations;



public interface StationService {
	
	public Stations findById(String routeId);
	
	public Stations Getbyorg_dest(String origin, String destination);
		
	public List<Stations> getAllStations();
	
}