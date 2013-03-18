package models.notembedded;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import play.db.jpa.Model;

@Entity
public class NotEmbeddedPost extends Model {

	public String text;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
	private List<NotEmbeddedPostDetails> listPostDetails = new ArrayList<NotEmbeddedPostDetails>();

	public NotEmbeddedPost(String text, String details) {
		this.text = text;
		setPostDetails(new NotEmbeddedPostDetails(this, details));
	}

	public void setPostDetails(NotEmbeddedPostDetails postDetails) {
		storeAlwaysOnlyOneInstance(postDetails);
	}

	public NotEmbeddedPostDetails getPostDetails() {
		return listPostDetails.get(0);
	}

	private void storeAlwaysOnlyOneInstance(NotEmbeddedPostDetails postDetails) {
		listPostDetails.clear();
		listPostDetails.add(postDetails);
	}

	public void addComment(NotEmbeddedComment comment) {
		getPostDetails().comments.add(comment);
		comment.postDetails = getPostDetails();
	}
}
