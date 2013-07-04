package br.ufma.lsd.mobileSUS.telas;

import java.util.HashMap;

import br.ufma.lsd.mobileSUS.entidades.Chamado;
import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;

public class ControllerTelasAbertas {
	private static HashMap<Usuario, TelaChat> listaTelasMensagensAbertas = new HashMap<>();
	private static HashMap<Chamado, TelaChamado> listaTelasChamadosAbertas = new HashMap<>();
	
	public static void abrirMgs(Usuario u) {
		System.out.println(listaTelasMensagensAbertas.containsKey(u));
		if(!listaTelasMensagensAbertas.containsKey(u)){
			System.out.println("Abrir Mensagens"+u);
			TelaChat tela = new TelaChat();
			listaTelasMensagensAbertas.put(u, tela);
			tela.open(u);
			
		}else{
			listaTelasMensagensAbertas.get(u).focus();
		}
	}

	public static void fecharMgs(Usuario u) {
		System.out.println("Fechar tela:"+u+":"+listaTelasMensagensAbertas.containsKey(u));
		if(listaTelasMensagensAbertas.containsKey(u)){
			listaTelasMensagensAbertas.remove(u);
		}
	}
	
	public static void forcarFecharMgs(Usuario u) {
		if(listaTelasMensagensAbertas.containsKey(u)){
			TelaChat tela = listaTelasMensagensAbertas.get(u);
			tela.fechar();
			listaTelasMensagensAbertas.remove(u);
		}
	}
	
	
	
	public static void abrirChamado(Chamado c) {
		System.out.println(listaTelasChamadosAbertas.containsKey(c));
		if(!listaTelasChamadosAbertas.containsKey(c)){
			System.out.println("Abrir Mensagens"+c);
			TelaChamado tela = new TelaChamado(c);
			listaTelasChamadosAbertas.put(c, tela);
			tela.open();
		
		}else{
			listaTelasChamadosAbertas.get(c).focus();
		}
	}

	public static void fecharChamado(Chamado c) {
		System.out.println("Fechar tela:"+c+":"+listaTelasChamadosAbertas.containsKey(c));
		if(listaTelasMensagensAbertas.containsKey(c)){
			listaTelasChamadosAbertas.remove(c);
		}
	}
	
	public static void forcarFecharChamado(Chamado c) {
		if(listaTelasChamadosAbertas.containsKey(c)){
			TelaChamado tela = listaTelasChamadosAbertas.get(c);
			tela.fechar();
			listaTelasChamadosAbertas.remove(c);
		}
	}
	
	public static void chatInvocar(Usuario u, Msg msg) {
		System.out.println("tela:"+listaTelasMensagensAbertas.containsKey(u));
		if(listaTelasMensagensAbertas.containsKey(u)){
			TelaChat tela = listaTelasMensagensAbertas.get(u);
			tela.mostrarMensagem(msg);
		}
	}
}
