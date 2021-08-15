package controllers;

import java.util.List;

import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Usuarios extends Controller {
	
	
	public static void form() {
		
		Usuario usu = (Usuario) Cache.get("usu");
		Cache.clear();
		render(usu);
	}
	
	public static void inicio() {
		render();

	}
		
	public static void listar() {
	  List<Usuario> usuarios = Usuario.findAll();
	  render(usuarios);
	}
	

	
	public static void deletar(Long id) {
	    Usuario usu = Usuario.findById(id);
		usu.delete();
		flash.success("Deletado com sucesso");
		listar();
	}
	public static void editar(Long id) {
		Usuario usu = Usuario.findById(id);
		renderTemplate("Usuarios/form.html", usu);
	}
	
	public static String senhaAtual = null;
	public static void salvar(@Valid Usuario usu) {

		
		
		if (usu.id == null) {
			
			
			
			if(validation.hasErrors()) {
				Cache.add("usu", usu);
				validation.keep();
				form();
			}
			
			usu.setSenha();
			senhaAtual = usu.senha;
		} else {
			
		
			if (usu.senha.equals("")) {
				usu.senha = senhaAtual;
			} else {
				if(validation.hasErrors()) {
					Cache.add("usu", usu);
					validation.keep();
					form();
				}
				usu.setSenha();
			}
		}
		
		usu.save();
		flash.success("Salvo com sucesso");
		listar();
	}
	
//	public static void salvar(@Valid Usuario usu, String senha) {
//		System.out.println("Senha atual criptografada: " + usu.senha);
//		
//		if (senha.equals("") == false) {
//			System.out.println("Senha passada no form: " + senha);
//			usu.senha = senha;
//			
//			System.out.println("Nova senha do admin: " + usu.senha);
//			usu.setSenha();
//			if(validation.hasErrors()) {
//				Cache.add("usu", usu);
//				validation.keep();
//				form();
//			}
//
//			System.out.println("Nova senha criptografada: " + usu.senha);
//			usu.save();
//			flash.success("Salvo com sucesso");
//			listar();
//			
//		} else {
//			if(validation.hasErrors()) {
//				Cache.add("usu", usu);
//				validation.keep();
//				System.out.println("Segundo validation: ");
//				form();
//			}
//			usu.save();
//			flash.success("Salvo com sucesso");
//			listar();
//		}
//	}
	
	public static void galeria() {
		render();
	}
	
	public static void contatos() {
		render();
	}
	
	public static void renderFotoPerfilAdmin(Long idAdmin) {
		Usuario admin = Usuario.findById(idAdmin);
		response.setContentTypeIfNotSet(admin.fotoPerfilAdmin.type());
		renderBinary(admin.fotoPerfilAdmin.get());
	}
}
