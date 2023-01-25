package principal.servicios.interfaces;

import java.util.List;

import principal.model.Ingrediente;

public interface IngredienteService {

	public Ingrediente insertarIngrediente(Ingrediente ingre);
	public List<Ingrediente> listarIngredientes();
	public Ingrediente obtenerIngredientePorId(Integer id);
	public Ingrediente obtenerIngredientePorNombre(String nombreIngre);
	public void eliminarIngrediente(Ingrediente ingre);
	public void eliminarIngredientePorId(Integer id);
}
