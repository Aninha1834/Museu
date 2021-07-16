package controllers;

import java.util.List;

import models.Objeto;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Objetos extends Controller {
	
	public static void form() {
		render();
	}

	public static void listar() {
	  List<Objeto> objetos = Objeto.findAll();
	  render(objetos);
	}
	
	public static void salvar(Objeto objeto) {
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
		renderTemplate("Objetos/form.html", objeto);
	}
}	
