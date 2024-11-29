package com.mingi.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Team {

	@Id
	@Column(name = "TEAM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "TEAM_NAME")
	private String name;
	
	// mappedBy는 이 양방향 관계에서 누가 주인인지 표시한다.
	@OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // fetch 설정의 기본값은 LAZY 
	List<Member> members = new ArrayList<>();
	
	// 편의 메서드
	public void addMember(Member memaber) { 
		memaber.setTeam(this);
		members.add(memaber);
	}
	
	public Team() { }
	
	public Team(String name) { // setName 안쓰려고 씀
		this.name = name;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

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

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", members=" + members + "]";
	}
	
}
