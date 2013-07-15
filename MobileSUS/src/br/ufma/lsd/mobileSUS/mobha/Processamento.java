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
	
	
	public void registrarRecebimetoContexto(String id){
		MOBHAContexto.init();
		MOBHAContexto.registrar(id);	
	}
	
	public void enviarMsgChat(String id,String msg){
		MOBHAChat.init();
		MOBHAChat.enviar(msg, id);
	}
	

	public static Processamento get(){
		if(p==null){
			p=new Processamento();
		}
		return p;
	}
	
	
}
