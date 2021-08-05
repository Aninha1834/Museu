package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Categoria extends Model{
	
	public String nome;
	public boolean categoriaVisivel;
	
	@OneToMany(mappedBy="categoria")
	public List<Objeto> objetos;

}
