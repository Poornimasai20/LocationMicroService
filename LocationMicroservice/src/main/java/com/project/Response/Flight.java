package com.project.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
	
	
	private int flightId;
	private String departureLoc;
	private String  arrivalLoc;
	private String   model;
    private String  fleetName;
    private int remainingSeats;

}
