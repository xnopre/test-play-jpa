package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Post extends Model {

	public String text;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Comment> comments = new ArrayList<Comment>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostDetails> listPostDetails = new ArrayList<PostDetails>();

	public Post(String text, String details) {
		this.text = text;
		setPostDetails(new PostDetails(details));
	}

	public void setPostDetails(PostDetails postDetails) {
		storeAlwaysOnlyOneInstance(postDetails);
	}

	public PostDetails getPostDetails() {
		return listPostDetails.get(0);
	}

	private void storeAlwaysOnlyOneInstance(PostDetails postDetails) {
		listPostDetails.clear();
		listPostDetails.add(postDetails);
	}
}
