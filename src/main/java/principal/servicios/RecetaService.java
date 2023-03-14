package principal.servicios;

import java.util.List;

import principal.model.Receta;

public interface RecetaService {

	public Receta insertarReceta(Receta rece);
	public List<Receta> listarRecetas();
	public Receta obtenerRecetaPorId(Integer id);
	public Receta obtenerRecetaPorNombre(String nombreReceta);
	public void eliminarReceta(Receta rece);
	public void eliminarRecetaPorId(Integer id);
}
