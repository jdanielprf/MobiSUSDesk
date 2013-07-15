package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;


import br.ufma.lsd.mbhealthnet.communication.ddstopics.Chat;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.GenericInformation;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.telas.TelaPrincipal;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;

public class MOBHAChat {
	private static String nome = MOBHAUtil.central;
	private static String receber = "e1u2" ;
	private static br.ufma.lsd.mbhealthnet.android.mobha.chat.MOBHAChat chatService;
	private static HashMap<String,InterfaceChat> lista=new HashMap<String,InterfaceChat>();

	private static void recever(Object o) {
		
		System.out.println(">>>>>>>>>>Chat:"+o);
		if(o instanceof GenericInformation){
			GenericInformation g=(GenericInformation)o;
			System.out.println(g.message);
		}
		
		if(o instanceof Chat){
			Chat g=(Chat)o;
			
			System.out.println(g.fromUserName);
			System.out.println(g.message);
			
			if(g.message.startsWith("*Chamado")){
				TelaPrincipal.getProcessamento().processarRecebimentoChamado(g.message);
			
			}else if(g.message.startsWith("*SolicitacaoChamado")){
				enviarChamado(g.fromUserName);
			}else{
				InterfaceChat cal = lista.get(g.fromUserName);
				if(cal!=null){
					cal.receberMsg(g.fromUserName, g.message, null);
				}
			}
		}
	}
	
	
	private static void enviarChamado(String id) {
		Usuario u=TratarEventos.buscarUsuario(id);
		if(u!=null&&u.getChamado()!=null){
			TelaPrincipal.getProcessamento().enviarChamado(u.getChamado());
		}else{
			System.out.println("Sem chamados para "+id);
		}
	}
	public static void cancelar() {
		chatService=null;
	}

	public static void init() {
		System.out.println("!!!!!!!!!!!!"+nome);
		if(chatService!=null){
			return;
		}
		try {

			chatService = new br.ufma.lsd.mbhealthnet.android.mobha.chat.MOBHAChat(nome, settingsProperties());
			chatService.registerSubTopicListener(new PubSubTopicListener() {
				@Override
				public void processTopic(Object o) {
					recever(o);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fim");
	}

	private static InputStream settingsProperties()
			throws FileNotFoundException {
		return new FileInputStream(new File("settings.properties"));
	}

	public static void enviar(String dados,String para) {

		try {
			Chat c = new Chat();
			c.message=dados;
			c.fromUserName=nome;
			c.toUserName=para;
			
			chatService.publishChat(c);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public static void registrar(String u,InterfaceChat c) {
		lista.put(u, c);
	}
	
	public static void main(String[] args) {
		MOBHAChat.nome="e1u1";
		MOBHAChat.receber = "e1u2";
		MOBHAChat.init();
		MOBHAChat.enviar("dados", receber);
	}

}
