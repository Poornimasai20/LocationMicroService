package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.Response.Flight;
import com.project.Response.ResponseTemplate;
import com.project.model.Location;
import com.project.repository.LocationRepository;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private RestTemplate restTemplate;
	
	public Location addLocation(Location loc)
	{
		return locationRepository.save(loc);
	}
	
	
	public  List<Location> getLocationDetails()
	{
		return  (List<Location>) locationRepository.findAll();
	}
	
	public ResponseTemplate getLocationById(int locationId) {
		
		ResponseTemplate vo = new ResponseTemplate();
		
		Location location = locationRepository.findByLocationId(locationId);

		Flight flight = restTemplate.getForObject(
				"http://FLIGHT-MICROSERVICE/flight/get-flight-by-id/" + location.getFlightId(), Flight.class);
		vo.setLocation(location);
		vo.setFlight(flight);

		return vo;
	}
	
	  public Optional<Location> fetchLocationById(int locationId) { return
			  locationRepository.findById(locationId); }
	 
	public Location updateLocation(Location loc)
	{
		return locationRepository.save(loc);	
	}
	public String deleteLocation(int locationId)
	{
		locationRepository.deleteById(locationId);
	  return "the Location id"+locationId+"is deleted succesufully";
		
		
	}  
	public String deleteAllLocationDetails()
	{
		locationRepository.deleteAll();
		return "All Location Details are Deleted ";

	}

}
