package principal.servicios.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import principal.model.Rol;
import principal.model.Usuario;
import principal.model.dto.UsuarioDTO;
import principal.persistencia.UsuarioRepo;
import principal.servicios.interfaces.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepo usuarioRepo;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepo.findByUsername(username);
		
		if(usuario.isPresent()){
			Usuario usermio= usuario.get();
			return usermio;
		}else{ 
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
	}

	@Override
	public Usuario insertarUsuario(Usuario usu) {
		return usuarioRepo.save(usu);
	}

	@Override
	public Usuario insertarUsuarioDTO(UsuarioDTO usuDTO) {
		Usuario usuarioNuevo = new Usuario(usuDTO.getUsername(), passwordEncoder.encode(usuDTO.getPassword()), usuDTO.getEmail());
		usuarioNuevo.getRoles().add(new Rol("ROLE_USER"));
		return usuarioRepo.save(usuarioNuevo);
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepo.findAll();
	}

	@Override
	public Usuario obtenerUsuarioPorId(Integer id) {
		return usuarioRepo.findById(id).get();
	}

	@Override
	public Usuario obtenerUsuarioPorUsername(String nombreUsuario) {
		return usuarioRepo.findByUsername(nombreUsuario).get();
	}

	@Override
	public void eliminarUsuario(Usuario user) {
		usuarioRepo.delete(user);
	}

	@Override
	public void eliminarUsuarioPorId(Integer id) {
		usuarioRepo.delete(usuarioRepo.findById(id).get());
	}

}
