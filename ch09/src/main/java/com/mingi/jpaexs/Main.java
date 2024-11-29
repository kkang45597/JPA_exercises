package com.mingi.jpaexs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.mingi.jpaexs.entity.Address;
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
    	
    	Thread.sleep(100);
    	searchFavoriteFood(memberId);
    	
    	Thread.sleep(100);
    	updateFavortieFood(memberId);
    	
    	Thread.sleep(100);
    	Long insertedAddressMemberId = insertAddressAndAddressList(memberIds);
    	
    	Thread.sleep(100);
    	updateAddress(memberIds);
    	
    	Thread.sleep(100);
    	updateAddressList(insertedAddressMemberId);
    	
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
			
			member.getFavoriteFood().add("짬뽕");
			member.getFavoriteFood().add("짜장면");
			member.getFavoriteFood().add("냉면");
			
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
	
	public static void searchFavoriteFood(Long memberId) {
		
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		try {
			tx.begin();
			
			Member member = em.find(Member.class, memberId);
//			System.out.println("Member ID: " + memberId);
			for(String str : member.getFavoriteFood()) {
				System.out.println("searchFavoriteeFood: " + str);
			}
            
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
	
	public static void updateFavortieFood(Long memberId) {
		
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		try {
			tx.begin();
			
			Member member = em.find(Member.class, memberId);
			System.out.println("Member ID: " + memberId);
			
			member.getFavoriteFood().remove("냉면");
			member.getFavoriteFood().add("짬짜면");
			
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
	
	public static Long insertAddressAndAddressList(List<Long> memberIds) {
		
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		Long memberId = -1L;
		try {
			tx.begin();
			
			Member member = em.find(Member.class, JpaBooks.generateRandomID(memberIds));
			
			memberId = member.getId();
			
			member.setAddress(new Address("123 Main Street", "Daegu", "12345"));
			
			member.getAddressList().add(new Address("456 Main Street", "Daegu", "67890"));
			member.getAddressList().add(new Address("789 Main Street", "Daegu", "12390"));
			
			em.flush();
			em.clear(); // 1차 캐시를 날린다.
			
			Member foundMember = em.find(Member.class, member.getId());
			
			for(Address address : foundMember.getAddressList()) {
				System.out.println("Street: " + address.getStreet() +
						", City: " + address.getCity() + 
						", ZipCode: " + address.getZipCode());
			}
			
			tx.commit(); // 트랜잭션 커밋
			
			LocalDateTime ldt = LocalDateTime.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
			
			System.out.println("commit complete time: " + ldt.format(dtf));
			System.out.println("commit complete time: " + ldt.toString());
			
//			System.out.println("flush Before time: " + flushBeforeTime.toString());
//			System.out.println("flush After time: " + flushAfterTime.toString());
        } 
        catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback(); // 트랜잭션 롤백
        } 
        finally {
            em.close(); // 엔티티 매니저 종료
        }
		return memberId;
	}
	
	public static Long updateAddress(List<Long> memberIds) {
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		Long memberId = -1L;
		try {
			tx.begin();
			
			Member member = em.find(Member.class, JpaBooks.generateRandomID(memberIds));
			member.setAddress(new Address("123 rodeo str", "Busan", "69713"));
			
			em.flush();
			tx.commit();
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback();
        } 
        finally {
            em.close();
		}
		
		
		em = emf.createEntityManager();
		tx = em.getTransaction();
		try {
			tx.begin();
//			Member member = em.find(Member.class, JpaBooks.generateRandomID(memberIds));
//			Member foundMember = em.find(Member.class, member.getId());	
//			foundMember.getAddress().setCity("Daegu");
			
//			Address addr = foundMember.getAddress();
//			addr.setStreet("dddd");
//			addr.setCity("Daegu");
//			addr.setZipCode("454545");
			 
//			foundMember.setAddress(new Address(addr.getStreet(), "Daegu", addr.getZipCode()));
			tx.commit();
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback(); // 트랜잭션 롤백
        } 
        finally {
            em.close(); // 엔티티 매니저 종료
		}
		
		return memberId;
	}
	
	public static void updateAddressList(Long memberId) {
		EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성
		EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

		try {
			tx.begin();
			
			Member foundMember = em.find(Member.class, memberId);

			List<Address> addrList = foundMember.getAddressList();
			int targetRemovealElementIndex = -1;
			for(int i = 0; i < addrList.size(); i++) {
				Address addr = addrList.get(i);
				if(addr.equals(new Address ("456 Main Street", "Daegu", "67890"))) {
					targetRemovealElementIndex = i;
					break;
				}
			}
			
			if(targetRemovealElementIndex != -1) {
				addrList.remove(targetRemovealElementIndex);
			}
			
			foundMember.getAddressList().add(new Address("347 Jongro Street", "Seoul", "23221"));
			
			em.flush();
			
			tx.commit();
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	tx.rollback();
        } 
        finally {
            em.close();
		}
	}
}
