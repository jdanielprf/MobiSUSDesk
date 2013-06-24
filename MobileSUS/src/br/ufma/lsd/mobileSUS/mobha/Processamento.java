package br.ufma.lsd.mobileSUS.mobha;


public class Processamento {
	public static Processamento p;
	public void registarRecebimentoMsgChat(InterfaceChat chat,String id) {

	}
	public void registrarRecebimentoConteudo(InterfaceConteudo conteudo,String id){
		
	}
	public void registrarRecebimetoContexto(InterfaceContexto contexto,String id){
		
	}
	
	public void enviarMsgChat(String id,String msg){
		
	}
	
	public void enviarChamado(String id,String c){

	}
	public static Processamento get(){
		if(p==null){
			p=new Processamento();
		}
		return p;
	}
	
	
}
