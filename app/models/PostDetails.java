package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class PostDetails extends Model {

	public String text;

	public PostDetails(String text) {
		this.text = text;
	}

}
