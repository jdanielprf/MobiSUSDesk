package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import br.ufma.lsd.mobileSUS.entidades.Chamados;
import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.telas.help.Sessao;

public class LogicaProcessamento {
	Sessao s;
	public String pasta="arquivos/";
	public void processarChat() {
		ArrayList<Usuario> listaUsuarios = s.getUsuarios();
		for (Iterator<Usuario> iterator = listaUsuarios.iterator(); iterator.hasNext();) {
			final Usuario usuario = (Usuario) iterator.next();
			Processamento.get().registarRecebimentoMsgChat(new InterfaceChat() {
				@Override
				public void receberMsg(String id, String texto, String data) {
					Msg msg = new Msg();
					msg.setRemetente(usuario);
					msg.setMsg(texto);
					s.addMsgRecebida(msg);
				}
			}, usuario.getId());
		}
	}

	public void processarRecebimentoConteudo() {
		ArrayList<Usuario> listaUsuarios = s.getUsuarios();
		for (Iterator<Usuario> iterator = listaUsuarios.iterator(); iterator.hasNext();) {
			final Usuario usuario = (Usuario) iterator.next();
			Processamento.get().registrarRecebimentoConteudo(new InterfaceConteudo() {
				
				@Override
				public void receberTexto(String texto,String nome) {
					Chamados c = receberChamado(texto);
					if(c!=null){
						usuario.getChamado().setStatus(c.getStatus());
						usuario.getChamado().setRelatorio(c.getRelatorio());
					}
					
				}
				
				@Override
				public void receberBytes(byte[] texto,String nome) {
					String pasta2=pasta+usuario.getChamado()+"/";
					File f = new File(pasta2+nome);
					File parent = f.getParentFile();

					if(!parent.exists() && !parent.mkdirs()){
					    throw new IllegalStateException("Couldn't create dir: " + parent);
					}
					
					PrintWriter gravar;
					try {
						gravar = new PrintWriter(f);
						gravar.print(texto);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
				}
			}, usuario.getId());
		}
	}
	
	public void processarRecebimentoInfoContexto() {
		ArrayList<Usuario> listaUsuarios = s.getUsuarios();
		for (Iterator<Usuario> iterator = listaUsuarios.iterator(); iterator.hasNext();) {
			final Usuario usuario = (Usuario) iterator.next();
			Processamento.get().registrarRecebimetoContexto(new InterfaceContexto() {
				
				@Override
				public void receberCoordenadas(String lat, String log) {
					usuario.setLatitude(lat);
					usuario.setLongitude(log);
				}
			}, usuario.getId());
		}
	}
	public void enviarChamado(Chamados chamado) {
		String texto="";
		texto+="Chamado";
		texto+=chamado.getId();
		texto+=chamado.getLatitude();
		texto+=chamado.getLongitude();
		texto+=chamado.getDescricao();
		texto+=chamado.getResponsavel().getId();
		texto+=chamado.getStatus();
		Processamento.get().enviarChamado(chamado.getResponsavel().getId(), texto);
	}
	
	public Chamados receberChamado(String chamado) {
		Scanner s=new Scanner(chamado);
		if(s.nextLine().equals("Chamado")){
			Chamados c = new Chamados();
			c.setId(s.nextLine());
			c.setLatitude(s.nextLine());
			c.setLongitude(s.nextLine());
			c.setRelatorio(s.nextLine());
			c.setStatus(s.nextLine());
			return c;
		}
		return null;
	}
}
