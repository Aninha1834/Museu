package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		List<Objeto> objetos = Objeto.findAll();
		List<Colecao> colecoes = Colecao.findAll();
		List<Categoria> categorias = Categoria.findAll();
		
		render(objetos, colecoes, categorias);
		

	}
		
	public static void listar() {
		String busca = params.get("busca");

		List<Usuario> lista;
		if (busca == null) {
			lista = Usuario.findAll();
		} else {
		   lista = Usuario.find("lower(nome) like ?1 or email like ?1 ",
				   "%"+busca.toLowerCase()+"%").fetch();
		}
		
		ValuePaginator listaPaginada = new ValuePaginator(lista);
		listaPaginada.setPageSize(3);
		
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
		String busca = params.get("busca");
		
		List<Colecao> colecoes;
		List<Objeto> objetos = Objeto.find("visivel = true").fetch();
		List<Colecao> colecoesTemporarias = new ArrayList<>();
		List<Colecao> colecoesPermanentes = new ArrayList<>();
		List<Categoria> categorias = Categoria.findAll();
		Map<Long, Integer> nObjVisi = new HashMap<Long, Integer>();
		
		if (busca == null) {
			colecoes = Colecao.find("visivel = ?1", true).fetch();
		} else {
		   colecoes = Colecao.find("lower(nome) like ?1 and visivel = true",
				   "%"+busca.toLowerCase()+"%").fetch();
		}
		
		for (Colecao col: colecoes) {
			if (col.exposicaoPermanente == true) {
				colecoesPermanentes.add(col);
			} else  {
				colecoesTemporarias.add(col);
			}
		}
		
		
		for (Colecao colecao: colecoes) {
			int i = 0;
			for (Objeto objeto: colecao.objetos) {
				if (objeto.visivel == true) {
					i++;
				}
			}
			nObjVisi.put(colecao.getId(), i);
		}
		
		System.out.println(categorias.get(0));
		System.out.print(colecoes.get(0));
		
		render(colecoesPermanentes, colecoesTemporarias, objetos, busca, categorias, nObjVisi);
	
	}
	
	public static void contatos() {
		render();
	}
	
//	public static void exposicaoObjetos() {
//		renderTemplate("Usuarios/exposicaoObjetos");
//	}
	
}
