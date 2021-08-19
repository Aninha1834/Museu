package controllers;

import java.io.File;
import java.util.List;


import models.Objeto;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.db.jpa.Blob;
import play.mvc.Controller;
import models.Categoria;
import models.Foto;
import play.mvc.With;

@With(Seguranca.class)
public class Objetos extends Controller {
	
	public static void form() {
		
		Objeto objeto = (Objeto) Cache.get("objeto");
		Cache.clear();
		
		List<Categoria> categorias = Categoria.findAll();
		List<Foto> fotos = Foto.findAll();
		render(categorias, fotos, objeto);
	}

	public static void listar() {
	  List<Objeto> objetos = Objeto.findAll();
	  render(objetos);
	}
	
	public static void salvar(@Valid Objeto objeto, Long idCategoria, File foto) {
		
		
		
		if(validation.hasErrors()) {
			Cache.add("objeto", objeto);
			validation.keep();
			form();
		}
		
		if (idCategoria != null) {
			Categoria categoria = Categoria.findById(idCategoria);
			objeto.categoria = categoria;
		}
		
		if (foto != null) {
			Foto f = new Foto(foto.getName());
			f.save();
			
			objeto.fotos.add(f);
			
			File dest = new File("./uploads/" + foto.getName());
			
			if (dest.exists()) {
				dest.delete();
			}
	
			foto.renameTo(dest);
		}
		objeto.save();
		editar(objeto.id);
	}
	
	
	
	public static void deletar(Long id) {
	    Objeto objeto = Objeto.findById(id);
		objeto.delete();
		listar();
	}
	
	
	
	public static void editar(Long id) {
		Objeto objeto = Objeto.findById(id);
		
		 List<Categoria> categorias = Categoria.findAll();
		 
		 List<Foto> fotos = Foto.findAll();
		
		renderTemplate("Objetos/form.html", objeto, categorias, fotos);
	}
	
}	
