package com.mingi.jpaexs;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

public class Main {
	
	public static Map<EntityClassStyle, Long> setTestTables(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Map<EntityClassStyle, Long> maps = new HashMap<>();
		
		tx.begin();
		try {
			Customer customer = new Customer();
			customer.setName("Kim");
			
			Order order = new Order();
			order.setDescription("Order 1");
			customer.addOrder(order);
			
			LineItem item1 = new LineItem();
			item1.setProductName("Item 1");
			item1.setQuantity(2);
			order.addLineItem(item1);
			
			em.persist(customer); // CascadeType.ALL일 경우 하나만 persist()를 한다.
//			em.persist(order); // CascadeType.REMOVE라 필요함
//			em.persist(item1); //CascadeType.REMOVE라 필요함
			
			tx.commit();
			
			System.out.println("order is Persist: " + em.contains(order));
			System.out.println("item1 is Persist: " + em.contains(item1));
			
			maps.put(EntityClassStyle.CUSTOMER, customer.getId());
			maps.put(EntityClassStyle.OREDER, order.getId());
			maps.put(EntityClassStyle.LINEITEM, item1.getId());
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		return maps;
	}
	
	public static void occurenceOrphanEntity(EntityManagerFactory emf, 
			Map<EntityClassStyle, Long> maps) throws InterruptedException {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Customer customer = null;
		Order order = null;
		LineItem item1 = null;
		
		tx.begin();
		try {
			
			customer = em.find(Customer.class, maps.get(EntityClassStyle.CUSTOMER));
			order = em.find(Order.class, maps.get(EntityClassStyle.OREDER));
			item1 = em.find(LineItem.class, maps.get(EntityClassStyle.OREDER));
			
			customer.removeOrder(order);
			
			// ordre는 oprhan entity class object
			// cascade=cascadeType.REMOVE로 설정된 경우
			// 편의 removeOrder 메서드가 영향을 미치치 못함, 그래서 직접 remove 해야된다.
//			em.remove(order);
			em.flush();
			
			if(em.contains(customer)) {
				System.out.println("1. customer는 영속 상태");
			}
			else {
				System.out.println("1. customer는 비영속 상태");
			}
			if(em.contains(order)) {
				System.out.println("1. order는 영속 상태");
			}
			else {
				System.out.println("1. order는 비영속 상태");
			}
			if(em.contains(item1)) {
				System.out.println("1. item1는 영속 상태");
			}
			else {
				System.out.println("1. item1는 비영속 상태");
			}
			
			order.removeLineItem(item1); // 이게 없으면 위에서 없어진 영속 상태의 order를 찾는다고 런타임 오류가 발생한다.
			
			tx.commit();
		} 
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////
		Thread.sleep(1000);
		
		// 엔티티 매니저를 종료 후 다시 실행하면 IDENTITY 값이 증가한다.
		em = emf.createEntityManager();
		tx = em.getTransaction();
		
		tx.begin();
		try {
			System.out.println("---------------------------------------------------------------------");
			Customer nCustomer = null;
			if(em.contains(customer)) {
				System.out.println("2. customer는 영속 상태");
			}
			else {
				System.out.println("2. customer는 비영속 상태");
				nCustomer = em.merge(customer);
			}
			System.out.println("---------------------------------------------------------------------");
			Order nOrder = null;
			if(em.contains(order)) {
				System.out.println("2. order는 영속 상태");
			}
			else {
				System.out.println("2. order는 비영속 상태");
				nOrder = em.merge(order);
			}
			System.out.println("---------------------------------------------------------------------");
			LineItem nItem1 = null;
			if(em.contains(item1)) {
				System.out.println("2. item1는 영속 상태");
			}
			else {
				System.out.println("2. item1는 비영속 상태");
				nItem1 = em.merge(item1);
			}
			
			nCustomer.addOrder(nOrder);
			nOrder.addLineItem(nItem1);
			
			em.flush();
			
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
	}

	public static void main(String ...args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		
		try {
			Map<EntityClassStyle, Long> maps = setTestTables(emf);
			occurenceOrphanEntity(emf, maps);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			emf.close();
		}
	}
}
