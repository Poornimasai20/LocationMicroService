package com.flight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flight.VO.User;
import com.flight.exception.InvalidFormatException;
import com.flight.exception.InvalidUserException;
import com.flight.exception.NoDataFoundException;
import com.flight.exception.NoSuchFlightIdException;
import com.flight.model.Flight;
import com.flight.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {

	private static final String PATTERN = "[a-zA-Z\\s?]{2,}";
	private static final String FLIGHT_MODEL_PATTERN = "[a-zA-Z0-9-\\s?]{2,}";
	
	@Autowired
	FlightService flightService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/get-all-flights")
	public ResponseEntity<List<Flight>> getAllFlights(){
		if(flightService.getAllFlightDetails().isEmpty()) {
			throw new NoDataFoundException("There are no records to view the flight details.");
		}
		return new ResponseEntity<>(flightService.getAllFlightDetails(),HttpStatus.OK);
	}
	
	//This path is used while booking the flight to get the flight details based flight ID
	@GetMapping("/get-flight-by-id/{flightId}")
	public ResponseEntity<Flight> getFlightById(
			@PathVariable("flightId") int flightId)
	{
		Flight flight = flightService.getFlightByFlightId(flightId);
		if(flight==null) {
			throw new NoSuchFlightIdException("There is no flight with flight ID "+flightId);
		}
		return new ResponseEntity<>(flight,HttpStatus.OK);
	}
	
	//This path is only used by the admins to fetch the flight details based on the flight ID
	@GetMapping("/get-flight-by-id-admin/{flightId}")
	public ResponseEntity<Flight> getFlightByIdAdmin(
			@PathVariable("flightId") int flightId){
		Flight flight = flightService.getFlightByFlightId(flightId);
		if(flight==null) {
			throw new NoSuchFlightIdException("There is no flight with flight ID "+flightId);
		}
		
		return new ResponseEntity<>(flight,HttpStatus.OK);
	}
	
	@PostMapping("/add-one-flight")
	public ResponseEntity<String> addOneFlight(
			@RequestBody Flight flight)
		
	{
		if(!flight.getArrivalLoc().matches(PATTERN)||
		   !flight.getDepartureLoc().matches(PATTERN)||
		   !flight.getFleetName().matches(PATTERN)||
		   !flight.getModel().matches(FLIGHT_MODEL_PATTERN)) {
			throw new InvalidFormatException("Invalid format, please enter again.");
		}
		return new ResponseEntity<>(flightService.addOneFlight(flight),HttpStatus.OK);
	}
	

	@PutMapping("/update-flight")
	public ResponseEntity<Flight> updateFlight(
			@RequestBody Flight flight){
		return new ResponseEntity<>(flightService.updateFlightDetail(flight),HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/delete-flight-by-id/{flightId}")
	public ResponseEntity<String> deleteFlightByid(
			@PathVariable("flightId") int flightId)
	{
			
		Flight flight = flightService.getFlightByFlightId(flightId);
		if(flight==null) {
			throw new NoSuchFlightIdException("There is no flight with flight ID "+flightId);
		}

		return new ResponseEntity<>(flightService.deleteByFlightId(flightId),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-all-flights")
	public ResponseEntity<String> deleteAllFlights(){
		
		
		if(flightService.getAllFlightDetails().isEmpty()) {
			throw new NoDataFoundException("There are no records to delete the flight details.");
		}
		return new ResponseEntity<>(flightService.deleteAllFlightDetails(),HttpStatus.OK);
	}
	
	


	
}
