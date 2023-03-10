package principal.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "recetas_ingredientes")
public class RecetasIngredientes {

	@EmbeddedId
	private RecetasIngredientesId id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("receta_id")
	@JsonIgnore
	private Receta receta;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("ingrediente_id")
	private Ingrediente ingrediente;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	private String nombre;

	public RecetasIngredientes(){}
	
	public RecetasIngredientes(Receta r, Ingrediente i, Integer c) {
		this.receta = r;
		this.ingrediente = i;
		this.cantidad = c;
		this.nombre = i.getNombre();
		this.id = new RecetasIngredientesId(r.getId(), i.getId());
	}
	
	@Override
	public boolean equals(Object o){
		if(o==null || getClass() != o.getClass() ) {
			return false;
		}
		
		RecetasIngredientes ri = (RecetasIngredientes) o;
		return Objects.equals(this.ingrediente, ri.ingrediente)&&Objects.equals(this.receta, ri.receta);
	}

	@Override
	public int hashCode() {
		return Objects.hash(receta, ingrediente);
	}

	public RecetasIngredientesId getId() {
		return id;
	}

	public void setId(RecetasIngredientesId id) {
		this.id = id;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
