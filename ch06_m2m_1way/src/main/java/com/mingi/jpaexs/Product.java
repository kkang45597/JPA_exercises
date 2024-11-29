package com.mingi.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private Long id;
	
	private String name;
	
	@ManyToMany(mappedBy="products") // mappedBy를 넣으면 양방향이 된다.
	private List<Member> member = new ArrayList<>();

	
	
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

	public List<Member> getMember() {
		return member;
	}

	public void setMember(List<Member> member) {
		this.member = member;
	}

	
	
	
}
