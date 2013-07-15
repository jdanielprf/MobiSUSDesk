package br.ufma.lsd.mobileSUS.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
@Entity()
public class Usuario {
	private String nome;
	@Id
	
	private String id;
	@Transient
	private String latitude=null;
	@Transient
	private String longitude=null;
	
	@OneToOne()
	private Chamado chamado;
	//@Transient
	private String status;
	
	public Usuario(){}
	
	public Usuario(String nome){
		this.nome=nome;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return nome;
	}
	@Transient
	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
