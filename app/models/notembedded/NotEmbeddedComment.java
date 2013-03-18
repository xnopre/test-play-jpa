package models.notembedded;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class NotEmbeddedComment extends Model {

	public String text;

	@ManyToOne
	public NotEmbeddedPostDetails postDetails;

	public NotEmbeddedComment(String text) {
		this.text = text;
	}
}
