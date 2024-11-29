package com.mingi.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="orders") // order는 예약어라 테이블명으로 사용할 수 없다.
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long id;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	// LineItem 클래스의 Order 클래스와 매핑
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order", orphanRemoval=true) 
	private List<LineItem> lineItems = new ArrayList<>();
	
	public void addLineItem() { }
	
	public void addLineItem(LineItem lineItem) {
		lineItem.setOrder(this);
		lineItems.add(lineItem);
	}
	
	public void removeLineItem(LineItem lineItem) {
		lineItems.remove(lineItem);
		lineItem.setOrder(null);
	}

	
	////////////////////////////////////////////////////////////////////////////////////
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

}
