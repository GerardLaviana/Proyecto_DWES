package principal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="comentarios")
public class Comentario {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "mensaje")
	private String mensaje;
	
	@ManyToOne
	@JoinColumn(name="id_receta", nullable = false)
	@JsonIgnore
	private Receta receta;
	
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable = false)
	@JsonIgnore
	private Usuario usuario;
	
	public Comentario() {}
	
	public Comentario(String mensaje, Receta receta, Usuario usuario) {
		this.mensaje = mensaje;
		this.receta = receta;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
