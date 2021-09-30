package controllers;

import java.util.List;

import models.Categoria;
import models.Objeto;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Categorias extends Controller {
	
	public static void form() {
		
		Categoria categoria = (Categoria) Cache.get("categoria");
		Cache.clear();
		render(categoria);
	}
	
	
	public static void listar() {
		String busca = params.get("busca");

		List<Categoria> lista;
		if (busca == null) {
			lista = Categoria.findAll();
		} else {
		   lista = Categoria.find("select c from Categoria c "
				   + " where lower(c.nome) like ? " ,
				   "%"+busca.toLowerCase()+"%").fetch();
		}
		
		ValuePaginator listaPaginada = new ValuePaginator(lista);
		listaPaginada.setPageSize(3);
		
		render(listaPaginada, busca);

	}
	
	public static void salvar(@Valid Categoria categoria) {
		
		if(validation.hasErrors()) {
			Cache.add("categoria", categoria);
			validation.keep();
			form();
		}
		
		categoria.save();
		listar();
	}
	
	public static void deletar(Long id) {
	    Categoria categoria = Categoria.findById(id);
		categoria.delete();
		listar();
	}
	
	public static void editar(Long id) {
		Categoria categoria = Categoria.findById(id);
		renderTemplate("Categorias/form.html", categoria);
	}
	
}