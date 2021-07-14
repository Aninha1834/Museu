package controllers;

import java.util.List;
import models.Usuario;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Usuarios extends Controller {
	
	public static void form() {
		render();
	}
	
	public static void inicio() {
		render();

	}
		
	public static void listar() {
	  List<Usuario> usuarios = Usuario.findAll();
	  render(usuarios);
	}
	
	public static void salvar(Usuario usu) {
		usu.save();
		flash.success("Salvo com sucesso");
		inicio();
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
	
	public static void galeria() {
		render();
	}
	
	public static void contatos() {
		render();
	}
}
