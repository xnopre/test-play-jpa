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

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	public PostDetails postDetails;

	public Post(String text, String details) {
		this.text = text;
		postDetails = new PostDetails(details);
	}
}
