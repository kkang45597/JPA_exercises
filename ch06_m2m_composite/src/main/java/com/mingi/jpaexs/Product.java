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
	
	@OneToMany(mappedBy="product")
	private List<MemberProduct> memberProducts = new ArrayList<>();
	
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

	public List<MemberProduct> getMemberProducts() {
		return memberProducts;
	}

	public void setMemberProducts(List<MemberProduct> memberProducts) {
		this.memberProducts = memberProducts;
	}

}
