package principal.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.model.Receta;
import principal.persistencia.RecetaRepo;
import principal.servicios.interfaces.RecetaService;

@Service
public class RecetaServiceImpl implements RecetaService {

	@Autowired
	RecetaRepo recetaRepo;

	@Override
	public Receta insertarReceta(Receta rece) {
		return recetaRepo.save(rece);
	}

	@Override
	public List<Receta> listarRecetas() {
		return recetaRepo.findAll();
	}

	@Override
	public Receta obtenerRecetaPorId(Integer id) {
		return recetaRepo.findById(id).get();
	}

	@Override
	public Receta obtenerRecetaPorNombre(String nombreReceta) {
		return recetaRepo.findByNombre(nombreReceta).get();
	}

	@Override
	public void eliminarReceta(Receta rece) {
		recetaRepo.delete(rece);
	}

	@Override
	public void eliminarRecetaPorId(Integer id) {
		recetaRepo.delete(recetaRepo.findById(id).get());
	}

}
