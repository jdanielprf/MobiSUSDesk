package br.ufma.lsd.mobileSUS.mobha;


public class Processamento {
	public static Processamento p;
	public void registarRecebimentoMsgChat(InterfaceChat chat,String id) {
		MOBHAChat.init();
		MOBHAChat.registrar(id, chat);
	}
	
	public void registrarRecebimentoConteudo(InterfaceConteudo conteudo,String id){
		
	}
	
	public void registrarRecebimetoContexto(InterfaceContexto contexto,String id){
		MOBHAContexto.init();
		MOBHAContexto.registrar(id, contexto);	
	}
	
	public void enviarMsgChat(String id,String msg){
		MOBHAChat.init();
		MOBHAChat.enviar(msg, id);
	}
	
	public void enviarChamado(String id,String c){
		MOBHAConteudo.init();
		MOBHAConteudo.upload(id, c.getBytes());
	}
	
	public static Processamento get(){
		if(p==null){
			p=new Processamento();
		}
		return p;
	}
	
	
}
