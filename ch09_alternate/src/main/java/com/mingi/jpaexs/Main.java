package com.mingi.jpaexs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.mingi.jpaexs.entity.Address;
import com.mingi.jpaexs.entity.AddressEntity;
import com.mingi.jpaexs.entity.FavoriteFood;
import com.mingi.jpaexs.entity.Member;
import com.mingi.jpaexs.utilites.JpaBooks;

public class Main {
	
	static final int TEAM_NUMBERS = 10;
	static final int MEMBER_NUMBERS = 10;
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
	
	public static void main(String[] args) throws InterruptedException {
		List<Long> memberIds = JpaBooks.initMemberTeamSampleData(emf, 
				TEAM_NUMBERS, MEMBER_NUMBERS);
		
		Thread.sleep(100);
    	Long memberId = insertFavortieFood(memberIds);
    	updateAddress(memberId);
    	
    	emf.close();
	}
	
	public static Long insertFavortieFood(List<Long> memberIds) {
		
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		Long id = -1L;
		try {
			tx.begin();
			
			Member member = em.find(Member.class, JpaBooks.generateRandomID(memberIds));
			
			id = member.getId();
			
			FavoriteFood pizza = FavoriteFood.builder().foodName("피자").member(member).build();
			FavoriteFood chicken = FavoriteFood.builder().foodName("치킨").member(member).build();
			FavoriteFood hambugger = FavoriteFood.builder().foodName("햄버거").member(member).build();
			
			member.getFavoriteFoos().add(pizza);
			member.getFavoriteFoos().add(chicken);
			member.getFavoriteFoos().add(hambugger);
			
			
			AddressEntity address1 = AddressEntity.builder().address(
					new Address("123 Elm st", "Daegu", "1234")).member(member).build();
			AddressEntity address2 = AddressEntity.builder().address(
					new Address("456 Elm st", "Busan", "5678")).member(member).build();
			
			member.getAddressList().add(address1);
			member.getAddressList().add(address2);
			
			em.persist(address1);
			
			em.flush();
            
			tx.commit(); // 트랜잭션 커밋
        } 
        catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback(); // 트랜잭션 롤백
        } 
        finally {
            em.close(); // 엔티티 매니저 종료
        }
		return id;
	}
	
	public static void updateAddress(Long memberId) {
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		try {
			tx.begin();
			
			Member member = em.find(Member.class, memberId);
			
			member.getAddressList();
			
			List<AddressEntity> addrList = member.getAddressList();
			if(!addrList.isEmpty()) {
				AddressEntity addressToRemove = addrList.get(0);
				addrList.remove(addressToRemove);
//				em.remove(addressToRemove);
			}
			
			AddressEntity newAddress = AddressEntity.builder()
					.address(new Address("St Free", "Newyork", "23231"))
					.member(member).build();
			
			addrList.add(newAddress);
			
			em.flush();
            
			tx.commit(); // 트랜잭션 커밋
        } 
        catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback(); // 트랜잭션 롤백
        } 
        finally {
            em.close(); // 엔티티 매니저 종료
        }
	}
}
