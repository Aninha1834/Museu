package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Entity;

import play.data.validation.InPast;
import play.data.validation.Min;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Objeto extends Model{
	
	@Required(message="O campo nome é obrigatório")
	@MinSize(value=3, message="O nome deve possuir no mínimo 3 caracteres")
	public String nome;
	
	@Required(message="O campo material é obrigatório")
	@MinSize(value=3, message="O nome do material teve ter no mínimo 3 caracteres")
	public String material;
	
	public float altura;
	
	public float largura;
	
	@InPast(message="A data tem que estar no passado")
	public Date data;
	
	@Required(message="O campo história é obrigatório")
	public String historia;
	
	@OneToMany 
	@JoinColumn (name="objeto_id")
	public List<Foto> fotos;

	@ManyToOne
	@JoinColumn(name="categoria_id")
	public Categoria categoria; 
	
	@ManyToMany(mappedBy="objetos")
	public List<Colecao> colecoes;
	
	public Boolean visivel;
	
	public Objeto() {
		this.colecoes = new ArrayList<>();
		this.fotos = new ArrayList<Foto>();
	}
	

}
