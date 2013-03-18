package models.embedded;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Embeddable
public class EmbeddedPostDetails {

	public String textDetails;

	// @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy =
	// "post")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	public List<EmbeddedComment> comments = new ArrayList<EmbeddedComment>();

}
