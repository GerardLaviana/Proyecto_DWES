package principal.servicios;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import principal.model.Usuario;
import principal.model.dto.UsuarioDTO;

public interface UsuarioService extends UserDetailsService{

	public Usuario insertarUsuario(Usuario usu);
	public Usuario insertarUsuarioDTO(UsuarioDTO usuDTO);
	public List<Usuario> listarUsuarios();
	public Usuario obtenerUsuarioPorId(Integer id);
	public Usuario obtenerUsuarioPorUsername(String nombreUsuario);
	public void eliminarUsuario(Usuario user);
	public void eliminarUsuarioPorId(Integer id);
	public String obtenerNombreUsuarioLoggeado();
}
