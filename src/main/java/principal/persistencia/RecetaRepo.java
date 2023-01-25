package principal.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import principal.model.Receta;

@Repository
public interface RecetaRepo extends JpaRepository<Receta, Integer>{

	public Optional<Receta> findByNombre(String name);
}
