package com.mingi.jpaexs;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String name;
	
	@ManyToOne/*(fetch = FetchType.LAZY)*/ // 기본값은 EAGER, LAZY를 쓰면 left outer join 안됨
	@JoinColumn(name="TEAM_ID") // 넣으면 양방향이 된다.
	private Team team;
	
	public Member() { }
	
	public Member(String name) { // setName 쓰기 귀찮아서 만듬
		this.name = name;
	}
	
	/*// 20240911 아직 안함
	@ElementCollection // 이 어노테이션이 없으면 런타임 에러가 발생한다.
	List<String> strs = new ArrayList<>();
	*/

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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
		return "Member [id=" + id + ", name=" + name + ", team=" + team + "]";
	}
	
}