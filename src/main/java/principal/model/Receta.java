package principal.model;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "recetas")
public class Receta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "valoracion")
	private float valoracion;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "duracion")
	private int duracion;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "origen")
	private String origen;

	@Column(name = "instrucciones")
	private String instrucciones;

	@Column(name = "dificultad")
	private String dificultad;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	@JsonIgnore
	private Usuario usuario;
	
	@OneToMany(mappedBy="receta", fetch = FetchType.EAGER )
	private Set<Comentario> comentarios;
	
	@OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RecetasIngredientes> ingredientes;

	public Receta() {
		this.fecha = new Date();
		this.valoracion = 0f;
		this.usuario = new Usuario();
		this.comentarios = new HashSet<Comentario>();
		this.ingredientes = new HashSet<RecetasIngredientes>();
	}

	public Receta(String nombre, float valoracion, int duracion, String tipo, String origen, String instrucciones,
			String dificultad) {
		this.nombre = nombre;
		this.valoracion = valoracion;
		this.duracion = duracion;
		this.tipo = tipo;
		this.origen = origen;
		this.instrucciones = instrucciones;
		this.dificultad = dificultad;
		this.fecha = new Date();
		this.usuario = new Usuario();
		this.comentarios = new HashSet<Comentario>();
		this.ingredientes = new HashSet<RecetasIngredientes>();
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

	public float getValoracion() {
		return valoracion;
	}

	public void setValoracion(float valoracion) {
		this.valoracion = valoracion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Set<RecetasIngredientes> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Set<RecetasIngredientes> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public void addIngrediente(Ingrediente ingre, int cantidad) {
	
		RecetasIngredientes ri = new RecetasIngredientes(this, ingre, cantidad);
		
		if (this.ingredientes.contains(ri)) {
			this.ingredientes.remove(ri);
		}
		
		if (cantidad != 0) {
			this.ingredientes.add(ri);
			ingre.getRecetas().add(ri);
		}
		
	}

	public void removeIngrediente(Ingrediente ingre) {
		
		for (RecetasIngredientes ri : this.ingredientes) {
			if (ri.getIngrediente().equals(ingre)) {
				this.ingredientes.remove(ri);
			}
		}
		
		for (RecetasIngredientes ri : ingre.getRecetas()) {
			if (ri.getReceta().equals(this)) {
				ingre.getRecetas().remove(ri);
			}
		}
		
	}
		
	@Override
	public String toString() {

		String resultado="Receta [id =" + id + ", nombre =" + nombre + ", valoracion =" + valoracion + ", fecha =" + fecha
				+ ", duracion =" + duracion + ", tipo =" + tipo + ", origen =" + origen + ", instrucciones ="
				+ instrucciones + ", dificultad =" + dificultad + ", ingredientes =";
		
		for (RecetasIngredientes ingre : this.ingredientes) {
			resultado = resultado +"\n"+ingre.getIngrediente().toString()+" Cantidad: "+ ingre.getCantidad();
		}
		
		resultado = resultado + "]\n";
		return resultado;
	}
}
