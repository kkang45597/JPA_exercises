package com.mingi.jpaexs.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="MEMBERS")
@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MEMBER_ID")
	private Long id;
	
	private String name;
	
	@Embedded
	private Address address; // JPA는 스냅셧으로 address 참조 필드의 "주소" 값 변경을 체크한다.
							 // address 의 필드 (street, city, zipCode) 변경 사항은 체크하지 않는다. 
	
	@OneToMany(mappedBy="member", cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<FavoriteFood> favoriteFoos = new HashSet<>();
	
	@OneToMany(mappedBy="member", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<AddressEntity> addressList = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="team_id")
	private Team team;
	

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + "]";
	}
}
