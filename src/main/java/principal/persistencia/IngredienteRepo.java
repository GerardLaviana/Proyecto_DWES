package principal.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import principal.model.Ingrediente;

@Repository
public interface IngredienteRepo extends JpaRepository<Ingrediente, Integer> {
	
	public Optional<Ingrediente> findByNombre(String name);

}
