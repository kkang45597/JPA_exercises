package com.mingi.jpaexs;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@DynamicInsert
@Entity
@DiscriminatorValue("BOOK") 
public class Book extends Item { // Item 클래스와 JOIN 한다.

	private String author;
	private String isbn;
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}
