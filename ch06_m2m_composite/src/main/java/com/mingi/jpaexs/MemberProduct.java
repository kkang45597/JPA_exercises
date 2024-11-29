package com.mingi.jpaexs;

import javax.persistence.*;

@Entity
@IdClass(MemberProductId.class)
public class MemberProduct {
	
	// 더 이상 primary key 값 자동 생성 전략이 필요하지 않음

	@Id
	@ManyToOne
	@JoinColumn(name="MEMBER_ID") // 실제 테이블에서 필드로 생성
	private Member member;
	
	@Id
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID") // 실제 테이블에서 필드로 생성
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
