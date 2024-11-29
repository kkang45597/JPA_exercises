package com.mingi.jpaexs;

import javax.persistence.*;

@Entity
public class LineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="lineitem_id")
	private Long id;
	
	private String productName;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

	
	////////////////////////////////////////////////////////////////////////////////////
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
