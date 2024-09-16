package com.swiftrails.SWIFTRAILS.services;

public interface TrainsSceduleAPIService {

	String fetchTrainStationsCodes(String fromStationCode, String toStationCode, String dateOfJourney);
	
}