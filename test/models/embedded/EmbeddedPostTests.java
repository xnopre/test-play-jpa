package models.embedded;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;

import play.db.jpa.JPABase;
import play.test.Fixtures;
import play.test.UnitTest;

public class EmbeddedPostTests extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void testCreatePost() {
		EmbeddedPost post = new EmbeddedPost("post 1", "details 1");

		post.save();

		assertEquals(1, EmbeddedPost.findAll().size());
	}

	@Test
	public void testCreateComments() {
		EmbeddedPost post = new EmbeddedPost("post 2", "details 2");
		post.addComment(new EmbeddedComment("p2 c1"));
		post.addComment(new EmbeddedComment("p2 c2"));

		post.save();

		assertEquals(1, EmbeddedPost.findAll().size());
		assertEquals(2, EmbeddedComment.findAll().size());
	}

	@Test
	public void testReload() {
		EmbeddedPost post = new EmbeddedPost("post 2", "details 2");
		post.addComment(new EmbeddedComment("p2 c1"));
		post.addComment(new EmbeddedComment("p2 c2"));

		post.save();
		post.em().clear();

		assertEquals("post 2", EmbeddedPost.<EmbeddedPost> findById(post.id).text);
		assertEquals("details 2", EmbeddedPost.<EmbeddedPost> findById(post.id).postDetails.textDetails);
		assertEquals("p2 c1", EmbeddedComment.<EmbeddedComment> findAll().get(0).text);
		assertEquals("p2 c2", EmbeddedComment.<EmbeddedComment> findAll().get(1).text);
	}

	@Test
	public void testUpdateComments() {
		EmbeddedPost post = new EmbeddedPost("post 3", "details 3");
		post.addComment(new EmbeddedComment("p3 c1"));
		post.addComment(new EmbeddedComment("p3 c2"));
		post.addComment(new EmbeddedComment("p3 c3"));

		post.save();
		post.em().clear();
		post = EmbeddedPost.<EmbeddedPost> findById(post.id);

		post.text = "post 3 bis";
		post.postDetails.comments.clear();
		post.addComment(new EmbeddedComment("p3 c2 bis"));

		post.save();
		post.em().clear();
		post = EmbeddedPost.<EmbeddedPost> findById(post.id);

		assertEquals("post 3 bis", EmbeddedPost.<EmbeddedPost> findById(post.id).text);
		assertEquals("p3 c2 bis", EmbeddedComment.<EmbeddedComment> findAll().get(0).text);
		assertEquals(1, EmbeddedPost.findAll().size());
		assertEquals(1, EmbeddedComment.findAll().size());
	}

	@Test
	public void testDelete() {
		EmbeddedPost post1 = new EmbeddedPost("post 4", "details 4");
		post1.addComment(new EmbeddedComment("p4 c1"));
		post1.addComment(new EmbeddedComment("p4 c1"));
		post1.save();
		EmbeddedPost post2 = new EmbeddedPost("post 4", "details 4");
		post2.addComment(new EmbeddedComment("p4 c1"));
		post2.addComment(new EmbeddedComment("p4 c1"));
		post2.save();
		EmbeddedPost post3 = new EmbeddedPost("post 4", "details 4");
		post3.addComment(new EmbeddedComment("p4 c1"));
		post3.save();

		assertEquals(3, EmbeddedPost.findAll().size());
		assertEquals(5, EmbeddedComment.findAll().size());

		post3.em().clear();

		post2 = EmbeddedPost.<EmbeddedPost> findById(post2.id);
		post2.delete();

		assertEquals(2, EmbeddedPost.findAll().size());
		assertEquals(3, EmbeddedComment.findAll().size());
	}
}
