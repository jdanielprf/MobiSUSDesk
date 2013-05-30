package br.ufma.lsd.mobileSUS.entidades;

public class Usuario {
	private String nome;
	private String id;
	private String latitude="0";
	private String longitude="0";
	private Chamados chamado;
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

	public Chamados getChamado() {
		return chamado;
	}

	public void setChamado(Chamados chamado) {
		this.chamado = chamado;
	}
}
