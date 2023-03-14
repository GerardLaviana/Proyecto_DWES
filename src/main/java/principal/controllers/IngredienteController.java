package principal.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import principal.model.Ingrediente;
import principal.model.Receta;
import principal.servicios.impl.RecetaServiceImpl;
import principal.servicios.impl.IngredienteServiceImpl;

@RequestMapping("/ingredientes")
@Controller
public class IngredienteController {

	@Autowired
	RecetaServiceImpl receServiceImpl;
	
	@Autowired
	IngredienteServiceImpl ingreServiceImpl; 
	
	@GetMapping(value = { "", "/" })
	String homeIngrediente(Model model) {

		// Buscar a la BBDD
		ArrayList<Receta> misRecetas = (ArrayList<Receta>) receServiceImpl.listarRecetas();
		ArrayList<Ingrediente> misIgre = (ArrayList<Ingrediente>) ingreServiceImpl.listarIngredientes();

		// En el template de alumnos imprimir tabla de alumnos
		model.addAttribute("listaRecetas", misRecetas);
		model.addAttribute("listaIngre", misIgre);
		
		model.addAttribute("ingredienteaAdd", new Ingrediente());

		return "ingredientes";
	}

	@GetMapping(value = { "/{id}" })
	String idIngre(Model model, @PathVariable(name = "id") Integer id) {
		
		Ingrediente ingreMostrar = ingreServiceImpl.obtenerIngredientePorId(id);
		model.addAttribute("ingreMostrar", ingreMostrar);
		model.addAttribute("ingreaEditar", new Ingrediente());
		
		return "ingrediente";
	}

	@PostMapping("/add")
    public String addIngre( @ModelAttribute("ingredienteaAdd") Ingrediente ingredienteNuevo,   BindingResult bindingResult) {        
       
        ingreServiceImpl.insertarIngrediente(ingredienteNuevo);
        return "redirect:/ingredientes";   
    }
	
	@DeleteMapping(value={"/delete/{id}"})
	String deleteIngre(@PathVariable(name="id") Integer id) {
		ingreServiceImpl.eliminarIngredientePorId(id);
		return "redirect:/ingredientes";
	}
	
	@PostMapping(value={"/edit/{id}"})
	public String editIngre(@PathVariable(name="id") Integer id, @ModelAttribute("ingreaEditar") Ingrediente ingreEditado, BindingResult binding ) {
		
		Ingrediente ingreaEditar = ingreServiceImpl.obtenerIngredientePorId(id);
		ingreaEditar.setNombre(ingreEditado.getNombre());
		ingreaEditar.setTipo(ingreEditado.getTipo());
		ingreaEditar.setGluten(ingreEditado.isGluten());
		ingreServiceImpl.insertarIngrediente(ingreaEditar);
		
		return "redirect:/ingredientes";
	}
}
