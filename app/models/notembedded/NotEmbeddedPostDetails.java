package models.notembedded;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class NotEmbeddedPostDetails extends Model {

	public String text;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "postDetails")
	public List<NotEmbeddedComment> comments = new ArrayList<NotEmbeddedComment>();

	@ManyToOne
	private NotEmbeddedPost post;

	public NotEmbeddedPostDetails(NotEmbeddedPost post, String text) {
		this.post = post;
		this.text = text;
	}

}
