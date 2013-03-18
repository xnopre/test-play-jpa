package models.embedded;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class EmbeddedComment extends Model {

	public String text;

	// @ManyToOne
	// public EmbeddedPost post;

	public EmbeddedComment(String text) {
		this.text = text;
	}
}
