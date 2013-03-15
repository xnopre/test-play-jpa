package models;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

import play.db.jpa.JPABase;
import play.test.Fixtures;
import play.test.UnitTest;

public class JpaTests extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void testCreatePost() {
		Post post = new Post("post 1", "details 1");

		post.save();

		assertEquals(1, Post.findAll().size());
		assertEquals(1, PostDetails.findAll().size());
	}

	@Test
	public void testCreateComments() {
		Post post = new Post("post 2", "details 2");
		post.comments.add(new Comment("p2 c1"));
		post.comments.add(new Comment("p2 c2"));

		post.save();

		assertEquals(1, Post.findAll().size());
		assertEquals(1, PostDetails.findAll().size());
		assertEquals(2, Comment.findAll().size());
	}

	@Test
	public void testReload() {
		Post post = new Post("post 2", "details 2");
		post.comments.add(new Comment("p2 c1"));
		post.comments.add(new Comment("p2 c2"));

		post.save();
		post.em().clear();

		assertEquals("post 2", Post.<Post> findById(post.id).text);
		assertEquals("details 2", Post.<Post> findById(post.id).postDetails.text);
		assertEquals("p2 c1", Comment.<Comment> findAll().get(0).text);
		assertEquals("p2 c2", Comment.<Comment> findAll().get(1).text);
	}

	@Test
	public void testUpdateComments() {
		Post post = new Post("post 3", "details 3");
		post.comments.add(new Comment("p3 c1"));
		post.comments.add(new Comment("p3 c2"));
		post.comments.add(new Comment("p3 c3"));

		post.save();
		post.em().clear();

		post = Post.<Post> findById(post.id);
		post.text = "post 3 bis";
		post.postDetails = new PostDetails("xyz");
		post.comments.get(1).text = "p3 c2 bis";
		post.comments.remove(post.comments.get(2));

		post.save();
		post.em().clear();

		assertEquals("post 3 bis", Post.<Post> findById(post.id).text);
		assertEquals("p3 c2 bis", Comment.<Comment> findAll().get(1).text);
		assertEquals(1, Post.findAll().size());
		assertEquals(1, PostDetails.findAll().size());
		assertEquals(2, Comment.findAll().size());
	}

	@Test
	public void testDelete() {
		Post post1 = new Post("post 4", "details 4");
		post1.comments.add(new Comment("p4 c1"));
		post1.comments.add(new Comment("p4 c1"));
		post1.save();
		Post post2 = new Post("post 4", "details 4");
		post2.comments.add(new Comment("p4 c1"));
		post2.comments.add(new Comment("p4 c1"));
		post2.save();
		Post post3 = new Post("post 4", "details 4");
		post3.comments.add(new Comment("p4 c1"));
		post3.save();

		assertEquals(3, Post.findAll().size());
		assertEquals(3, PostDetails.findAll().size());
		assertEquals(5, Comment.findAll().size());

		post3.em().clear();

		Post.<Post> findById(post2.id).delete();

		assertEquals(2, Post.findAll().size());
		assertEquals(2, PostDetails.findAll().size());
		assertEquals(3, Comment.findAll().size());

	}
}
