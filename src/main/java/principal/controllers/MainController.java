package principal.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.model.Ingrediente;
import principal.model.Receta;
import principal.model.Usuario;
import principal.persistencia.IngredienteRepo;
import principal.persistencia.RecetaRepo;
import principal.persistencia.UsuarioRepo;

@RequestMapping("/")
@Controller
public class MainController {

	@Autowired
	RecetaRepo recetaRepo;
	
	@Autowired
	UsuarioRepo usuRepo;
	
	@Autowired
	IngredienteRepo ingreRepo;
	
	@GetMapping(value = { "", "/" })
	String home(Model model) {

		// Buscar a la BBDD
		ArrayList<Receta> misRecetas = (ArrayList<Receta>) recetaRepo.findAll();
		ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) usuRepo.findAll();
		ArrayList<Ingrediente> misIgre = (ArrayList<Ingrediente>) ingreRepo.findAll();

		// En el template de alumnos imprimir tabla de alumnos
		model.addAttribute("listaRecetas", misRecetas);
		model.addAttribute("listaUsuarios", misUsuarios);
		model.addAttribute("listaIngre", misIgre);
		
		model.addAttribute("recetaNueva", new Receta());

		return "index";
	}

	@GetMapping(value = { "/{id}" })
	String idReceta(Model model, @PathVariable(name = "id") Integer id) {
		
		Receta recetaMostrar = recetaRepo.findById(id).get();
		ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) usuRepo.findAll();
		ArrayList<Ingrediente> misIgre = (ArrayList<Ingrediente>) ingreRepo.findAll();
		model.addAttribute("recetaMostrar", recetaMostrar);
		model.addAttribute("listaUsuarios", misUsuarios);
		model.addAttribute("listaIngre", misIgre);
		return "receta";
	}

	@PostMapping("/add")
    public String addReceta( @ModelAttribute("recetaNueva") Receta recetaNueva,   BindingResult bindingResult) {        
       
       //Alumno alumnoNuevo = alumDAO.buscarAlumnoHibernate(pedidoNuevo.getAlumno().getId());
       Usuario usuarioNuevo = usuRepo.findById(recetaNueva.getUsuario().getId()).get();
       
       usuarioNuevo.getRecetas().add(recetaNueva);
       recetaNueva.setUsuario(usuarioNuevo);
        
       //miDAO.insertarPedidoHibernate(pedidoNuevo);
       
       
        for(Ingrediente i: recetaNueva.getIngredientes()) {
            i.getRecetas().add(recetaNueva);
            //bocaDAO.modificarBocadilloHibernate(b);
        }
        recetaRepo.save(recetaNueva);
        return "redirect:/";   
    }
	
	@PostMapping(value={"/edit/{id}"})
	public String editReceta(@PathVariable(name="id") Integer id, @ModelAttribute("recetaMostrar") Receta recetaEditada, BindingResult binding ) {
		
		Receta recetaAEditar = recetaRepo.findById(id).get();
		
		for (Ingrediente ingre : recetaAEditar.getIngredientes()) {
			if(!recetaEditada.getIngredientes().contains(ingre)) {
				ingre.getRecetas().remove(recetaAEditar);
			}
		}
		
		for (Ingrediente ingreNuevo : recetaEditada.getIngredientes()) {
			if(!recetaAEditar.getIngredientes().contains(ingreNuevo)) {
				ingreNuevo.getRecetas().add(recetaEditada);
			}
		}
		
		recetaRepo.save(recetaEditada);
		
		return "redirect:/";
	}
	
	@GetMapping(value={"/delete/{id}"})
	String deleteReceta(@PathVariable(name="id") Integer id) {
		Receta recetaABorrar = recetaRepo.findById(id).get();
		recetaRepo.delete(recetaABorrar);
		
		return "redirect:/";
	}
	
}
