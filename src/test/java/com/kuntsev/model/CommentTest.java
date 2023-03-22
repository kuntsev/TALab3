package com.kuntsev.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//The first test case verifies that a new comment can be persisted to the database, and that its properties are correctly set. The second test case uses the @Sql annotation to load test data from a SQL script, and then queries for all comments that match a given post. The third test case verifies that an empty body field will throw a ConstraintViolationException when persisted to the database. These tests use the Spring Boot DataJpaTest annotation to configure an in-memory database for testing, and the @PersistenceContext annotation to inject an EntityManager instance for use in the tests.
@DataJpaTest
public class CommentTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void save_newComment_commentIsPersisted() {
        // Arrange
        com.kuntsev.model.Comment comment = new Comment();
        comment.setBody("This is a test comment");
        comment.setCreateDate(new Date());
        Post post = entityManager.find(Post.class, new Integer(1));
        User user = entityManager.find(User.class, new Integer(1));
        comment.setPost(post);
        comment.setUser(user);

        // Act
        entityManager.persist(comment);
        entityManager.flush();

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
        Post post = entityManager.find(Post.class, new Integer(1));

        // Act
        List<Comment> comments = entityManager.createQuery("SELECT c FROM Comment c WHERE c.post = :post", Comment.class)
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
            entityManager.persist(comment);
            entityManager.flush();
        });
    }
}
