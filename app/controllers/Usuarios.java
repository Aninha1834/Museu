package controllers;

import java.util.List;

import models.Categoria;
import models.Colecao;
import models.Foto;
import models.Objeto;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.modules.paginate.ValuePaginator;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Usuarios extends Controller {
	
	
	public static void form() {
		
		Usuario usu = (Usuario) Cache.get("usu");
		Cache.clear();
		render(usu);
	}
	
	public static void inicio() {
		render();

	}
		
	public static void listar() {
		String busca = params.get("busca");

		List<Usuario> lista;
		if (busca == null) {
			lista = Usuario.findAll();
		} else {
		   lista = Usuario.find("nome like ?1 or email like ?1 ",
				   "%"+busca+"%").fetch();
		}
		
		ValuePaginator listaPaginada = new ValuePaginator(lista);
		listaPaginada.setPageSize(5);
		
		render(listaPaginada, busca);

	    }
	

	
	public static void deletar(Long id) {
	    Usuario usu = Usuario.findById(id);
		usu.delete();
		flash.success("Deletado com sucesso");
		listar();
	}
	public static void editar(Long id) {
		Usuario usu = Usuario.findById(id);
		renderTemplate("Usuarios/form.html", usu);
	}
	
	public static void salvar(@Valid Usuario usu, String senha) {
		
		if (usu.id == null) {
			
			if(validation.hasErrors()) {
				Cache.add("usu", usu);
				validation.keep();
				form();
			}
			
			usu.setSenha();
			
		} else {
			
			if (usu.senha.equals("")) {
				usu.senha = senha;
			} else {
				if(validation.hasErrors()) {
					Cache.add("usu", usu);
					validation.keep();
					form();
				}
				usu.setSenha();
			}
		}
		
		usu.save();
		flash.success("Salvo com sucesso");
		listar();
	}
	
	public static void galeria() {
		List<Colecao> colecoes = Colecao.findAll();
		render(colecoes);
		
	}
	
	public static void contatos() {
		render();
	}
	
//	public static void exposicaoObjetos() {
//		renderTemplate("Usuarios/exposicaoObjetos");
//	}
	
}
