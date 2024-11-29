package com.mingi.jpaexs;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Long id;
	
	private String name;
	
	// 일대다에서 일은 부모를 다는 자식이 된다.
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customer", orphanRemoval=true)
	private Set<Order> orders = new HashSet<>();
	
	public void addOrder() { }
	
	// 편의 메서드
	public void addOrder(Order order) {
		order.setCustomer(this);
		orders.add(order);
	}
	
	public void removeOrder(Order order) {
		orders.remove(order);
		order.setCustomer(null);
	}
	
	////////////////////////////////////////////////////////////////////////////////////
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

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	
}
