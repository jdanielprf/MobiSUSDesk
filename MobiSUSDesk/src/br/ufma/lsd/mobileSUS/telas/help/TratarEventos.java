package br.ufma.lsd.mobileSUS.telas.help;



import java.util.ArrayList;
import java.util.Iterator;

import br.ufma.lsd.mobileSUS.entidades.Chamados;
import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;

public class TratarEventos {
	public static Sessao sessao=new Sessao(); 
	public static void addChamado(Chamados c){
		sessao.addChamado(c);
	}
	public static void addUsuario(Usuario u){
		sessao.addUsuario(u);
	}
	
	public static void atualizarPosicao(Usuario u,String lat,String log){
		u.setLatitude(lat);
		u.setLongitude(log);
	}
	
	public static void atualizarPosicao(String u,String lat,String log){
		Usuario usuario=buscarUsuario(u);
		if(usuario!=null){
			atualizarPosicao(usuario, lat, log);
		}
	}
	
	public static void atualizarPosicao(String u,String msg){
		Usuario usuario=buscarUsuario(u);
		sessao.addMsg(usuario, msg);
	}
	public static Usuario buscarUsuario(String u){
		if(u==null) return  null;
		java.util.List<Usuario> lista=sessao.getUsuarios();
		for (Iterator<Usuario> iterator = lista.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			if(usuario.getNome().equals(u)){
				return usuario;
			}
		}
		return null;
	}
	
	public static Chamados buscarChamado(String c){
		if(c==null) return  null;
		java.util.List<Chamados> lista=sessao.getChamados();
		for (Iterator<Chamados> iterator = lista.iterator(); iterator.hasNext();) {
			Chamados chamado = (Chamados) iterator.next();
			if(chamado.getId().equals(c)){
				return chamado;
			}
		}
		return null;
	}
	
	
	public static ArrayList<Msg> buscarMgs(Usuario u){
		return sessao.buscarMgs(u);
	}
	
	public static void testar(){
		
		for (int i = 0; i < 2; i++) {
			Chamados chamado = new Chamados();
			chamado.setDescricao("descricao "+i);
			chamado.setLatitude(""+(i*10+10));
			chamado.setLongitude(""+(i*10+5));
			chamado.setId(""+i);
			addChamado(chamado);
		}
		
		for (int i = 0; i < 10; i++) {
			Usuario u = new Usuario();
			u.setId(""+i);
			u.setNome("nome "+i);
			u.setLatitude(""+(i*10+5));
			u.setLongitude(""+(i*10+10));
			addUsuario(u);
		}	
	}
	
}
