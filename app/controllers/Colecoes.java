package controllers;

import java.util.List;

import models.Colecao;
import models.Objeto;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Colecoes extends Controller{
	
	public static void form() {
		
		Colecao colecao = (Colecao) Cache.get("colecao");
		Cache.clear();
		render(colecao);
		
	}
	
	public static void listar() {
		List<Colecao> colecoes = Colecao.findAll();
		render(colecoes);
	}
	
	public static void salvar(@Valid Colecao colecao, Long idObjeto) {

		if(validation.hasErrors()) {
			Cache.add("colecao", colecao);
			validation.keep();
			form();
		}
		
		if (idObjeto != null) {
			Objeto obj = Objeto.findById(idObjeto);
			if (colecao.objetos.contains(obj) == false) {
				colecao.objetos.add(obj);
			}
		}
		
		colecao.save();
		editar(colecao.id);
	}
	
	public static void deletar(Long id) {
		Colecao colecao = Colecao.findById(id);
		colecao.delete();
		listar();
	}
	
	public static void editar(Long id) {
		Colecao colecao = Colecao.findById(id);
		
		
		//List<Objeto> objetos = Objeto.findAll();
		
		List<Objeto> objetos = Objeto.find("select ob from Objeto ob, Colecao col where col.id = ?1 "
				+ " and ob not member of col.objetos "
				+ " order by ob.nome ", colecao.id).fetch();
		
		renderTemplate("Colecoes/form.html", colecao, objetos);
	}
	
	public static void removerObjeto (Long idColecao, Long idObjeto) {
		Objeto obj = Objeto.findById(idObjeto);
		Colecao colec = Colecao.findById(idColecao);
		
		colec.objetos.remove(obj);
		
		colec.save();
		
		editar(colec.id);
	}

}
