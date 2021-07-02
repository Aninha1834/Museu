package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Objeto extends Model{
	
	public String nome;
	public String categoria;
	public String material;
	public float altura;
	public float largura;
	public Date data;
	public String historia;
}
