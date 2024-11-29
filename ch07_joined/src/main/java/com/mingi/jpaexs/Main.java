package com.mingi.jpaexs;

import java.util.List;

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
			
			Album album = new Album();
			album.setName("Album1");
			album.setPrice(5000);
			album.setArtist("Song");
			em.persist(album);
			
			Book book = new Book();
			book.setName("Book1");
			book.setPrice(15000);
			book.setAuthor("Kim");
			book.setIsbn("12345");
			em.persist(book);
			
			Movie movie = new Movie();
			movie.setName("Book1");
			movie.setPrice(12000);
			movie.setDirector("Ku");
			em.persist(movie);
			
			em.flush();
			em.clear();
			
//			Book gotBook = em.find(Book.class, book.getId());
//			Album gotAlbum = em.find(Album.class, album.getId());
//			Movie gotMovie = em.find(Movie.class, movie.getId());
			
			Item gotItem = em.find(Item.class, book.getId()); // UNION ALL 쿼리를 수행
			
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
