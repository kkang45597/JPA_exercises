package com.mingi.jpaexs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
	
	public static void createMemberAndLocker(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		Traveler traveler1 = new Traveler("Kim");
		em.persist(traveler1);
		Long TravId = traveler1.getId();
		
		Passport passport = new Passport("Passport 1");
		em.persist(passport);
		Long PassId = passport.getId();
		
		/////////////////////////////////////
		traveler1.setPassport(passport);
		/////////////////////////////////////
		
		em.flush();
		em.clear();
		
//		Traveler traveler = em.find(Traveler.class, TravId);
//		Passport passport1 = traveler.getPassport();
		
		Passport passport2 = em.find(Passport.class, PassId);
		Traveler traveler2 = passport2.getTraveler();
		
		tx.commit();
		
		em.close();
		emf.close();
	}

	public static void main(String ...args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		
		createMemberAndLocker(emf);
		
	}

}
