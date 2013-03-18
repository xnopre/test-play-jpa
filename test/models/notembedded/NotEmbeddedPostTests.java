package models.notembedded;

import static junit.framework.Assert.assertEquals;

import java.util.List;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;

import play.db.jpa.JPABase;
import play.test.Fixtures;
import play.test.UnitTest;

public class NotEmbeddedPostTests extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void testCreatePost() {
		NotEmbeddedPost post = new NotEmbeddedPost("post 1", "details 1");

		post.save();

		assertEquals(1, NotEmbeddedPost.findAll().size());
		assertEquals(1, NotEmbeddedPostDetails.findAll().size());
	}

	@Test
	public void testCreateComments() {
		NotEmbeddedPost post = new NotEmbeddedPost("post 2", "details 2");
		post.addComment(new NotEmbeddedComment("p2 c1"));
		post.addComment(new NotEmbeddedComment("p2 c2"));

		post.save();

		assertEquals(1, NotEmbeddedPost.findAll().size());
		assertEquals(1, NotEmbeddedPostDetails.findAll().size());
		assertEquals(2, NotEmbeddedComment.findAll().size());
	}

	@Test
	public void testReload() {
		NotEmbeddedPost post = new NotEmbeddedPost("post 2", "details 2");
		post.addComment(new NotEmbeddedComment("p2 c1"));
		post.addComment(new NotEmbeddedComment("p2 c2"));

		post.save();
		post.em().clear();

		assertEquals("post 2", NotEmbeddedPost.<NotEmbeddedPost> findById(post.id).text);
		assertEquals("details 2", NotEmbeddedPost.<NotEmbeddedPost> findById(post.id).getPostDetails().text);
		assertEquals("p2 c1", NotEmbeddedComment.<NotEmbeddedComment> findAll().get(0).text);
		assertEquals("p2 c2", NotEmbeddedComment.<NotEmbeddedComment> findAll().get(1).text);
	}

	@Test
	public void testUpdateComments() {
		NotEmbeddedPost post = new NotEmbeddedPost("post 3", "details 3");
		post.addComment(new NotEmbeddedComment("p3 c1"));
		post.addComment(new NotEmbeddedComment("p3 c2"));
		post.addComment(new NotEmbeddedComment("p3 c3"));

		post.save();
		post.em().clear();

		post = NotEmbeddedPost.<NotEmbeddedPost> findById(post.id);
		post.text = "post 3 bis";
		post.setPostDetails(new NotEmbeddedPostDetails(post, "xyz"));
		post.addComment(new NotEmbeddedComment("p3 c2 bis"));

		post.save();
		post.em().clear();

		assertEquals("post 3 bis", NotEmbeddedPost.<NotEmbeddedPost> findById(post.id).text);
		assertEquals("p3 c2 bis", NotEmbeddedComment.<NotEmbeddedComment> findAll().get(0).text);
		assertEquals(1, NotEmbeddedPost.findAll().size());
		assertEquals(1, NotEmbeddedPostDetails.findAll().size());
		assertEquals(1, NotEmbeddedComment.findAll().size());
	}

	@Test
	public void testDelete() {
		System.out.println("testDelete debut");
		NotEmbeddedPost post1 = new NotEmbeddedPost("post 4", "details 4");
		post1.addComment(new NotEmbeddedComment("p4 c1"));
		post1.addComment(new NotEmbeddedComment("p4 c1"));
		post1.save();
		NotEmbeddedPost post2 = new NotEmbeddedPost("post 4", "details 4");
		post2.addComment(new NotEmbeddedComment("p4 c1"));
		post2.addComment(new NotEmbeddedComment("p4 c1"));
		post2.save();
		NotEmbeddedPost post3 = new NotEmbeddedPost("post 4", "details 4");
		post3.addComment(new NotEmbeddedComment("p4 c1"));
		post3.save();

		assertEquals(3, NotEmbeddedPost.findAll().size());
		assertEquals(3, NotEmbeddedPostDetails.findAll().size());
		assertEquals(5, NotEmbeddedComment.findAll().size());

		post3.em().clear();

		post2 = NotEmbeddedPost.<NotEmbeddedPost> findById(post2.id);
		post2.delete();

		assertEquals(2, NotEmbeddedPost.findAll().size());
		assertEquals(2, NotEmbeddedPostDetails.findAll().size());
		assertEquals(3, NotEmbeddedComment.findAll().size());

		System.out.println("testDelete fin");
	}
}
