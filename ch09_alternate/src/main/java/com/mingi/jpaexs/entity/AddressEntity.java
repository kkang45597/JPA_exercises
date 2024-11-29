package com.mingi.jpaexs.entity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity
public class AddressEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Embedded
	private Address address;
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID")
	private Member member;
}
