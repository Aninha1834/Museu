package controllers;

import java.util.List;

import models.Foto;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;

public class Fotos extends Controller{
	
	public static void form() {
		
		Foto foto = (Foto) Cache.get("foto");
		Cache.clear();
		render(foto);
	}
	
		
	public static void listar() {
	  List<Foto> fotos = Foto.findAll();
	  render(fotos);
	}
	
	public static void salvar(@Valid Foto foto) {
		
		if(validation.hasErrors()) {
			Cache.add("foto", foto);
			validation.keep();
			form();
		}
		
		foto.save();
		flash.success("Salvo com sucesso");
		listar();
	}
	
	public static void deletar(Long id) {
	    Foto foto = Foto.findById(id);
		foto.delete();
		flash.success("Deletado com sucesso");
		listar();
	}
	
	public static void editar(Long id) {
		Foto foto = Foto.findById(id);
		renderTemplate("Fotos/form.html", foto);
	}
	
	public static void renderFotoObjeto(Long idFoto) {
		Foto foto = Foto.findById(idFoto);
		response.setContentTypeIfNotSet(foto.fotoObjeto.type());
		renderBinary(foto.fotoObjeto.get());
	}
	
}
