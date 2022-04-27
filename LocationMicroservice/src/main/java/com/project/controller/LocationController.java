package com.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Response.ResponseTemplate;
import com.project.exception.IdNotFoundException;
import com.project.exception.RecordsNotFoundException;
import com.project.model.Location;
import com.project.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {

	@Autowired
	LocationService locationService;

	@PostMapping("/addLocation")
	public ResponseEntity<String> addLocationDetails(@Validated @RequestBody Location loc) {
		Location location = locationService.addLocation(loc);
		return new ResponseEntity<String>("Successfully registered the id is:" + location.getLocationId(),
				HttpStatus.OK);
	}

	@GetMapping("/getAllLoactionDetails")
	public ResponseEntity<List<Location>> fetchLocById() {
		List<Location> location = locationService.getLocationDetails();
		if (locationService.getLocationDetails().isEmpty()) {
			throw new RecordsNotFoundException("NO RECORDS LEFT TO DELETE");
		}
		return new ResponseEntity<List<Location>>(location, HttpStatus.OK);
	}

	@GetMapping("/getlocById")
	public ResponseEntity<Location> fetchLocById(@RequestParam("id") int locationId) {
		Optional<Location> location = locationService.fetchLocationById(locationId);

		if (location.isEmpty()) {
			throw new IdNotFoundException("ID NOT FOUND TO FETCH");
		}
		return new ResponseEntity<Location>(location.get(), HttpStatus.OK);

	}

	@GetMapping("/getLocationById/{id}")
	public ResponseEntity<ResponseTemplate> getLocationById(@PathVariable("id") int locationId) {
		System.out.println("am from fnnn");
		ResponseTemplate obj = locationService.getLocationById(locationId);
		return new ResponseEntity<ResponseTemplate>(obj, HttpStatus.OK);
	}

	@PutMapping("updatelocDetails")
	public ResponseEntity<String> updateLocById(@RequestBody Location loc) {
		String status = null;

		Optional obj = locationService.fetchLocationById(loc.getLocationId());
		if (obj.isPresent()) {
			locationService.updateLocation(loc);
			status = "Successfully updated";
		} else {
			throw new IdNotFoundException("ID NOT FOUND TO UPDATE");
		}

		return new ResponseEntity<String>(status, HttpStatus.OK);

	}

	@DeleteMapping("/deletelocByid")
	public ResponseEntity<String> deleteLocation(@RequestParam("id") int locationId) {

		String status = null;
		Optional<Location> location = locationService.fetchLocationById(locationId);
		if (location.isPresent()) {
			locationService.deleteLocation(locationId);
			status = "Deleted successfully";
		}

		else {

			throw new IdNotFoundException("ID NOT FOUND TO DELETE");
		}

		return new ResponseEntity<String>("id " + location.get() + status, HttpStatus.OK);

	}

	@DeleteMapping("/DeleteAll")
	public ResponseEntity<String> deleteAll() {
				if (locationService.getLocationDetails().isEmpty()) {
			throw new RecordsNotFoundException("NO RECORDS LEFT TO DELETE");
		}

		return new ResponseEntity<String>(locationService.deleteAllLocationDetails(), HttpStatus.OK);
	}

}
