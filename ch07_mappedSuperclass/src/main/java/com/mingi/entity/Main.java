package com.mingi.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		try {
			tx.begin();
			
			Member member = new Member();
			member.setName("Name1");
//			member.setCreatedBy("me");
			em.persist(member);
			
			em.flush();
			em.clear();
			
			Member gotMember = em.find(Member.class, member.getId());
			
        	///////////////////////////////////////////////////////////
 
        	///////////////////////////////////////////////////////////
            
			tx.commit(); //트랜잭션 커밋
        } 
        catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback(); //트랜잭션 롤백
        } 
        finally {
            em.close(); //엔티티 매니저 종료
            emf.close(); //엔티티 매니저 팩토리 종료
        }
	}

}
