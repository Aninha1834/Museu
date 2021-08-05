package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Colecao extends Model{
	
	public String nome;
	public Blob fotoCapa;
	public boolean colecaoVisivel;
	
	@ManyToMany
	@JoinTable(name="colecao_objeto")
	public List<Objeto> objetos;

}
