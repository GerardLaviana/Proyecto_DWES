package principal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity 
@Table(name = "ingredientes")
public class Ingrediente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "gluten")
	private boolean gluten;
	
	@ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(
			name="recetas_ingredientes",
			joinColumns = {@JoinColumn(name="id_ingrediente")},
			inverseJoinColumns = {@JoinColumn(name="id_receta")}
	)
	private Set<Receta> recetas;

	public Ingrediente() {
		this.recetas = new HashSet<Receta>();
	}

	public Ingrediente(String nombre, String tipo, boolean gluten) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.gluten = gluten;
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

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isGluten() {
		return gluten;
	}

	public void setGluten(boolean gluten) {
		this.gluten = gluten;
	}

	public Set<Receta> getRecetas() {
		return recetas;
	}

	public void setRecetas(Set<Receta> recetas) {
		this.recetas = recetas;
	}

	@Override
	public String toString() {
		String resultado="Ingrediente [id =" + id + ", nombre =" + nombre + ", tipo =" + tipo + ", gluten =" + gluten + "]";
		return resultado;
	}

}
