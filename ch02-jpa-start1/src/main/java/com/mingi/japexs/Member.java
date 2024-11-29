package com.mingi.japexs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DynamicUpdate
@TableGenerator(
		  name = "MEMBER_SEQ_GENERATOR",
		  table = "MY_SEQUENCES",
		  pkColumnValue = "MEMBER_SEQ", allocationSize = 50)
public class Member {
	// Default Constructor 반드시 있어야 한다.
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(strategy = GenerationType.TABLE,
//			generator = "MEMBER_SEQ_GENERATOR")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // Integer(X), String(X): 자동증가가 안됨
	
//	@Column(name = "name")
	private String userName;
	
	private Integer age;
	
//	@Enumerated(EnumType.ORDINAL) // 정수값으로 저장됨 (0, 1, 2...)
	@Enumerated(EnumType.STRING) // 문자열로 저장됨
	private RoleType roleType;
	
//	@Column(unique = true)
//	private String email;
	
//	@Column(unique = true)
//	private String phoneNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate; 

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate; 

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@PrePersist
	protected void onCreate() {
        this.createdDate = new Date();
        this.lastModifiedDate = new Date();
    }
	
	@PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = new Date();
    }
}
