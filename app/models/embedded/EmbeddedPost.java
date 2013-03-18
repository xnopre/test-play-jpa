package models.embedded;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class EmbeddedPost extends Model {

	public String text;

	@Embedded
	public EmbeddedPostDetails postDetails = new EmbeddedPostDetails();

	public EmbeddedPost(String text, String textDetails) {
		this.text = text;
		postDetails.textDetails = textDetails;
	}

	public void addComment(EmbeddedComment comment) {
		postDetails.comments.add(comment);
		// comment.post = this;
	}
}
