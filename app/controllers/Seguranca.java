package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller{
	
	@Before(unless={"Usuarios.contatos","Usuarios.galeria", "Colecoes.exibirObjetos", "Colecoes.visualizarObjeto", "Objetos.exibirObjetos"})
	static void verificar() {
		
		if (session.contains("usuario.email") == false) {
			Login.form();
		}
		
	}
	
	@Before(only={"Usuarios.form","Usuarios.salvar","Usuarios.deletar","Usuarios.editar","Usuarios.listar"})
	static void permissoes() {
		if (session.get("usuario.nivel").equals("1") == false) {
			renderText("Acesso negado");
		}
	}

}
