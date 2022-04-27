package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="location")
public class Location {

	@Id
	@Column(name="location_id")
	private int locationId;
	@Column(name="airport")
	@NotEmpty
	@Pattern(regexp="[A-Za-z]*$",message="No special characters")
	private String airport;
	@Column(name="city")
	@NotEmpty
	@Pattern(regexp="[A-Za-z]*$",message="No special characters")
	private String city;
	@Column(name="country")
	@NotEmpty
	@Pattern(regexp="[A-Za-z]*$",message="No special characters")
	private String country;
	@NotNull
	@Column(name="flight_id")
	private int flightId;
	

}