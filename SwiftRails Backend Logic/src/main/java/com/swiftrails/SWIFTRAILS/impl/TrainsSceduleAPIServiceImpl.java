package com.swiftrails.SWIFTRAILS.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.swiftrails.SWIFTRAILS.services.TrainsSceduleAPIService;

/**
 * 
 * @author monil
 * 
 *         Fetching the train schedule and other details from the API
 * 
 */
@Service
public class TrainsSceduleAPIServiceImpl implements TrainsSceduleAPIService {

	@Override
	public String fetchTrainStationsCodes(@RequestParam String fromStationCode, @RequestParam String toStationCode,
			@RequestParam String dateOfJourney) {
		// Encode the parameters to ensure they are properly formatted for the URL
		String encodedFromStationCode = URLEncoder.encode(fromStationCode, StandardCharsets.UTF_8);
		String encodedToStationCode = URLEncoder.encode(toStationCode, StandardCharsets.UTF_8);
		String encodedDateOfJourney = URLEncoder.encode(dateOfJourney, StandardCharsets.UTF_8);

		// Construct the URL with encoded query parameters
		String baseUrl = "https://irctc1.p.rapidapi.com/api/v3/trainBetweenStations";
		String urlWithParams = String.format("%s?fromStationCode=%s&toStationCode=%s&dateOfJourney=%s", baseUrl,
				encodedFromStationCode, encodedToStationCode, encodedDateOfJourney);

		// Create an HTTP request
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlWithParams))
				.header("content-type", "application/json")
				.header("X-RapidAPI-Key", "9c3d4791b7msh90e4a48418ed2bdp19eb4cjsn3cf7c7734ff4")
				.header("X-RapidAPI-Host", "irctc1.p.rapidapi.com").method("GET", HttpRequest.BodyPublishers.noBody())
				.build();

		// Send the HTTP request and handle the response
		try {
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch (IOException | InterruptedException e) {
			System.out.println("IOException | InterruptedException: " + e);
			return null;
		}
	}

}
