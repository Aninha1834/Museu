package controllers;

import java.io.File;
import java.util.List;


import models.Objeto;
import play.mvc.Controller;
import models.Categoria;
import models.Foto;
import play.mvc.With;

@With(Seguranca.class)
public class Objetos extends Controller {
	
	public static void form() {
		List<Categoria> categorias = Categoria.findAll();
		List<Foto> fotos = Foto.findAll();
		render(categorias, fotos);
	}

	public static void listar() {
	  List<Objeto> objetos = Objeto.findAll();
	  render(objetos);
	}
	
	public static void salvar(Objeto objeto, Long idCategoria, Long idFoto) {
		
	
		
		if (idCategoria != null) {
			Categoria categoria = Categoria.findById(idCategoria);
			objeto.categoria = categoria;
		}
		
		objeto.save();
		editar(objeto.id);
	}
	
	
	
	public static void deletar(Long id) {
	    Objeto objeto = Objeto.findById(id);
		objeto.delete();
		listar();
	}
	
	public static void salvarFoto(Long idFoto, Long idObjeto) {

		Foto foto = Foto.findById(idFoto);
		Objeto objeto = Objeto.findById(idObjeto);
		foto.objeto = objeto;
		foto.save();
		
		editar(idObjeto);
	}
	
	public static void editar(Long id) {
		Objeto objeto = Objeto.findById(id);
		
		 List<Categoria> categorias = Categoria.findAll();
		 
		 List<Foto> fotos = Foto.findAll();
		
		renderTemplate("Objetos/form.html", objeto, categorias, fotos);
	}
}	
