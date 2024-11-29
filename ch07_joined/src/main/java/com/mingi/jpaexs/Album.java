package com.mingi.jpaexs;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@DynamicInsert
@Entity
@DiscriminatorValue("ALBUM") 
public class Album extends Item { // Item 클래스와 JOIN 한다.
	
	private String artist;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	
}
