package principal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import principal.model.dto.UsuarioDTO;
import principal.servicios.impl.UsuarioServiceImpl;

@RequestMapping("/login")
@Controller
public class LoginController {

	@Autowired
    private UsuarioServiceImpl userServiceImpl;
	
	@GetMapping(value={"","/"})
	String homeLogin(Model model) {
		return "login";
	}
	
	@GetMapping(value= {"/logout"})
	String logOut(Model model) {	
		return "redirect:/login";
	}
	
	@GetMapping("/registro")
    String mostrarRegistro(Model model, @ModelAttribute("usuarioNew") UsuarioDTO usuarioAdd, BindingResult binding) {

        model.addAttribute("newUserDTO", new UsuarioDTO());

        return "registro";
    }
	
	@PostMapping(value={"/addRegistro"})
    public String addRegistro(@ModelAttribute("newUserDTO") UsuarioDTO usuarioNew, BindingResult binding) {

        userServiceImpl.insertarUsuarioDTO(usuarioNew);

        return "login";
    }
}
