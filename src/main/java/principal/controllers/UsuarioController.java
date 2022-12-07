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

import principal.model.Receta;
import principal.model.Usuario;
import principal.persistencia.RecetaRepo;
import principal.persistencia.UsuarioRepo;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

	@Autowired
	RecetaRepo recetaRepo;
	
	@Autowired
	UsuarioRepo usuRepo;
	
	@GetMapping(value = { "", "/" })
	String homeUsuario(Model model) {

		// Buscar a la BBDD
		ArrayList<Receta> misRecetas = (ArrayList<Receta>) recetaRepo.findAll();
		ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) usuRepo.findAll();

		// En el template de alumnos imprimir tabla de alumnos
		model.addAttribute("listaRecetas", misRecetas);
		model.addAttribute("listaUsuarios", misUsuarios);
		
		model.addAttribute("usuarioaAdd", new Usuario());

		return "usuarios";
	}

	@GetMapping(value = { "/{id}" })
	String idUsuario(Model model, @PathVariable(name = "id") Integer id) {
		
		Usuario usuMostrar = usuRepo.findById(id).get();
		model.addAttribute("usuMostrar", usuMostrar);
		/*
		ArrayList<Pedido> misPedidos = (ArrayList<Pedido>) pedidoRepo.findAll();
		ArrayList<Alumno> alumnos = (ArrayList<Alumno>) alumnoRepo.findAll();
		ArrayList<Bocadillo> bocas = (ArrayList<Bocadillo>) bocaRepo.findAll();
		

		// En el template de alumnos imprimir tabla de alumnos
		model.addAttribute("listaPedidos", misPedidos);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("ListaBoca", bocas);
		*/
		return "usuario";
	}

	@PostMapping("/add")
    public String addUsuario( @ModelAttribute("usuarioaAdd") Usuario usuNuevo,   BindingResult bindingResult) {        
       
        for(Receta re: usuNuevo.getRecetas()) {
            re.setUsuario(usuNuevo);
        }
        usuRepo.save(usuNuevo);
        return "redirect:/usuarios";   
    }
	
	@GetMapping(value={"/delete/{id}"})
	String deleteUsuario(@PathVariable(name="id") Integer id) {
		Usuario usuABorrar = usuRepo.findById(id).get();
		usuRepo.delete(usuABorrar);
		
		return "redirect:/usuarios";
	}
}
