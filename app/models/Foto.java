package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Foto extends Model{
	
	public Foto(String nome) {
		nomeFoto = nome;
	}

	public String nomeFoto;
}
