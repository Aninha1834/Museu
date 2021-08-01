package controllers;

import java.util.List;

import models.Colecao;
import play.mvc.Controller;

public class Colecoes extends Controller{
	
	public static void form() {
		render();
	}
	
	public static void listar() {
		List<Colecao> colecoes = Colecao.findAll();
		render(colecoes);
	}
	
	public static void salvar(Colecao colecao) {
		colecao.save();
		listar();
	}
	
	public static void deletar(Long id) {
		Colecao colecao = Colecao.findById(id);
		colecao.delete();
		listar();
	}
	
	public static void editar(Long id) {
		Colecao colecao = Colecao.findById(id);
		renderTemplate("Colecoes/form.html", colecao);
	}

}
