package com.mingi.jpaexs;

import javax.persistence.*;

@Entity
public class Traveler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TRAVELAR_ID")
	private Long id;
	
	private String userName;
	
	@OneToOne
	@JoinColumn(name="PASSPORT_ID", unique=true)
	private Passport passport;
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	public Traveler() { }
	
	public Traveler(String userName) { 
		this.userName = userName;
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	
	

}
