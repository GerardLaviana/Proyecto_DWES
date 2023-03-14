 package principal.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.model.Ingrediente;
import principal.persistencia.IngredienteRepo;
import principal.servicios.IngredienteService;

@Service
public class IngredienteServiceImpl implements IngredienteService {

	@Autowired
	IngredienteRepo ingredienteRepo;

	@Override
	public Ingrediente insertarIngrediente(Ingrediente ingre) {
		return ingredienteRepo.save(ingre);
	}

	@Override
	public List<Ingrediente> listarIngredientes() {
		return ingredienteRepo.findAll();
	}

	@Override
	public Ingrediente obtenerIngredientePorId(Integer id) {
		return ingredienteRepo.findById(id).get();
	}

	@Override
	public Ingrediente obtenerIngredientePorNombre(String nombreIngre) {
		return ingredienteRepo.findByNombre(nombreIngre).get();
	}

	@Override
	public void eliminarIngrediente(Ingrediente ingre) {
		ingredienteRepo.delete(ingre);
	}

	@Override
	public void eliminarIngredientePorId(Integer id) {
		ingredienteRepo.delete(ingredienteRepo.findById(id).get());
	}
	
}
