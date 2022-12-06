package principal.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import principal.model.Ingrediente;

public interface IngredienteRepo extends JpaRepository<Ingrediente, Integer> {
	
	public Optional<Ingrediente> findByNombre(String name);

}
