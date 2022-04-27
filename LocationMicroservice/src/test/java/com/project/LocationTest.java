package com.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.project.model.Location;
import com.project.repository.LocationRepository;
import com.project.service.LocationService;

@SpringBootTest
public class LocationTest {

	@MockBean
	private LocationRepository locationRepository;

	@Autowired
	private LocationService locationService;

	@Test
	public void getAllLocationDetails() {
		when(locationRepository.findAll()).thenReturn(Stream.of(new Location(19, "RajeevGandhiAirport", "Hyderabad", "India", 654),
				new Location(20, "TirupathiAirport", "Tirupathi", "India", 3456)).collect(Collectors.toList()));
		assertEquals(2, locationService.getLocationDetails().size());
	}

	@Test
	public void fetchById() {
		int id = 21;
		Optional<Location> l = Optional.ofNullable(new Location(21, "IndiraGandhiAirport", "Delhi", "India", 87899));
		when(locationRepository.findById(id))
				.thenReturn(Optional.ofNullable(new Location(21, "IndiraGandhiAirport", "Delhi", "India", 9876)));
		assertEquals(l.get().getLocationId(), locationService.fetchLocationById(id).get().getLocationId());
	}

	@Test
	public void addLocation() {
		Location loc = new Location(12, "IndiraGandhiAirport", "Delhi", "India", 765);
		when(locationRepository.save(loc)).thenReturn(loc);
		assertEquals(loc, locationService.addLocation(loc));

	}

	@Test
	public void updateLocation() {

		Location loc = new Location(21, "Tirupathi Airport", "Tirupathi", "India",6);
		when(locationRepository.save(loc)).thenReturn(loc);
		assertEquals(loc, locationService.updateLocation(loc));
	}

	@Test
	public void deleteLocation() {
		int id = 20;
		Optional<Location> location = Optional.ofNullable(new Location(20, "IndiraGandhiAirport", "Delhi", "India",9));
		when(locationRepository.findById(id)).thenReturn(Optional.ofNullable(new Location(20, "IndiraGandhiAirport", "Delhi", "India",9)));
		assertEquals("the Location id"+id+"is deleted succesufully", locationService.deleteLocation(id));
	}

	@Test
	public void deleteAllLocationDetails() {
		int id = 20;
		Optional<Location> location = Optional.ofNullable(new Location(20, "IndiraGandhiAirport", "Delhi", "India",9));
		when(locationRepository.findById(id)).thenReturn(Optional.ofNullable(new Location(20, "IndiraGandhiAirport", "Delhi", "India",9)));
		assertEquals("All Location Details are Deleted ", locationService.deleteAllLocationDetails());
	}

}
