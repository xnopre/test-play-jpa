package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Comment extends Model {

	public String text;

	public Comment(String text) {
		super();
		this.text = text;
	}
}
