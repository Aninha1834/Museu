package controllers;

import java.util.List;

import models.Usuario;
import play.mvc.Controller;

public class Usuarios extends Controller {
	
	public static void form() {
		render();
	}

	public static void listar() {
	  List<Usuario> usuarios = Usuario.findAll();
	  render(usuarios);
	}
	
	public static void salvar(Usuario usu) {
		usu.save();
		listar();
	}
	
	public static void deletar(Long id) {
	    Usuario usu = Usuario.findById(id);
		usu.delete();
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
