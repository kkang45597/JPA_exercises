package com.mingi.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		
		/*
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			
			Team team = new Team();
			team.setName("A-Team");
			em.persist(team);
			
			Member member = new Member();
			member.setName("Seo");
				
			team.addMember(member);
			
			em.persist(member);
			
			Long Id = member.getId(); // 이 시점에 member 테이블에 대한 쿼리를 수행한다.
		
//			em.flush();
			em.clear();
			
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			
			
			// EAGER을 통한 지연로딩이기 때문에, Member 엔티티 클래스 객체와 Team 엔티티 클래스 객체가 만들어져서
			// 1차 캐시에 저장한다.
			Member gotMember = em.find(Member.class, member.getId());
			
			System.out.println("gotMember: " + gotMember);
			
			Thread.sleep(100);
			
			Team gotTeam = gotMember.getTeam(); // 이 시점에 team 테이블에 대한 특정 row 쿼리를 수행한다.
			
//			Long teamId = gotTeam.getId();
//			String teamName = gotTeam.getName();
//			System.out.println("Team id: " + teamId);
//			System.out.println("Team Name: " + teamName);
			
			System.out.println("------------------------------------------------------------------");
			
			try {

			}
			catch(Exception e) {
				em.clear();
			}
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
			emf.close();
		}
		*/
		
		List<Long> memIds = saveMembersAndReturnMemberIds(emf);
		printTeamNamesForMemberIds(emf, memIds);

	}
	
	public static List<Long> saveMembersAndReturnMemberIds(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		List<Long> membersIds = new ArrayList<>();
		
		try {
			tx.begin();
			
			Member member1 = new Member("회원1");
			Member member2 = new Member("회원2");
			
			Team team1 = new Team("팀1");
			
			em.persist(team1);
			em.persist(member1);
			em.persist(member2);
			
			team1.addMember(member1);
			team1.addMember(member2);
			
			membersIds.add(member1.getId());
			membersIds.add(member2.getId());
			
			em.flush();
			em.clear();
			
			Member mem = em.find(Member.class, membersIds.get(0));
			
			System.out.println("member의 이름: " + mem.getName());
			
			System.out.println("member의 이름: " + mem.getTeam().getMembers().get(0).getName());
			
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		
		return membersIds;
	}

	public static void printTeamNamesForMemberIds(EntityManagerFactory emf, List<Long> memIds) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		List<Member> members = new ArrayList<>();
		List<Team> teams = new ArrayList<>();
		
		try {
			tx.begin();
			for(Long memberId : memIds) {
				Member member = em.find(Member.class, memberId.longValue());
				members.add(member);
				
				if(teams.indexOf(member.getTeam()) == -1) {
					teams.add(member.getTeam());
				}
			}
			
			for(Team team : teams) {
				if(em.contains(teams)) {
					System.out.println("this team is entity class object");
				}
				
				System.out.printf("팀 이름: %s", team.getName());
				System.out.println("");
				
				// 1차 캐시에 있는데 왜 SELECT문으로 조회하는 이유는
				// Team 클래스의 @OneToMany의 fetch 속성이 현재 LAZY이기 때문이다.
				for(Member member : team.getMembers()) { 
					System.out.printf("       멤버 ID: %s, 멤버 이름: %s", 
							member.getId(), member.getName());
					System.out.println("");
				}
			}
			
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
			emf.close();
		}
	}
}
