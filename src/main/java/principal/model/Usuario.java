package principal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "admin")
	private boolean admin;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private Set<Receta> recetas;

	public Usuario() {
		this.admin = false;
		this.recetas = new HashSet<Receta>();
	}

	public Usuario(String nombreUsuario, String password, String email) {
		this.nombre = nombreUsuario;
		this.password = password;
		this.email = email;
		this.admin = false;
		this.recetas = new HashSet<Receta>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombreUsuario) {
		this.nombre = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Set<Receta> getRecetas() {
		return recetas;
	}

	public void setRecetas(Set<Receta> recetas) {
		this.recetas = recetas;
	}

	@Override
	public String toString() {
		String resultado = "Usuario [id =" + id + ", nombre =" + nombre + ", password =" + password + ", email =" + email
				+ ", admin =" + admin + ", recetas =";
		
		for (Receta receta : this.recetas) {
			resultado = resultado + "\n" +receta.toString();
		}
		resultado = resultado + "]\n";
		return resultado;
	}

}
