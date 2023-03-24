package com.kuntsev;

import com.kuntsev.model.Comment;
import com.kuntsev.model.Post;
import com.kuntsev.model.User;
import com.kuntsev.service.CommentService;
import com.kuntsev.service.PostService;
import com.kuntsev.service.UserService;
import org.junit.Assert;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTests {


    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(CommentTests.class);
    @Test
    public void contextLoads() {
        /*Post post1 = new Post();
        post1.setUser(userService.findByUsername("user").get());
        post1.setBody("bla-bla-bla");
        post1.setTitle("Title_bla");
        postService.save(post1);
*/
        Post p1 = postService.findForId(new Long(1)).get();
        p1.setTitle("very interesting title");
        postService.save(p1);

        Post p1_copy = postService.findForId(p1.getId()).get();

        Assert.assertEquals(p1_copy.getTitle(), "very interesting title");
        logger.info("p1.getTitle(): " + p1.getTitle());
    }

    @Test
    public  void save_newComment_commentIsPersisted() {
        // Arrange

        Comment comment = new Comment();
        comment.setBody("This is a test comment");
        comment.setCreateDate(new Date());
        Post post = postService.findForId(new Long(1)).get();
        User user = userService.findByUsername("user").get();
        comment.setPost(post);
        comment.setUser(user);

        // Act
        commentService.save(comment);

        // Assert
        Assert.assertNotNull(comment.getId());
        Assert.assertEquals(comment.getBody(), "This is a test comment");
        Assert.assertEquals(comment.getPost(), post);
        Assert.assertEquals(comment.getUser(), user);
    }

    @Test
    public void setBody_emptyBody_throwsException() {
        // Arrange
        Comment comment = new Comment();

        // Act & Assert
        Assertions.assertThrows(javax.validation.ConstraintViolationException.class, () -> {
            comment.setBody("");
            commentService.save(comment);
        });
    }


}
