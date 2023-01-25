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

import principal.model.Receta;
import principal.model.Rol;
import principal.model.Usuario;
import principal.servicios.impl.RecetaServiceImpl;
import principal.servicios.impl.UsuarioServiceImpl;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {

	@Autowired
	private RecetaServiceImpl receServiceImpl;
	
	@Autowired
    private UsuarioServiceImpl userServiceImpl;
	
	@GetMapping(value = { "", "/" })
	String homeUsuario(Model model) {

		// Buscar a la BBDD
		ArrayList<Receta> misRecetas = (ArrayList<Receta>) receServiceImpl.listarRecetas();
		ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) userServiceImpl.listarUsuarios();

		// En el template de alumnos imprimir tabla de alumnos
		model.addAttribute("listaRecetas", misRecetas);
		model.addAttribute("listaUsuarios", misUsuarios);
		
		model.addAttribute("usuarioaAdd", new Usuario());

		return "usuarios";
	}

	@GetMapping(value = { "/{id}" })
	String idUsuario(Model model, @PathVariable(name = "id") Integer id) {
		
		Usuario usuMostrar = userServiceImpl.obtenerUsuarioPorId(id);
		model.addAttribute("usuMostrar", usuMostrar);
		model.addAttribute("usuaEditar", new Usuario());
		return "usuario";
	}

	@PostMapping("/add")
    public String addUsuario( @ModelAttribute("usuarioaAdd") Usuario usuNuevo,   BindingResult bindingResult) {        
       
        for(Receta re: usuNuevo.getRecetas()) {
            re.setUsuario(usuNuevo);
        }
        userServiceImpl.insertarUsuario(usuNuevo);
        return "redirect:/usuarios";   
    }
	
	
	@GetMapping("/delete/{id}")
    String deleteUser(@PathVariable(name = "id") Integer id) {
    	if(isAdmin()) {
    		Usuario usuarioAEliminar = userServiceImpl.obtenerUsuarioPorId(id);
    		userServiceImpl.eliminarUsuario(usuarioAEliminar);
    	}
    	return "redirect:/usuarios";
    }
 
    public boolean isAdmin() {
    	Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserDetails usuDetails = null;
    	if(auth instanceof UserDetails) {
    		usuDetails = (UserDetails) auth;
    		Usuario u = userServiceImpl.obtenerUsuarioPorUsername(usuDetails.getUsername());
    		for(Rol r : u.getRoles()){
    			if(r.getNombre().compareTo("ROLE_ADMIN") == 0){
    				return true;
    			}
    		}
    	}
    	return false;
    }
	
	@PostMapping(value={"/edit/{id}"})
	public String editUsu(@PathVariable(name="id") Integer id, @ModelAttribute("usuaEditar") Usuario usuEditado, BindingResult binding ) {
		
		Usuario usuaEditar = userServiceImpl.obtenerUsuarioPorId(id);
		usuaEditar.setUsername(usuEditado.getUsername());
		usuaEditar.setPassword(usuEditado.getPassword());
		usuaEditar.setEmail(usuEditado.getEmail());
		usuaEditar.setAdmin(usuEditado.isAdmin());
		userServiceImpl.insertarUsuario(usuaEditar);
		
		return "redirect:/usuarios";
	}
}
