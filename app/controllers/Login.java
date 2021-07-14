package controllers;

import play.libs.Crypto;
import play.mvc.Controller;
import models.Usuario;

public class Login extends Controller{
	
	
	public static void teste() {
		Usuario u = new Usuario();
		u.email = "admin@admin.com";
		u.senha = "123456";
		u.save();
		
		form();
	}
	
	public static void form() {
		render();
	}
	
	public static void logar(String email, String senha) {
		
		Usuario usu = Usuario.find("email = ?1 and senha = ?2 ", email, Crypto.passwordHash(senha) ).first();
		
		if(usu == null) {
			flash.error("Senha inválida");
			form();
		}else {
			session.put("usuario.email", usu.email);
			
			Usuarios.inicio();
		}
		
	}
	
	public static void sair() {
		session.clear();
		Login.form();
	}

}

