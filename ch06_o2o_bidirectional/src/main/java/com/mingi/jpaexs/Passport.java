package com.mingi.jpaexs;

import javax.persistence.*;

@Entity
public class Passport {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PASSPORT_ID")
	private Long id;
	
	private String name;
	
	@OneToOne(mappedBy="passport")
	private Traveler traveler;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	public Passport() { }
	
	public Passport(String name) { 
		this.name = name;
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
	
	
}
