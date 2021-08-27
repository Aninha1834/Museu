package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

	@OneToOne
	@JoinColumn(name="Foto_id")
	public Foto fotoCapa;
	
	public String descricao;
	
	@ManyToMany
	@JoinTable(name="colecao_objeto")
	public List<Objeto> objetos;
	
	public Colecao () {
		objetos = new ArrayList<>();
	}
	
}
