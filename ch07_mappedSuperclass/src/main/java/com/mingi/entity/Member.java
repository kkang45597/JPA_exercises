package com.mingi.entity;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;
	
	private String name;

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
	
	
}
