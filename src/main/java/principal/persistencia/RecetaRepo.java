package principal.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import principal.model.Receta;

public interface RecetaRepo extends JpaRepository<Receta, Integer>{

	public Optional<Receta> findByNombre(String name);
}
