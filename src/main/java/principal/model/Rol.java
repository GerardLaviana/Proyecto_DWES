package principal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Usuario> usuarios;
	
	public Rol() {
		this.usuarios = new HashSet<Usuario>();
	}
	
	public Rol(String nombre) {
		this.nombre = nombre;
		this.usuarios = new HashSet<Usuario>();
	}

	public Rol(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.usuarios = new HashSet<Usuario>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}
