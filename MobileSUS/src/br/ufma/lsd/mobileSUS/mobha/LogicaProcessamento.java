package br.ufma.lsd.mobileSUS.mobha;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.eclipse.swt.widgets.Display;

import br.ufma.lsd.mobileSUS.entidades.Chamado;
import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.telas.ControllerTelasAbertas;
import br.ufma.lsd.mobileSUS.telas.TelaPrincipal;
import br.ufma.lsd.mobileSUS.telas.help.DAO;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;

public class LogicaProcessamento {
	private DAO s = TratarEventos.sessao;
	public String pasta = "arquivos/";
	private boolean check=false;
	public void init() {
		 
		Thread tr = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Conectando...");
				processarChat();
				processarRecebimentoInfoContexto();
				check=true;
				System.out.println("Conectado");
			}
		});
		tr.start();
		
	
		ActionListener action = new ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent e) {  
            	if(!check){
            		JOptionPane.showMessageDialog(null, "Não foi possivel estabelecer conexao com o servidor!");
            	}
                
            }  
        }; 
        
        Timer t = new Timer(4*1000, action);
        t.setRepeats(false);
		t.start();
	}

	public void processarChat() {
		List<Usuario> listaUsuarios = s.getUsuarios();
		for (Iterator<Usuario> iterator = listaUsuarios.iterator(); iterator
				.hasNext();) {
			final Usuario usuario = (Usuario) iterator.next();
			Processamento.get().registarRecebimentoMsgChat(new InterfaceChat() {
				@Override
				public void receberMsg(String id, String texto, String data) {
					final Msg msg = new Msg();
					msg.setRemetente(usuario);
					msg.setMsg(texto);
					s.addMsgRecebida(msg);

					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							TelaPrincipal.window
									.carregarTabelaUsuarios(TratarEventos.sessao
											.getUsuarios());
							ControllerTelasAbertas.chatInvocar(usuario, msg);
						}
					});

				}
			}, usuario.getId());
		}
	}

	public void processarRecebimentoConteudo() {
		List<Usuario> listaUsuarios = s.getUsuarios();
		for (Iterator<Usuario> iterator = listaUsuarios.iterator(); iterator
				.hasNext();) {
			final Usuario usuario = (Usuario) iterator.next();
			Processamento.get().registrarRecebimentoConteudo(
					new InterfaceConteudo() {

						@Override
						public void receberTexto(String texto, String nome) {
							Chamado c = lerChamado(texto);
							if (c != null) {
								usuario.getChamado().setStatus(c.getStatus());
								usuario.getChamado().setRelatorio(
										c.getRelatorio());
							}

						}

						@Override
						public void receberBytes(byte[] texto, String nome) {
							String pasta2 = pasta + usuario.getChamado() + "/";
							File f = new File(pasta2 + nome);
							File pai = f.getParentFile();

							if (!pai.exists() && !pai.mkdirs()) {
								throw new IllegalStateException(
										"erro ao criar os diretorios: " + pai);
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
		List<Usuario> listaUsuarios = s.getUsuarios();
		for (Iterator<Usuario> iterator = listaUsuarios.iterator(); iterator
				.hasNext();) {
			final Usuario usuario = (Usuario) iterator.next();
			Processamento.get().registrarRecebimetoContexto(
					new InterfaceContexto() {

						@Override
						public void receberCoordenadas(String lat, String log) {
							usuario.setLatitude(lat);
							usuario.setLongitude(log);
						}
					}, usuario.getId());
		}
	}

	public void enviarChamado(Chamado chamado) {
		if (chamado.getResponsavel() != null) {
			String texto = "*";
			texto += "Chamado\n";
			texto += chamado.getId() + "\n";
			texto += chamado.getLatitude() + "\n";
			texto += chamado.getLongitude() + "\n";
			texto += chamado.getDescricao() + "\n";
			texto += chamado.getStatus() + "\n";
			texto += chamado.getRelatorio() + "\n";

			Processamento.get().enviarMsgChat(chamado.getResponsavel().getId(),
					texto);
		}
	}

	public Chamado lerChamado(String chamado) {
		Scanner s = new Scanner(chamado);
		if (s.nextLine().startsWith("*Chamado")) {
			Chamado c = new Chamado();
			c.setId(Integer.parseInt(s.nextLine()));
			c.setLatitude(s.nextLine());
			c.setLongitude(s.nextLine());
			c.setDescricao(s.nextLine());
			c.setStatus(s.nextLine());
			c.setRelatorio(s.nextLine());
			s.close();
			return c;
		}
		s.close();
		return null;
	}

	public void processarRecebimentoChamado(String chamado) {
		Chamado c = lerChamado(chamado);
		if (c != null) {
			Chamado c2 = TratarEventos.buscarChamado(c.getId());
			c2.setRelatorio(c.getRelatorio());
			c2.getResponsavel().setChamado(null);
		}
	}

	public void cancelar() {
		// TODO Auto-generated method stub
	}

}
