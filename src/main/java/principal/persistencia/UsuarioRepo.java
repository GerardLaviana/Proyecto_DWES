package principal.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import principal.model.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer>{

	public Optional<Usuario> findByUsername(String name);
}
