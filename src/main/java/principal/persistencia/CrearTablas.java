package principal.persistencia;

import principal.model.Ingrediente;
import principal.model.Receta;
import principal.model.Usuario;

public class CrearTablas {

	public void creacion() {
		
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
		
		RecetaDAO recetaDAO = new RecetaDAO();
		IngredienteDAO ingreDAO = new IngredienteDAO();
		UsuarioDAO usuDAO = new UsuarioDAO();
		
		usuario1.getRecetas().add(primeraReceta);
		primeraReceta.setUsuario(usuario1);
		
		usuario2.getRecetas().add(terceraReceta);
		terceraReceta.setUsuario(usuario2);
		
		usuario3.getRecetas().add(segundaReceta);
		segundaReceta.setUsuario(usuario3);
		
		primeraReceta.getIngredientes().add(huevo);
		primeraReceta.getIngredientes().add(patata);
		huevo.getRecetas().add(primeraReceta);
		patata.getRecetas().add(primeraReceta);
		
		segundaReceta.getIngredientes().add(pan);
		segundaReceta.getIngredientes().add(jamon);
		segundaReceta.getIngredientes().add(queso);
		pan.getRecetas().add(segundaReceta);
		jamon.getRecetas().add(segundaReceta);
		queso.getRecetas().add(segundaReceta);
		
		terceraReceta.getIngredientes().add(huevo);
		terceraReceta.getIngredientes().add(almendra);
		terceraReceta.getIngredientes().add(mantequilla);
		terceraReceta.getIngredientes().add(azucar);
		terceraReceta.getIngredientes().add(limon);
		huevo.getRecetas().add(terceraReceta);
		almendra.getRecetas().add(terceraReceta);
		mantequilla.getRecetas().add(terceraReceta);
		azucar.getRecetas().add(terceraReceta);
		limon.getRecetas().add(terceraReceta);
		
		usuDAO.insertarUsuario(usuario1);
		usuDAO.insertarUsuario(usuario2);
		usuDAO.insertarUsuario(usuario3);
		
		recetaDAO.insertarReceta(primeraReceta);
		recetaDAO.insertarReceta(segundaReceta);
		recetaDAO.insertarReceta(terceraReceta);
		
		ingreDAO.insertarIngrediente(pan);
		ingreDAO.insertarIngrediente(jamon);
		ingreDAO.insertarIngrediente(queso);
		ingreDAO.insertarIngrediente(huevo);
		ingreDAO.insertarIngrediente(patata);
		ingreDAO.insertarIngrediente(almendra);
		ingreDAO.insertarIngrediente(mantequilla);
		ingreDAO.insertarIngrediente(azucar);
		ingreDAO.insertarIngrediente(limon);
	}
}
