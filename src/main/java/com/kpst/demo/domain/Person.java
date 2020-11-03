package com.kpst.demo.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Person extends AbstractEntity {
	
	@Column(name = "firstName", length = 200)
	private String firstName;
	
	@Column(name = "lastName", length = 200)
	private String lastName;
	
	@Column(name = "email", length = 200)
	private String email;
	
	@Column(name = "dateOfBirth")
	@Temporal(TemporalType.DATE)
	private LocalDate dateOfBirth;
	
	@Column(name = "latitude")
	private float latitude;
	
	@Column(name = "longitude")
	private float longitude;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", dateOfBirth="
				+ dateOfBirth + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
