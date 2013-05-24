package br.ufma.lsd.mobileSUS.entidades;

import java.sql.Date;

public class Msg {
	private String msg;
	private Date data;
	private Usuario destino;
	private Usuario remetente;
	
	public Msg(){
		data=new Date(System.currentTimeMillis());
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Usuario getDestino() {
		return destino;
	}
	public void setDestino(Usuario destino) {
		this.destino = destino;
	}
	public Usuario getRemetente() {
		return remetente;
	}
	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}

	
}
