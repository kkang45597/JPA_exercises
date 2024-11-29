package com.mingi.jpaexs;

import java.util.List;

import javax.persistence.*;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		try {
			tx.begin();
        	
			Member member1 = new Member();
			member1.setName("Member1");
			em.persist(member1);
			Long memId1 = member1.getId();
			
			Member member2 = new Member();
			member2.setName("Member2");
			em.persist(member2);
			Long memId2 = member2.getId();
        	
			Product product1 = new Product();
			product1.setName("Product1");
        	em.persist(product1);
        	Long productId1 = product1.getId();
        	
        	Product product2 = new Product();
			product2.setName("Product2");
        	em.persist(product2);
        	Long productId2 = product2.getId();
        	
        	MemberProduct memberProduct1 = new MemberProduct();
        	memberProduct1.setMember(member1);
        	memberProduct1.setProduct(product1);
        	memberProduct1.setOrderAmount(10);
        	em.persist(memberProduct1);
        	
        	MemberProduct memberProduct2 = new MemberProduct();
        	memberProduct2.setMember(member2);
        	memberProduct2.setProduct(product2);
        	memberProduct2.setOrderAmount(10);
        	em.persist(memberProduct2);

        	MemberProduct memberProduct3 = new MemberProduct();
        	memberProduct3.setMember(member1);
        	memberProduct3.setProduct(product2);
        	memberProduct3.setOrderAmount(10);
        	em.persist(memberProduct3);
        	
        	MemberProduct memberProduct4 = new MemberProduct();
        	memberProduct4.setMember(member2);
        	memberProduct4.setProduct(product1);
        	memberProduct4.setOrderAmount(10);
        	em.persist(memberProduct4);
        	
        	em.flush();
        	em.clear();
        	
        	///////////////////////////////////////////////////////////
//        	Member findMember = em.find(Member.class, memId1);
//        	List<MemberProduct> memberProducts = findMember.getMemberProducts();
//        	for(MemberProduct mp : memberProducts) {
//        		Product product = mp.getProduct();
//        		System.out.printf("상품 id: %d, 상품 name: %s \n", product.getId(), product.getName());
//        	}
        	
        	Product findProduct = em.find(Product.class, productId1);
        	List<MemberProduct> memberProducts2 = findProduct.getMemberProducts();
        	for(MemberProduct mp : memberProducts2) {
        		Member member = mp.getMember();
        		System.out.printf("멤버 id: %d, 멤버 name: %s \n", member.getId(), member.getName());
        	}
        	
        	
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
