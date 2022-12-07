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

@RequestMapping("/ingredientes")
@Controller
public class IngredienteController {

	@Autowired
	RecetaRepo recetaRepo;
	
	@Autowired
	UsuarioRepo usuRepo;
	
	@Autowired
	IngredienteRepo ingreRepo;
	
	@GetMapping(value = { "", "/" })
	String homeIngrediente(Model model) {

		// Buscar a la BBDD
		ArrayList<Receta> misRecetas = (ArrayList<Receta>) recetaRepo.findAll();
		ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) usuRepo.findAll();
		ArrayList<Ingrediente> misIgre = (ArrayList<Ingrediente>) ingreRepo.findAll();

		// En el template de alumnos imprimir tabla de alumnos
		model.addAttribute("listaRecetas", misRecetas);
		model.addAttribute("listaUsuarios", misUsuarios);
		model.addAttribute("listaIngre", misIgre);
		
		model.addAttribute("ingredienteaAdd", new Ingrediente());

		return "ingredientes";
	}

	@GetMapping(value = { "/{id}" })
	String idIngre(Model model, @PathVariable(name = "id") Integer id) {
		
		Ingrediente ingreMostrar = ingreRepo.findById(id).get();
		model.addAttribute("ingreMostrar", ingreMostrar);
		model.addAttribute("ingreaEditar", new Ingrediente());
		
		return "ingrediente";
	}

	@PostMapping("/add")
    public String addIngre( @ModelAttribute("ingredienteaAdd") Ingrediente ingredienteNuevo,   BindingResult bindingResult) {        
       
        for(Receta re: ingredienteNuevo.getRecetas()) {
            re.getIngredientes().add(ingredienteNuevo);
        }
        ingreRepo.save(ingredienteNuevo);
        return "redirect:/ingredientes";   
    }
	
	@GetMapping(value={"/delete/{id}"})
	String deleteIngre(@PathVariable(name="id") Integer id) {
		Ingrediente ingreABorrar = ingreRepo.findById(id).get();
		ingreRepo.delete(ingreABorrar);
		
		return "redirect:/ingredientes";
	}
	
	@PostMapping(value={"/edit/{id}"})
	public String editIngre(@PathVariable(name="id") Integer id, @ModelAttribute("ingreaEditar") Ingrediente ingreEditado, BindingResult binding ) {
		
		Ingrediente ingreaEditar = ingreRepo.findById(id).get();
		ingreaEditar.setNombre(ingreEditado.getNombre());
		ingreaEditar.setTipo(ingreEditado.getTipo());
		ingreaEditar.setGluten(ingreEditado.isGluten());
		ingreRepo.save(ingreaEditar);
		
		return "redirect:/ingredientes";
	}
}
