package controllers;

import java.io.File;
import java.util.List;

import models.Colecao;
import models.Foto;
import models.Objeto;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Colecoes extends Controller{
	
	public static void form() {
		System.out.println("---Formulário---");
		Colecao colecao = (Colecao) Cache.get("colecao");
		Cache.clear();
		render(colecao);
		
	}
	
	public static void listar() {
		System.out.println("---Listagem---");
		String busca = params.get("busca");

		List<Colecao> lista;
		if (busca == null) {
			lista = Colecao.findAll();
		} else {
		   lista = Colecao.find("select c from Colecao c "
				   + " where c.nome like ? " ,
				   "%"+busca+"%").fetch();
		}
		
		ValuePaginator listaPaginada = new ValuePaginator(lista);
		listaPaginada.setPageSize(5);
		
		render(listaPaginada, busca);

	}
	
	public static void salvar(@Valid Colecao colecao, Long idObjeto, File foto) {
		
		System.out.println("---Salvar---");
		
		
		
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
		
		if (foto != null) {
			System.out.println("Tem foto");
			Foto f = new Foto(foto.getName());
			f.save();
			
			colecao.fotoCapa = f;
			
			File dest = new File("./uploads/" + foto.getName());
			
			
//			if (dest.exists()) {
//				dest.delete();
//			}
	
			foto.renameTo(dest);
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
	
	public static void exibirObjetos (Long idColecao) {
		
		Colecao colecao = Colecao.findById(idColecao);
		render(colecao);
	}
	
	public void visualizarObjeto (Long idObjeto) {
		Objeto objeto = Objeto.findById(idObjeto);
		render(objeto);
	}

}
