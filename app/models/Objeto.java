package models;

import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Entity;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Objeto extends Model{
	
	public String nome;
	public String material;
	public float altura;
	public float largura;
	public Date data;
	public String historia;
	public boolean objetoVisivel;
	
	@OneToMany (mappedBy="objeto")
	public List<Foto> fotos;
	
	@ManyToOne
	@JoinColumn(name="categoria_id")
	public Categoria categoria; 
	
	@ManyToMany(mappedBy="objetos")
	public List<Colecao> colecoes;
}
