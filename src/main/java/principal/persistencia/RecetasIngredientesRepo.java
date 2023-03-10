package principal.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import principal.model.RecetasIngredientes;

public interface RecetasIngredientesRepo extends JpaRepository<RecetasIngredientes, Integer> {
	
	//public Optional<RecetasIngredientes> findByNombre(String name);

}
