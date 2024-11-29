package com.mingi.jpaexs;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@DynamicInsert
@Entity
@DiscriminatorValue("MOVIE") 
public class Movie extends Item { // Item 클래스와 JOIN 한다.

	private String director;
	
	

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
}
