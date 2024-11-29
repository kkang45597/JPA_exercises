package com.mingi.jpaexs;

import javax.persistence.*;

@Entity
public class MemberProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID")
	private Product product;
	
	private int orderAmount;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	
}
