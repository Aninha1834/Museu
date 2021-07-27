package controllers;

import java.util.List;

import models.Categoria;
import models.Objeto;
import play.mvc.Controller;

public class Categorias extends Controller {
	
	public static void form() {
		render();
	}
	
	
	public static void listar() {
		List<Categoria> categorias = Categoria.findAll();
	    render(categorias);
	}
	
	public static void salvar(Categoria categoria) {
		categoria.save();
		listar();
	}
	
	public static void deletar(Long id) {
	    Categoria categoria = Categoria.findById(id);
		categoria.delete();
		listar();
	}
	
	public static void editar(Long id) {
		Categoria categoria = Categoria.findById(id);
		renderTemplate("Categorias/form.html", categoria);
	}
	
}