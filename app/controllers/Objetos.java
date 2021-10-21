package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import models.Objeto;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.db.jpa.Blob;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import models.Categoria;
import models.Colecao;
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
		String busca = params.get("busca");

		List<Objeto> lista;
		if (busca == null) {
			lista = Objeto.findAll();
		} else {
		   lista = Objeto.find("select o from Objeto o "
				   + " where lower(o.nome) like ? " ,
				   "%"+busca.toLowerCase()+"%").fetch();
		}
		
		ValuePaginator listaPaginada = new ValuePaginator(lista);
		listaPaginada.setPageSize(3);
		
		render(listaPaginada, busca);

	    }
	
	public static void salvar(@Valid Objeto objeto, Long idCategoria, File foto, String visivel) {
		
		System.out.println("VISIBILIDADE: " + visivel);
		
		if (visivel == null) {
			objeto.visivel = false;
		} else  {
			objeto.visivel = true;
		}
		
		
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
			System.out.println("nome foto: " + f.nomeFoto);
			f.save();
			
			objeto.fotos.add(f);
			
			
			File dest = new File("./uploads/" + foto.getName());
			

	
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
	
	public static void excluirFoto (Long idFoto, Long idOjeto) {
		Objeto objeto = Objeto.findById(idOjeto);
		Foto foto = Foto.findById(idFoto);
		List<Foto> fotos = objeto.fotos;
		
		for (int i = 0; i<fotos.size(); i++) {
			if (fotos.get(i).nomeFoto.equals(foto.nomeFoto)) {
				objeto.fotos.remove(i);
			}
		}
		objeto.save();
		foto.delete();
		editar(objeto.getId());
		
	}
	
	public static void exibirObjetos(Long idCategoria, Long idColecao, String titulo, String descricao) {
		List<Objeto> objetos = new ArrayList<>();
		List<Colecao> colecoes = Colecao.find("visivel = true").fetch();
		
		String busca = params.get("busca");
		String classificacao = params.get("atributoOrdenacao");
		
		System.out.println("ID COLEÇÃO: " + idColecao);
		System.out.println("ID CATEGORIA: " + idCategoria);
		System.out.println("CLASSIFIICAÇÃO: " + classificacao);
		
		//ORDENAMENTO
		String order;
		if (classificacao != null) {
			order = "order by objeto." + classificacao;
		} else  {
			order = "order by objeto.nome";
			classificacao = "nome";
		}
		
		
		//BUSCA
		if (busca == null) {
			busca = "";
		}
		
		
		if (idCategoria != null) {
			objetos= Objeto.find("select objeto from Objeto objeto, Categoria categoria "
					+ " where categoria.id = ?1 "
					+ " and objeto member of categoria.objetos and LOWER(objeto.nome) like ?2 and objeto.visivel = true "
					+ order, idCategoria, "%"+busca.toLowerCase()+"%").fetch();
		} else if (idColecao != null) {
			objetos= Objeto.find("select objeto from Objeto objeto, Colecao colecao "
					+ " where colecao.id = ?1 "
					+ " and objeto member of colecao.objetos and LOWER(objeto.nome) like ?2 and objeto.visivel = true "
					+ order, idColecao, "%"+busca.toLowerCase()+"%").fetch();
		} 
		
		
		
		render(idCategoria, idColecao, objetos, titulo, descricao, busca, classificacao, colecoes);
	}
	
	
}	
