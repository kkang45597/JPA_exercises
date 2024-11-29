package com.mingi.japexs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Main {

	public static void main(String ...args) {
		
		// JPA 프로그래밍을 하기 위해서는 항상 엔티티 매니저가 필요하다!
		// EntityManagerFactory에 필요한 정보가 있는 persistence.xml
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			/*
			Member mem5 = save(em, 5L, "Anny", 23);
			Member mem6 = save(em, 6L, "Oni", 24);
			Member mem7 = save(em, 7L, "Risa", 25);
			Member mem8 = save(em, 8L, "Bart", 26);
			*/
			
			/*
			// find 메서드의 호출 결과는 이 메서드가,
			// 리턴한 클래스 객체가 영속 컨텍스트의 1차 캐시에 저장되어 있음을 의미
			Member findMem = find(em, 8L);
			// 엔티티 클래스 객체 메서드는 특별하다.
			// 1차 캐시의 해당 엔티티 클래스 객체가 수정, 스냅샷 발생!
			findMem.setUserName("Hong");
			*/
			
			/*
			Member mem9 = save(em, 9L, "Bart", 28);
			em.remove(mem9); // 1차 캐시, 즉 영속 상태의 엔티티 클래스 객체만 remove 한다.
			*/
			
			/*
			Long id = 9L;
			Member memberA = save(em, id, "Tomcat", 10);
			Member memberB = save(em, id+1, "NyCat", 11);
			Member memberC = save(em, id+2, "SunCat", 12);
			*/
			
			/*
			Member member11 = find(em, 11L);
			em.clear();
			member11.setUserName("Kong");
			*/
			
			/*
			Long id = 12L;
			Member mem12 = save(em, id, "hello", 50, RoleType.User);
			Member mem13 = save(em, id+1, "world", 50, RoleType.Admin);
			
			update(em, id, "hello", 50, RoleType.User);
			update(em, id+1, "world", 50, RoleType.Admin);
			*/
			
			for(int i = 0; i < 50; i++) {
//			for(int i = 50; i < 100; i++) {
				String name = "name" + i;
				Member mem1 = save(em, name, 20+i, RoleType.User);
			}
			
//			Member mem1 = save(em, "Hello", 20, RoleType.User);
//			Member mem2 = save(em, "world", 30, RoleType.Admin);
//			Member mem3 = save(em, "my", 40, RoleType.Admin);
//			Member mem4 = save(em, "the", 50, RoleType.Admin);
//			Member mem5 = save(em, "world", 60, RoleType.Admin);
			
			System.out.println("---------------------------------------------------------");
			/*
			// 중간에 JPQL 실행
			// JPQL에게 m은 인스턴스 객체이자 테이블의 각 Row에 매핑된다.
			List members = (List) em.createQuery("select m from Member m", 
					Member.class).getResultList();
			// 별로 안좋다, https://sundaland.tistory.com/189 참조
			*/
			
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			
			em.flush();
			
			try {
//				Thread.sleep(2000);
			}
			catch(Exception e) {
				em.clear();
			}
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			
			/*
			// Select 쿼리 트랜잭션이 수행됨
			// 위 쿼리를 수행 후, 리턴 값으로 Member 엔티티 클래스 객체를 1차 캐시에 생성
			Member member = find(em, id);
			System.out.println("member.name: " + member.getUserName());
			
			// 영속 컨텍스트의 1차 캐시에 저장되어 있는 Member 엔티티 클래스 객체를 리턴
			Member member2 = find(em, id);
			System.out.println("member.name: " + member2.getUserName());
			*/
			
//			Integer age = 70;
//			update(em, id, age);
//	
//			delete(em, id);
			
		
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
			emf.close();
		}
	}

//	private static void save(final EntityManager em, 
	private static Member save(final EntityManager em, 
			String name, Integer age, RoleType roleType) {
		Member member = new Member();
		member.setUserName(name);
		member.setAge(age);
		member.setRoleType(roleType);
		member.onCreate();
		member.onUpdate();
		// 현재 member 객체는 비영속성 상태
		
		em.persist(member);
		return member;
	}

	private static Member find(final EntityManager em, final Long id) {
		return em.find(Member.class, id);
	}

	private static void update(final EntityManager em, 
			final Long id, String name, final Integer age, RoleType roleType) {
		Member member1 = em.find(Member.class, id);
		member1.setUserName(name);
		member1.setAge(age);
		member1.setRoleType(roleType);
		member1.onUpdate();
	}

	private static void delete(final EntityManager em, final Long id) {
		Member member = em.find(Member.class, id);
		if (member != null) em.remove(member);
	}
}
