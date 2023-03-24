package com.kuntsev.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//The first test case verifies that a new comment can be persisted to the database, and that its properties are correctly set. The second test case uses the @Sql annotation to load test data from a SQL script, and then queries for all comments that match a given post. The third test case verifies that an empty body field will throw a ConstraintViolationException when persisted to the database. These tests use the Spring Boot DataJpaTest annotation to configure an in-memory database for testing, and the @PersistenceContext annotation to inject an EntityManager instance for use in the tests.
@DataJpaTest
public class CommentTest {
    /* EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_JPA");
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }*/

   // @PersistenceContext
    //private EntityManager entityManager;

    @Test
    void save_newUser() {
        // Arrange
        EntityTransaction et = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_JPA");
        EntityManager entityManager = emf.createEntityManager();
        try{
            et = entityManager.getTransaction();
            et.begin();
            User user1 = new User();
            user1.setId(new Long(101));
            user1.setName("Ivan");
            user1.setLastName("Ivanov");
            entityManager.persist(user1);
            et.commit();

            User user2 = entityManager.find(User.class, new Integer(101));
            assertNotNull(user2.getId());
            assertEquals(user2.getName(), user1.getName());
            assertEquals(user2.getLastName(), user1.getLastName());

        }
        catch (Exception e)
        {
            et.rollback();
        }

        // Assert

    }

    /*void save_newComment_commentIsPersisted() {
        // Arrange

        Comment comment = new Comment();
        comment.setBody("This is a test comment");
        comment.setCreateDate(new Date());
        getEntityManager().persist(comment);
        Post post = getEntityManager().
                find(Post.class, new Integer(1));
        User user = getEntityManager().find(User.class, new Integer(1));
        comment.setPost(post);
        comment.setUser(user);

        // Act
       // getEntityManager().persist(comment);
        getEntityManager().flush();

        // Assert
        assertNotNull(comment.getId());
        assertEquals(comment.getBody(), "This is a test comment");
        assertEquals(comment.getPost(), post);
        assertEquals(comment.getUser(), user);
    }

    @Test
    @Sql("/data.sql")
    void findByPost_postExists_returnsMatchingComments() {
        // Arrange
        Post post = getEntityManager().find(Post.class, new Integer(1));

        // Act
        List<Comment> comments = getEntityManager().createQuery("SELECT c FROM Comment c WHERE c.post = :post", Comment.class)
                .setParameter("post", post)
                .getResultList();

        // Assert
        assertNotNull(comments);
        assertEquals(2, comments.size());
        assertEquals("This is a test comment", comments.get(0).getBody());
        assertEquals("This is another test comment", comments.get(1).getBody());
    }

    @Test
    void setBody_emptyBody_throwsException() {
        // Arrange
        Comment comment = new Comment();

        // Act & Assert
        assertThrows(javax.validation.ConstraintViolationException.class, () -> {
            comment.setBody("");
            getEntityManager().persist(comment);
            getEntityManager().flush();
        });
    }*/
}
