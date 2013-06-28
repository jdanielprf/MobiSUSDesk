package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import br.ufma.lsd.mobileSUS.entidades.Chamado;
import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.telas.ControllerTelasAbertas;
import br.ufma.lsd.mobileSUS.telas.TelaPrincipal;
import br.ufma.lsd.mobileSUS.telas.help.Sessao;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;

public class LogicaProcessamento {
	private Sessao s=TratarEventos.sessao;
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
					ControllerTelasAbertas.chatInvocar(usuario);
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
					Chamado c = receberChamado(texto);
					if(c!=null){
						usuario.getChamado().setStatus(c.getStatus());
						usuario.getChamado().setRelatorio(c.getRelatorio());
					}
					
				}
				
				@Override
				public void receberBytes(byte[] texto,String nome) {
					String pasta2=pasta+usuario.getChamado()+"/";
					File f = new File(pasta2+nome);
					File pai = f.getParentFile();

					if(!pai.exists() && !pai.mkdirs()){
					    throw new IllegalStateException("erro ao criar os diretorios: " + pai);
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
	public void enviarChamado(Chamado chamado) {
		String texto="*";
		texto+="Chamado\n";
		texto+=chamado.getId()+"\n";
		texto+=chamado.getLatitude()+"\n";
		texto+=chamado.getLongitude()+"\n";
		texto+=chamado.getDescricao()+"\n";
		texto+=chamado.getResponsavel().getId()+"\n";
		texto+=chamado.getStatus()+"\n";
		Processamento.get().enviarMsgChat(chamado.getResponsavel().getId(), texto);
	}
	
	public Chamado receberChamado(String chamado) {
		Scanner s=new Scanner(chamado);
		if(s.nextLine().equals("*Chamado")){
			Chamado c = new Chamado();
			c.setId(s.nextLine());
			c.setLatitude(s.nextLine());
			c.setLongitude(s.nextLine());
			c.setRelatorio(s.nextLine());
			c.setStatus(s.nextLine());
			return c;
		}
		return null;
	}

	public void processarRecebimentoChamado(String chamado) {
		Chamado c=receberChamado(chamado);
		if(c!=null){
			Chamado c2 = TratarEventos.buscarChamado(c.getId());
			c2.setRelatorio(c.getRelatorio());
			c2.getResponsavel().setChamado(null);
		}
	}

	

	
}
