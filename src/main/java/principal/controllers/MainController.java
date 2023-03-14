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
import principal.model.RecetasIngredientes;
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
		//crearTablas();
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

	@GetMapping(value = { "/recetas/{id}" })
	String idReceta(Model model, @PathVariable(name = "id") Integer id) {
		
		Receta recetaMostrar = receServiceImpl.obtenerRecetaPorId(id);
		ArrayList<Usuario> misUsuarios = (ArrayList<Usuario>) userServiceImpl.listarUsuarios();
		ArrayList<Ingrediente> misIgre = (ArrayList<Ingrediente>) ingreServiceImpl.listarIngredientes();
		boolean editable = userServiceImpl.isEditable(recetaMostrar.getUsuario().getUsername());
		model.addAttribute("recetaMostrar", recetaMostrar);
		model.addAttribute("listaUsuarios", misUsuarios);
		model.addAttribute("listaIngre", misIgre);
		model.addAttribute("listaRecetasIngredientes", recetaMostrar.getIngredientes());
		model.addAttribute("recetasIngredientesNew", new RecetasIngredientes(recetaMostrar, new Ingrediente(), 1));
		model.addAttribute("isEditable",editable);
		return "receta";
	}
	
	@PostMapping("/recetas/add")
    public String addReceta( @ModelAttribute("recetaNueva") Receta recetaNueva,   BindingResult bindingResult) {        
       
       Usuario usuarioReceta = userServiceImpl.obtenerUsuarioPorUsername(userServiceImpl.obtenerNombreUsuarioLoggeado());
       
       usuarioReceta.getRecetas().add(recetaNueva);
       recetaNueva.setUsuario(usuarioReceta);
       
       receServiceImpl.insertarReceta(recetaNueva);
       return "redirect:/";   
    }
	
	@PostMapping(value={"/recetas/edit/{id}"})
	public String editReceta(@PathVariable(name="id") Integer id, @ModelAttribute("recetaMostrar") Receta recetaEditada, BindingResult binding ) {
		
		Receta recetaAEditar = receServiceImpl.obtenerRecetaPorId(id);
		
		recetaAEditar.setNombre(recetaEditada.getNombre());
		recetaAEditar.setValoracion(recetaEditada.getValoracion());
		recetaAEditar.setFecha(recetaEditada.getFecha());
		recetaAEditar.setDuracion(recetaEditada.getDuracion());
		recetaAEditar.setTipo(recetaEditada.getTipo());
		recetaAEditar.setOrigen(recetaEditada.getOrigen());
		recetaAEditar.setInstrucciones(recetaEditada.getInstrucciones());
		recetaAEditar.setDificultad(recetaEditada.getDificultad());
		recetaAEditar.setUsuario(recetaEditada.getUsuario());
		
		receServiceImpl.insertarReceta(recetaAEditar);
		
		return "redirect:/";
	}
	
	@DeleteMapping(value={"/recetas/delete/{id}"})
	String deleteReceta(@PathVariable(name="id") Integer id) {
		receServiceImpl.eliminarRecetaPorId(id);
		return "redirect:/";
	}
	
	@PostMapping({ "/addIngre/{id}" })
	String addIngrediente(Model model, @ModelAttribute("recetasIngredientesNew") RecetasIngredientes recetasIngredientesNew, BindingResult bindingResult, @PathVariable Integer id) {

		Receta recetaActual = receServiceImpl.obtenerRecetaPorId(id);
		Ingrediente ingreAñadir = ingreServiceImpl.obtenerIngredientePorId(recetasIngredientesNew.getIngrediente().getId());

		recetaActual.addIngrediente(ingreAñadir, recetasIngredientesNew.getCantidad());

		receServiceImpl.insertarReceta(recetaActual);

		return "redirect:/recetas/" + id;
	}
	
	public void crearTablas() {
		
		Receta primeraReceta = new Receta("Tortilla Española",5f,15,"Comida","España", "Aqui se explica como facelo","Media");
		Receta segundaReceta = new Receta("Sándwich mixto",4.5f,5,"Cena","Inglaterra", "Aqui se explica como facelo","Fácil");
		Receta terceraReceta = new Receta("Tarta de Santiago",3.5f,60,"Postre","España", "Aqui se explica como facelo","Dificíl");
		
		Ingrediente pan = new Ingrediente("Pan de molde", "Cereal", true);
		Ingrediente jamon = new Ingrediente("Jamon", "Carne", false);
		Ingrediente queso = new Ingrediente("Queso", "Lacteo", false);
		Ingrediente huevo = new Ingrediente("Huevo", "Producto Animal", false);
		Ingrediente patata = new Ingrediente("Patata", "Verdura", false);
		Ingrediente almendra = new Ingrediente("Almendra", "Fruto Seco", false);
		Ingrediente mantequilla = new Ingrediente("Mantequilla", "Lacteo", false);
		Ingrediente azucar = new Ingrediente("Azucar", "Azucar", false);
		Ingrediente limon = new Ingrediente("Limon", "Fruta", false);
		
		Usuario usuario1 = new Usuario("Isabel","1234", "ejemplo@gmail.com");
		Usuario usuario2 = new Usuario("Manolo","1234", "manolo@gmail.com");
		Usuario usuario3 = new Usuario("Juan","1234", "juan@gmail.com");

		usuario1.getRecetas().add(primeraReceta);
		primeraReceta.setUsuario(usuario1);

		usuario2.getRecetas().add(terceraReceta);
		terceraReceta.setUsuario(usuario2);

		usuario2.getRecetas().add(segundaReceta);
		segundaReceta.setUsuario(usuario2);

		userServiceImpl.insertarUsuario(usuario1);
		userServiceImpl.insertarUsuario(usuario2);
		
		
		ingreServiceImpl.insertarIngrediente(pan);
		ingreServiceImpl.insertarIngrediente(jamon);
		ingreServiceImpl.insertarIngrediente(queso);
		ingreServiceImpl.insertarIngrediente(huevo);
		ingreServiceImpl.insertarIngrediente(patata);
		ingreServiceImpl.insertarIngrediente(almendra);
		ingreServiceImpl.insertarIngrediente(mantequilla);
		ingreServiceImpl.insertarIngrediente(azucar);
		ingreServiceImpl.insertarIngrediente(limon);
		
		receServiceImpl.insertarReceta(primeraReceta);
		receServiceImpl.insertarReceta(segundaReceta);
		receServiceImpl.insertarReceta(terceraReceta);
		
		primeraReceta.addIngrediente(huevo, 1);
		primeraReceta.addIngrediente(patata, 3);

		segundaReceta.addIngrediente(pan, 5);
		segundaReceta.addIngrediente(jamon, 3);
		segundaReceta.addIngrediente(queso, 3);

		terceraReceta.addIngrediente(huevo, 2);
		terceraReceta.addIngrediente(almendra, 14);
		terceraReceta.addIngrediente(mantequilla, 50);
		terceraReceta.addIngrediente(azucar, 200);
		terceraReceta.addIngrediente(limon, 2);
		userServiceImpl.insertarUsuario(usuario3);
		
		/*
		userServiceImpl.insertarUsuario(usuario1);
		userServiceImpl.insertarUsuario(usuario2);
		userServiceImpl.insertarUsuario(usuario3);

		receServiceImpl.insertarReceta(primeraReceta);
		receServiceImpl.insertarReceta(segundaReceta);
		receServiceImpl.insertarReceta(terceraReceta);

		ingreServiceImpl.insertarIngrediente(pan);
		ingreServiceImpl.insertarIngrediente(jamon);
		ingreServiceImpl.insertarIngrediente(queso);
		ingreServiceImpl.insertarIngrediente(huevo);
		ingreServiceImpl.insertarIngrediente(patata);
		ingreServiceImpl.insertarIngrediente(almendra);
		ingreServiceImpl.insertarIngrediente(mantequilla);
		ingreServiceImpl.insertarIngrediente(azucar);
		ingreServiceImpl.insertarIngrediente(limon);
		 */
	}
	
	
}
