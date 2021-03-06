package controllers;

import play.libs.Crypto;
import play.mvc.Controller;
import models.Usuario;

public class Login extends Controller{
	
	
	public static void teste() {
		Usuario u = new Usuario();
		u.email = "museu.admin@gmail.com";
		u.senha = "muvje123";
		u.nome = "Administrador";
		u.cpf =  "111.111.111-22";
		u.nivel = 1;
		u.setSenha();
		u.save();
		
		form();
	}
	
	public static void form() {
		render();
	}
	
	public static void logar(String email, String senha) {
		
		Usuario usu = Usuario.find("email = ?1 and senha = ?2 ", email, Crypto.passwordHash(senha)).first();
		
		if(usu == null) {
			flash.error("Usuário ou senha inválida");
			form();
		}else {
		
			session.put("usuario.email", usu.email);
			session.put("usuario.nome", usu.nome);
			session.put("usuario.id", usu.id);
			session.put("usuario.nivel", usu.nivel);
			
//			if (usu.fotoPerfilAdmin.exists()) {
//				session.put("adminTemFoto", true);	
//			}
			
			Usuarios.inicio();
		}
		
	}
	
	public static void sair() {
		session.clear();
		Login.form();
	}

}

