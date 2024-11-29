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
        	
			Member member = new Member();
			member.setName("Member1");
			em.persist(member);
			Long memId = member.getId();
        	
			Product product = new Product();
			product.setName("Product1");
        	em.persist(product);
        	Long productId = product.getId();
        	
        	member.getProducts().add(product);
//        	product.getMember().add(member);
        	
        	em.flush();
        	em.clear();
        	
        	Member findMember1 = em.find(Member.class, memId);
        	List<Product> productList = findMember1.getProducts();
        	
        	for(Product item : productList) {
        		String name = item.getName();
        	}
        	
//        	Product findProdcut = em.find(Product.class, productId);
//        	String name = findProdcut.getName();
//        	
//        	//////////////////////////////////////////////////////////////////
//        	Member mem = findProdcut.getMember().get(0); 
//        	//////////////////////////////////////////////////////////////////
        	
            
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
