package controllers;

import java.util.List;

import models.Objeto;
import play.mvc.Controller;
import models.Categoria;
import play.mvc.With;

@With(Seguranca.class)
public class Objetos extends Controller {
	
	public static void form() {
		List<Categoria> categorias = Categoria.findAll();
		render(categorias);
	}

	public static void listar() {
	  List<Objeto> objetos = Objeto.findAll();
	  render(objetos);
	}
	
	public static void salvar(Objeto objeto, Long idCategoria) {
		
		if (idCategoria != null) {
			Categoria categoria = Categoria.findById(idCategoria);
			objeto.categoria = categoria;
			}
		
		objeto.save();
		listar();
	}
	
	public static void deletar(Long id) {
	    Objeto objeto = Objeto.findById(id);
		objeto.delete();
		listar();
	}
	
	public static void editar(Long id) {
		Objeto objeto = Objeto.findById(id);
		
		 List<Categoria> categorias = Categoria.findAll();
		
		renderTemplate("Objetos/form.html", objeto);
	}
}	
