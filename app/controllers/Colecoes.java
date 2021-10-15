package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
				   + " where lower(c.nome) like ? " ,
				   "%"+busca.toLowerCase()+"%").fetch();
		}
		
		ValuePaginator listaPaginada = new ValuePaginator(lista);
		listaPaginada.setPageSize(3);
		
		render(listaPaginada, busca);

	}
	
	public static void salvar(@Valid Colecao colecao, Long idObjeto, File foto, String visivel, String tipoExposicao) {
		
		System.out.println("---Salvar---");
		
		
		
		
		if (tipoExposicao.equals("permanente")) {
			colecao.exposicaoPermanente = true;
		} else  {
			colecao.exposicaoPermanente = false;
		}
		
		if (visivel == null) {
			colecao.visivel = false;
		} else  {
			colecao.visivel = true;
		}
		
		
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
			Foto f = new Foto(foto.getName());
			f.save();
			colecao.fotoCapa = f;
			File dest = new File("./uploads/" + foto.getName());
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
		int listSizeObj = colecao.objetos.size();
		System.out.println(listSizeObj);
		renderTemplate("Colecoes/form.html", colecao, objetos, listSizeObj);
	}
	
	public static void removerObjeto (Long idColecao, Long idObjeto) {
		Objeto obj = Objeto.findById(idObjeto);
		Colecao colec = Colecao.findById(idColecao);
		
		colec.objetos.remove(obj);
		
		colec.save();
		
		editar(colec.id);
	}
	
	public static void exibirObjetos (Long idColecao) {
		String busca = params.get("busca");
		
		Colecao colecao = Colecao.findById(idColecao);
		List<Objeto> objetos = new ArrayList<>();
	
		if (busca == null) {
			for (Objeto obj: colecao.objetos) {
				if (obj.visivel  == true) {
					objetos.add(obj);
				}
			}
		} else {
			
			
			List<Objeto> objets = Objeto.find("LOWER(nome) like ?1 ","%"+busca.toLowerCase()+"%").fetch(); 
			List<Objeto> ob = new ArrayList<>();
		
			for(Objeto obj: objets) {
				for(Colecao col: obj.colecoes) {
					if(col.getId() == colecao.getId() && obj.visivel == true) {
						System.out.println("Obj visivel: " + obj.visivel);
						ob.add(obj);
					}
				}   
			}
			objetos = ob;
		}
		
		render(colecao, objetos, idColecao, busca);
	}
	
	public void visualizarObjeto (Long idObjeto) {
		Objeto objeto = Objeto.findById(idObjeto);
		render(objeto);
	}

}
