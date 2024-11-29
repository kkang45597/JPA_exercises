package com.mingi.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String name;
	
	@ManyToMany
	@JoinTable(name="MEMBER_PRODUCT", 
		joinColumns=@JoinColumn(name="MEMBER_ID"),
		inverseJoinColumns=@JoinColumn(name="PRODUCT_ID"))
	private List<Product> products = new ArrayList<>();

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	
}
