package principal.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RecetasIngredientesId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="receta_id")
	private Integer recetaId;
	
	@Column(name="ingrediente_id")
	private Integer ingredienteId;
	
	public RecetasIngredientesId() {}

	public RecetasIngredientesId(Integer idR, Integer idI) {
		this.recetaId = idR;
		this.ingredienteId = idI;
	}

	@Override
	public boolean equals(Object o){
		if(o==null || getClass() != o.getClass() ) {
			return false;
		}
		RecetasIngredientesId ri = (RecetasIngredientesId) o;
		return Objects.equals(this.ingredienteId, ri.ingredienteId)&&Objects.equals(this.recetaId, ri.recetaId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(recetaId, ingredienteId);
	}

	public Integer getRecetaId() {
		return recetaId;
	}

	public void setRecetaId(Integer recetaId) {
		this.recetaId = recetaId;
	}

	public Integer getIngredienteId() {
		return ingredienteId;
	}

	public void setIngredienteId(Integer ingredienteId) {
		this.ingredienteId = ingredienteId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
