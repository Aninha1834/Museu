package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class Categoria extends Model{
	
	@Required(message="O campo nome é obrigatório")
	@Unique(message="A categoria digitada já existe")
	@MinSize(value=3, message="O nome deve possuir no mínimo 3 caracteres")
	public String nome;
	
	@OneToMany(mappedBy="categoria")
	public List<Objeto> objetos;
	
	public Foto fotoCapa;
	
}
