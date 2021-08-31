package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller{
	
	@Before(unless={"Usuarios.contatos","Usuarios.galeria", "Colecoes.exibirObjetos", "Colecoes.visualizarObjeto"})
	static void verificar() {
		
		if (session.contains("usuario.email") == false) {
			Login.form();
		}
		
	}

}
