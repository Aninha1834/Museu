package models;

import javax.persistence.Entity;

import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import play.libs.Crypto;

@Entity
public class Usuario extends Model{
	
	@Required(message="O campo nome é obrigatório")
	@MinSize(value=3, message="O nome deve possuir no mínimo 3 caracteres")
	public String nome;
	
	@Required(message="O campo cpf é obrigatório")
	@Unique(message="O cpf já existe")
	public String cpf;
	
	@Required(message="O campo email é obrigatório")
	@Email(message="Email inválido")
	@Unique(message="O email já existe")
	public String email;
	
	@Required(message="O campo senha é obrigatório")
	@MinSize(value=4, message="A senha deve possuir no mínimo 4 caracteres")
	public String senha;
	
	public int nivel;
	
//	public Blob fotoPerfilAdmin;
	
	public void setSenha() {
		this.senha = Crypto.passwordHash(senha);
		
	}
}
