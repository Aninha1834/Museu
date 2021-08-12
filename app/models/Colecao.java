package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Colecao extends Model{
	
	@Required(message="O campo nome é obrigatório")
	@MinSize(value=3, message="O nome deve possuir no mínimo 3 caracteres")
	@Unique(message="A coleção já existe")
	public String nome;
	public Blob fotoCapa;
	public boolean colecaoVisivel;
	
	@ManyToMany
	@JoinTable(name="colecao_objeto")
	public List<Objeto> objetos;

}
