package com.mingi.jpaexs;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@DynamicInsert
@Entity
//@Inheritance(strategy=InheritanceType.JOINED)
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="DTYPE") // DTYPE 이름의 필드를 생성
public abstract class Item {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // TABLE_PER_CLASS에선 못씀
	@GeneratedValue // IDENTITY 전략을 사용하면 Sub Class의 테이블을 생성할 수 없다.
	@Column(name = "ITEM_ID")
	private Long id;
	
	private String name;
	private int price;
	
	
	///////////////////////////////////////////////////////
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
