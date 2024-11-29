package com.mingi.jpaexs;

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

            tx.begin(); //트랜잭션 시작

            createMembersAndTeam(em);
            
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
            emf.close(); //엔티티 매니저 팩토리 종료
        }
    }

    public static void createMembersAndTeam(EntityManager em) {

        System.out.println("******************************************************");
        System.out.println("+createMembersAndTeam");

        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("Member1");
        member2.setName("Member2");
        System.out.println("em.persist(member1);");
        System.out.println("em.persist(member2);");
        em.persist(member1); // persist 호출 후에, team의 id를 얻을 수 있다.
        em.persist(member2);

        Long member1Id = member1.getId();
        Long member2Id = member2.getId();

        Team team1 = new Team();
        team1.setName("TEAM1");

        System.out.println("team1.getMembers().add(member1);");
        System.out.println("team1.getMembers().add(member2);");
        ////////////////////////////////
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);
        ////////////////////////////////

        System.out.println("em.persist(team1);");
        em.persist(team1);
        Long teamId = team1.getId();

        System.out.println("******************************************************");
        System.out.println("em.flush()");
        System.out.println("******************************************************");
        em.flush();
        em.clear();

        /*
        System.out.println("Team team = em.find(Team.class, teamId);");
        ////////////////////////////////////////////////////////////
        Team team = em.find(Team.class, teamId);
        /////////////////////////////////////////////////////////////
        System.out.printf("팀 ID:%d, 팀 이름:%s \n", team.getId(), team.getName());
        System.out.println("for (Member member : team.getMembers())");
        for (Member member : team.getMembers()) { //
            System.out.printf("        멤버 ID:%d, 멤버 이름:%s \n", member.getId(), member.getName());
        }
        */
        
        System.out.println("=======================================================");

        System.out.println("Member 엔티티 클래스 객체를 생성함.");
        Member mem1 = em.find(Member.class, member1Id);
        Member mem2 = em.find(Member.class, member2Id);
        
        System.out.println("******************************************************");
        
        Long teamxId = mem1.getTeam().getId();
        Team team = em.find(Team.class, teamxId);

        System.out.println("-createMembersAndTeam");
        System.out.println("******************************************************");
    }
}