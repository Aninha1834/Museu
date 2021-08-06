package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Foto extends Model{

	public String nome;
	public Blob fotoObjeto;
	
	@ManyToOne
	@JoinColumn(name="objeto_id")
	public Objeto objeto;
}
