package com.mingi.jpaexs.utilites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.mingi.jpaexs.entity.Member;
import com.mingi.jpaexs.entity.Team;

public class JpaBooks {
	
	static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	public static List<Long> initMemberTeamSampleData(EntityManagerFactory emf, 
			int teamNumbers, int memberNumbers) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		List<Long> memberIds = new ArrayList<>();
		
		try {
			tx.begin();
			List<Long> teamIds = new ArrayList<>();
			for(int i = 0; i < teamNumbers; i++) {
				Team team = new Team();
				team.setName("team: " + i);
				em.persist(team);
				teamIds.add(team.getId());
			}
			
			Long minIdValue = Collections.min(teamIds);
			Long maxIdValue = Collections.max(teamIds);
			
			
			for(int i = 0; i < memberNumbers; i++) {
				Member member = new Member();
				member.setName("Kim: " + i);
				Long targetTeamid = generateRandomNumber(minIdValue, maxIdValue);
				
				Team team = em.find(Team.class, targetTeamid);
				team.addMember(member);
				em.persist(member);
				memberIds.add(member.getId());
			}
			
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
		return memberIds;
	}
	
	public static Long generateRandomNumber(Long min, Long max) {
		Random random = new Random();
		return random.nextLong(max - min +1) + min;
	}
	
	public static Long generateRandomID(List<Long> ids) {
		Long minIdValue = Collections.min(ids);
		Long maxIdValue = Collections.max(ids);
		
		return generateRandomNumber(minIdValue, maxIdValue);
	}
}
