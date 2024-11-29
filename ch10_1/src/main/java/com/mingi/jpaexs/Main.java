package com.mingi.jpaexs;

import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.*;

import com.mingi.jpaexs.entity.Address;
import com.mingi.jpaexs.entity.AddressEntity;
import com.mingi.jpaexs.entity.FavoriteFood;
import com.mingi.jpaexs.entity.Member;
import com.mingi.jpaexs.entity.Team;
import com.mingi.jpaexs.entity.UserDTO;
import com.mingi.jpaexs.qentity.QMember;
import com.mingi.jpaexs.qentity.QTeam;
import com.mingi.jpaexs.utilites.JpaBooks;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.querydsl.core.Tuple;

public class Main {
	
	static final int TEAM_NUMBERS = 10;
	static final int MEMBER_NUMBERS = 30;

	static final int POST_NUMBERS = 10;
	static final int COMMENT_NUMBERS = 10;

	static final long POST_STRING_MAX_SIZE = 1500L;

	static final long COMMENT_STRING_MAX_SIZE = 300L;

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
	
	public static void main(String[] args) throws InterruptedException {
//		List<Long> memberIds = JpaBooks.initMemberTeamSampleData(emf,
//				TEAM_NUMBERS, MEMBER_NUMBERS);
		
//		Thread.sleep(100);
//    	Long memberId = insertFavortieFood(memberIds);
    	
//    	queryMemberOfTypedQuery();
//    	queryColumOfQuery();
//    	useUserDTO();
//    	queryParameterBounding(memberIds);
//    	testJPACriteria(memberIds);
//    	getSingleRelationalShipEntity();
//    	testInnerJoin();
//    	testLeftOuterJoin();
//    	testCrossJoin();
//    	testAggregateFunction();
//    	testGroupbyHavingORederby();
//    	setSubQueryInSelect();
//    	testSubQueryInWhere();
//    	testSubQueryInFromAlternate();

//		testQueryDSLSelect();
//    	testQueryDSLInsert();
//		testQueryDSLUpdate();
//		testQueryDSLDelete();
//		testQueryDSLX();
//		testPagingAPIByJPQL();
//		testPagingAPIByQueryDSL();
//		testPagingAPIWithOffsetByQueryDSL();
//		testFetchJoinByJPQL();
//		testFetchJoinByQueryDSL();
//		testCollectionFetchJoin();

		List<Long> postIds = JpaBooks.initPostCommentSampleData(emf,
				POST_NUMBERS, POST_STRING_MAX_SIZE, COMMENT_NUMBERS, COMMENT_STRING_MAX_SIZE);

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

	public static void queryMemberOfTypedQuery() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 

		try {
			tx.begin();
			
			TypedQuery<Member> query = em.createQuery("Select m from Member m", Member.class);
			
			List<Member> members = query.getResultList();
			
			for(Member m : members) {
				System.out.println("Member Id: " + m.getId() + ", Member Name: " + m.getName());
			}
			
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
	
	public static void queryColumOfQuery() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			Query query = em.createQuery("Select m.name, m.age from Member m");
			
			// 엘리먼트는 오브젝트 배열: 엘리먼트 개수는 2개
			@SuppressWarnings("unchecked")
			List<Object[]> resultList = query.getResultList();
			
			for(Object[] result : resultList) {
				String name = (String)result[0];
				Integer age = (Integer)result[1];
				System.out.println("Member Name: " + name + ", Member Age: " + age);
			}
			
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
	
	public static void queryParameterBounding(List<Long> memberIds) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			Long memberId = JpaBooks.generateRandomID(memberIds);
			Member foundMember = em.find(Member.class, memberId);
			em.clear();
			
			String userNameParam = foundMember.getName();
			
			List<Member> members = 
					em.createQuery("Select m FROM Member m WHERE m.name = :name", 
							Member.class).setParameter("name", userNameParam).getResultList();
			
			for(Member m : members) {
				System.out.println("found Name: " + m.getName());
			}
			
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
	
	public static void useUserDTO() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			TypedQuery<UserDTO> query = 
					em.createQuery("SELECT new com.mingi.jpaexs.entity.UserDTO(m.name, m.age) FROM Member m",
							UserDTO.class);
			
			List<UserDTO> resultList = query.getResultList();
			for(UserDTO d : resultList) {
				System.out.println("Name: " + d.getName() + ", Age: " + d.getAge());
			}
			
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
	
	public static void testJPACriteria(List<Long> memberIds) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			Long memberId = JpaBooks.generateRandomID(memberIds);
			Member foundMember = em.find(Member.class, memberId);
			em.clear();
			
			String name = foundMember.getName();
			System.out.println("Name: " + name); // member Name과 같다.
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Member> cq = cb.createQuery(Member.class);
			
			Root<Member> member = cq.from(Member.class);
			cq.select(member).where(cb.equal(member.get("name"), name));
			
			List<Member> members = em.createQuery(cq).getResultList();
			
			for(Member m : members) {
				System.out.println("member Name: " + m.getName());
			}
			
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
	
	public static void getSingleRelationalShipEntity() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			TypedQuery<Team> query = 
					em.createQuery("SELECT m.team FROM Member m", Team.class);
			
			List<Team> teamList = query.getResultList();
			
			for(Team t : teamList) {
				System.out.println("team Id: " + t.getId() + ", team Name: " + t.getName());
			}
			
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
	
	public static void testInnerJoin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			List<Team> teamList =
					em.createQuery("SELECT distinct t FROM Member m JOIN m.team t",
							Team.class).getResultList();
			
			for(Team t : teamList) {
				System.out.println("team Id: " + t.getId() + ", team Name: " + t.getName());
			}
			
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
	
	public static void testLeftOuterJoin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			@SuppressWarnings("unchecked")
			List<Object[]> objList =
					em.createQuery("SELECT m, t FROM Member m LEFT OUTER JOIN m.team t")
					.getResultList();
			
			for(Object[] obj : objList) {
				Member m = (Member)obj[0];
				Team t = (Team)obj[1];
				System.out.printf("Member name: %s, Team name: %s \n", m.getName(), t.getName());
			}
			
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

	public static void testCrossJoin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			String query = "SELECT m, t FROM Member m, Team t";
			@SuppressWarnings("unchecked")
			List<Object[]> objList = em.createQuery(query).getResultList();
			
			for(Object[] obj : objList) {
				Member m = (Member)obj[0];
				Team t = (Team)obj[1];
				System.out.printf("Member: %s, Team: %s \n", m, t);
			}
			
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
	
	public static void testAggregateFunction() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			TypedQuery<Long> sumQuery =
					em.createQuery("SELECT sum(m.age) FROM Member m", Long.class);
			Long totalAge = sumQuery.getSingleResult();
			System.out.println("Sum of Age: " + totalAge);
			
			Double averageAge = 
					em.createQuery("SELECT avg(m.age) FROM Member m", Double.class).getSingleResult();
			System.out.println("Avg of Age: " + averageAge);
			
			Integer maxValue = 
					em.createQuery("SELECT max(m.age) FROM Member m", Integer.class).getSingleResult();
			System.out.println("Max of Age: " + maxValue);
			
			Integer minValue = 
					em.createQuery("SELECT min(m.age) FROM Member m", Integer.class).getSingleResult();
			System.out.println("Min of Age: " + minValue);
			
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
	
	public static void testGroupbyHavingORederby() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();

			String query = "SELECT t.name, AVG(m.age) "
					+ "FROM Team t JOIN t.memberList m " // 팀에 속한 멤버들애서부터 시작
					+ "GROUP BY t.name " // 팀 이름별로 그룹핑
					+ "HAVING AVG(m.age) > 30 "
					+ "ORDER BY AVG(m.age) DESC "; // 내림차순으로 정렬
			
			@SuppressWarnings("unchecked")
			List<Object[]> objList = em.createQuery(query).getResultList();
			
			for(Object[] obj : objList) {
				String name = (String)obj[0];
				Double age = (Double)obj[1];
				System.out.printf("Team name: %s, Team age: %s \n", name, age);
			}
			
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
	
	public static void setSubQueryInSelect() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();

			// 각 Member 의 name 과 해당 Member 가 속한 Team 의 총 멤버 수를 구하는 쿼리
			String query = "SELECT m.name, "
					+ "(SELECT COUNT(subM) FROM Member subM WHERE subM.team = m.team) "
					+ "AS teamMemberCount FROM Member m";
			
			@SuppressWarnings("unchecked")
			List<Object[]> objList = em.createQuery(query).getResultList();
			
			for(Object[] obj : objList) {
				String name = (String)obj[0];
				Long count = (Long)obj[1];
				System.out.printf("Member name: %s, Team member count: %s \n", name, count);
			}
			
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
	
	public static void testSubQueryInWhere() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();

			String query = "SELECT m FROM Member m  WHERE m.age = (SELECT MAX(subM.age) "
					+ "FROM Member subM WHERE subM.team = m.team)";
			List<Member> memberList = em.createQuery(query, Member.class).getResultList();
			
			for(Member m : memberList) {
				System.out.println("Team: " + m.getTeam().getName() + ", Member: " + m.getName() + 
						", Max Age: " + m.getAge());
			}
			
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
	
	public static void testSubQueryInFromAlternate() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction(); 
		
		try {
			tx.begin();
			
			// JPQL은 FROM 절에서 서브쿼리를 허용하지 않기 때문에 네이비트 JOIN SQL 쿼리로 풀어야함
			
			String query = "SELECT m.name, subQuery.teamName "
					+ "FROM Member m "
					+ "JOIN (SELECT t.TEAM_ID  AS team_id, t.name AS teamName "
						+ "FROM Team t "
						+ "JOIN Member m on t.TEAM_ID = m.TEAM_ID "
						+ "GROUP BY t.TEAM_ID, t.name HAVING AVG(m.age) > 30) AS subQuery "
					+ "ON m.TEAM_ID = subQuery.team_id";

			Query nativeQuery = em.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> objList = nativeQuery.getResultList();
			
			for(Object[] obj : objList) {
				String memberName = (String) obj[0];
				String teamName = (String) obj[1];
				System.out.println("Member name: " + memberName + ", Team name: " + teamName);
			}
			
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

	public static void testQueryDSLInsert() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Member member = new Member();
			member.setName("1stQueryDSLInsert");
			member.setAge(30);

			em.persist(member);

			System.out.println("+testQueryDSLInsert");
			System.out.println("-testQueryDSLInsert");

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

	public static void testQueryDSLSelect() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		try {
			tx.begin();

			Member member = new Member();
			member.setName("3rdQueryDSLUpdate");
			member.setAge(20);

			em.persist(member);
			em.flush();
			em.clear();

			QMember qMember = QMember.member;
			List<Member> memberList = queryFactory.selectFrom(qMember).where(qMember.id.eq(member.getId())).fetch();

			for(Member m : memberList) {
				System.out.println("Member name: " + m.getName());
			}

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

	public static void testQueryDSLUpdate() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		try {
			tx.begin();

			Member member = new Member();
			member.setName("1stQueryDSLUpdate");
			member.setAge(30);

			em.persist(member);
			em.flush();
			em.clear();

			String newName = "New Name";
			QMember qMember = QMember.member;
			long affectedRows = queryFactory
					.update(qMember)
					.set(qMember.name, newName)
					.where(qMember.id.eq(member.getId()))
					.execute();

			System.out.println("affected Rows : " + affectedRows);

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

	public static void testQueryDSLDelete() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		try {
			tx.begin();

			Member member = new Member();
			member.setName("sungwon");
			member.setAge(30);

			em.persist(member);
			em.flush();
			em.clear();

			QMember qMember = QMember.member;
			long affectedRows = queryFactory
					.delete(qMember)
					.where(qMember.id.eq(member.getId()))
					.execute();

			System.out.println("affected Rows : " + affectedRows);

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

	public static void testQueryDSLX() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		try {
			tx.begin();

			QMember qMember = QMember.member;
			QTeam qTeam = QTeam.team;
			List<Tuple> memberList = queryFactory.select(qTeam.name, qMember.age.avg())
					.from(qTeam)
					.join(qTeam.memberList, qMember)
					.groupBy(qTeam.name)
					.having(qMember.age.avg().goe(30)) // goe는 >=를 의미
					.orderBy(qMember.age.avg().desc()).fetch();

			for(Tuple tuple : memberList) {
				String teamName = tuple.get(qTeam.name);
				Double avgAge = tuple.get(qMember.age.avg());
				System.out.println("Team name: " + teamName + ", Member average age: " + avgAge);
			}

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

	public static void testPagingAPIByJPQL() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m ORDER By m.name DESC", Member.class)
					.setFirstResult(10).setMaxResults(20);

			List<Member> memberList = query.getResultList();
			for(Member member : memberList) {
				System.out.println("Member: " + member.getId());
			}

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

	public static void testPagingAPIByQueryDSL() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		try {
			tx.begin();

			QMember qMember = QMember.member;
			List<Member> memberList = queryFactory
					.selectFrom(qMember)
					.orderBy(qMember.id.desc())
					.offset(10)
					.limit(20)
					.fetch();

			for(Member m : memberList) {
				System.out.println("Member Id: " + m.getId());
			}

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

	public static Long getLastMemberId() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		Long lastMemberId = -1L;
		try {
			tx.begin();

			QMember qMember = QMember.member;
			lastMemberId = queryFactory
					.select(qMember.id)
					.from(qMember)
					.orderBy(qMember.id.desc())
					.limit(1)
					.fetchOne();

			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		return lastMemberId;
	}

	public static Long getPagedMembers(Long lastMemberId, int limit) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		List<Member> members = null;
		try {
			tx.begin();

			QMember qMember = QMember.member;
			if(lastMemberId == null) {
				members = queryFactory
						.selectFrom(qMember)
						.orderBy(qMember.id.asc())
						.limit(limit)
						.fetch();
			}
			else {
				members = queryFactory
						.selectFrom(qMember)
						.where(qMember.id.gt(lastMemberId))
						.orderBy(qMember.id.asc())
						.limit(limit)
						.fetch();
			}

			for(Member m : members) {
				System.out.println(m);
			}

			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}

		if (members != null) {
			if (!members.isEmpty()) {

				// member.size() - 1은 member 리스트의 마지막 엘리먼트 인덱스 값이다.
				return members.get(members.size() -1).getId();
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	public static void testPagingAPIWithOffsetByQueryDSL() {
		Long queryedLastMemberId = getLastMemberId();

		int pageSize = 20;
		// 총 페이지 수
		int totalPages = (MEMBER_NUMBERS + pageSize - 1) / pageSize; // 올림 계산

		Long lastMemberId = null;

		for(int currentPage = 1; currentPage <= totalPages; currentPage++) {
			System.out.println("Page " + currentPage + ": ");
			lastMemberId = getPagedMembers(lastMemberId, pageSize);
		}
		if(queryedLastMemberId.equals(lastMemberId)) {
			System.out.println("마지막 페이지입니다.");
		}
	}

	public static void testFetchJoinByJPQL() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			List<Member> query =
					em.createQuery("SELECT m FROM Member m JOIN fetch m.team", Member.class).getResultList();

			for(Member m : query) {
				Team team = m.getTeam();
				System.out.println("Member name= " + m.getName() + ", Team Id= " + team.getId() + ", Name=" + team.getName());
			}

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

	public static void testFetchJoinByQueryDSL() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);

		try {
			tx.begin();

			List<Member> query =
					em.createQuery("SELECT m FROM Member m JOIN fetch m.team", Member.class).getResultList();

			QMember qMember = QMember.member;
			QTeam qteam = QTeam.team;
			List<Member> members = queryFactory
					.selectFrom(qMember)
					.join(qMember.team, qteam).fetchJoin()
					.fetch();

			for(Member m : members) {
				Team team = m.getTeam();
				System.out.println("Member name= " + m.getName() + ", Team Id= " + team.getId() + ", Name=" + team.getName());
			}

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

	public static void testCollectionFetchJoin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		String nameParameter = "team 5";

		try {
			tx.begin();

//			List<Team> teams = em.createQuery("SELECT t FROM Team t JOIN FETCH t.memberList", Team.class).getResultList();

			// One: team
			// Many: member
			// OneToMany join: 중복된 결과값이 발생한다.
//			List<Team> teams =
//					em.createQuery("SELECT t FROM Team t JOIN FETCH t.memberList WHERE t.name = :name", Team.class)
//					.setParameter("name", nameParameter)
//					.getResultList();

			List<Team> teams =
					em.createQuery("SELECT DISTINCT t FROM Team t JOIN FETCH t.memberList WHERE t.name = :name", Team.class)
							.setParameter("name", nameParameter)
							.getResultList();

			for(Team t : teams) {
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.printf("Team id= %d\n", t.getId());
				for(Member m : t.getMemberList()) {
					System.out.printf("Member id= %d, Team name= %s \n", m.getId(), m.getName());
				}
				System.out.println("===============================================================================");
			}

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
}
