package principal.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import principal.servicios.impl.IngredienteServiceImpl;
import principal.servicios.impl.RecetaServiceImpl;
import principal.servicios.impl.UsuarioServiceImpl;


@RequestMapping("/")
@Controller
public class MainController {

	@Autowired
	private RecetaServiceImpl receServiceImpl;
	
	@Autowired
	private UsuarioServiceImpl userServiceImpl;
	
	@Autowired
	private IngredienteServiceImpl ingreServiceImpl;
	
	@GetMapping(value = { "", "/" })
	String home(Model model) {

		// Buscar a la BBDD
		ArrayList<Receta> misRecetas = (ArrayList<Receta>) receServiceImpl.listarRecetas();
		ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) userServiceImpl.listarUsuarios();
		ArrayList<Ingrediente> misIgre = (ArrayList<Ingrediente>) ingreServiceImpl.listarIngredientes();

		// En el template de alumnos imprimir tabla de alumnos
		model.addAttribute("listaRecetas", misRecetas);
		model.addAttribute("listaUsuarios", misUsuarios);
		model.addAttribute("listaIngre", misIgre);
		
		model.addAttribute("recetaNueva", new Receta());

		return "index";
	}

	@GetMapping(value = { "/{id}" })
	String idReceta(Model model, @PathVariable(name = "id") Integer id) {
		
		Receta recetaMostrar = receServiceImpl.obtenerRecetaPorId(id);
		ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) userServiceImpl.listarUsuarios();
		ArrayList<Ingrediente> misIgre = (ArrayList<Ingrediente>) ingreServiceImpl.listarIngredientes();
		//boolean editable = isEditable(recetaMostrar.getUsuario().getUsername());
		model.addAttribute("recetaMostrar", recetaMostrar);
		model.addAttribute("listaUsuarios", misUsuarios);
		model.addAttribute("listaIngre", misIgre);
		//model.addAttribute("isEditable",editable);
		return "receta";
	}
	
	
	/**
	public boolean isEditable(String nombreUsu) {
    	Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserDetails usuDetails = null;
    	if(auth instanceof UserDetails) {
    		usuDetails = (UserDetails) auth;
    		if(usuDetails.getUsername()==nombreUsu){
    			return true;
    		}
    	}
    	return false;
    }*/

	@PostMapping("/add")
    public String addReceta( @ModelAttribute("recetaNueva") Receta recetaNueva,   BindingResult bindingResult) {        
       
       //Alumno alumnoNuevo = alumDAO.buscarAlumnoHibernate(pedidoNuevo.getAlumno().getId());
       Usuario usuarioNuevo = userServiceImpl.obtenerUsuarioPorId(recetaNueva.getUsuario().getId());
       
       usuarioNuevo.getRecetas().add(recetaNueva);
       recetaNueva.setUsuario(usuarioNuevo);
        
       //miDAO.insertarPedidoHibernate(pedidoNuevo);
       
       
        for(Ingrediente i: recetaNueva.getIngredientes()) {
            i.getRecetas().add(recetaNueva);
            //bocaDAO.modificarBocadilloHibernate(b);
        }
        receServiceImpl.insertarReceta(recetaNueva);
        return "redirect:/";   
    }
	
	@PostMapping(value={"/edit/{id}"})
	public String editReceta(@PathVariable(name="id") Integer id, @ModelAttribute("recetaMostrar") Receta recetaEditada, BindingResult binding ) {
		
		Receta recetaAEditar = receServiceImpl.obtenerRecetaPorId(id);
		
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
		
		receServiceImpl.insertarReceta(recetaEditada);
		
		return "redirect:/";
	}
	
	@GetMapping(value={"/delete/{id}"})
	String deleteReceta(@PathVariable(name="id") Integer id) {
		receServiceImpl.eliminarRecetaPorId(id);
		return "redirect:/";
	}
	
}
