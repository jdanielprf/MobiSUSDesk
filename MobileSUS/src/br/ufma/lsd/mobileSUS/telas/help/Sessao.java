package br.ufma.lsd.mobileSUS.telas.help;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import br.ufma.lsd.mobileSUS.entidades.Chamados;
import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;

public class Sessao {
	private ArrayList<Chamados> chamados=new ArrayList<Chamados>();
	private ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
	private HashMap<String, ArrayList<Msg>> mensagens=new HashMap<String, ArrayList<Msg>>();
	private String dir="arquivos/";
	public ArrayList<Chamados> getChamados() {
		return chamados;
	}
	public boolean removeEvent(Chamados o) {
		return chamados.remove(o);
	}
	
	public void setChamados(ArrayList<Chamados> chamados) {
		this.chamados = chamados;
	}
	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void addChamado(Chamados c) {
		chamados.add(c);
	}
	public void addUsuario(Usuario u) {
		usuarios.add(u);
	}
	
	public ArrayList<Msg> buscarMgs(Usuario u) {
		return mensagens.get(u.getNome());
	}
	
	public void addMsgRecebida(Msg msg) {
		ArrayList<Msg> listaMgs = mensagens.get(msg.getRemetente().getNome());
		System.out.println("de:"+msg.getRemetente().getNome());
		if(listaMgs==null){
			listaMgs=new ArrayList<Msg>();
			mensagens.put(msg.getRemetente().getNome(),listaMgs );
		}
		
		listaMgs.add(msg);
	}
	
	/** 
	 * Ao receber uam mensagem de um usuario é necessario adicinar uma nova mensagem 
	 * na caixa de mensagem de acordo com que estar falando
	 * é necessario que o destino seja diferente de NULL, pois se naão, não é possivel identificar com quem estar falando
	 * @param msg
	 */
	public void addMsgEnviada(Msg msg) {
		ArrayList<Msg> listaMgs = mensagens.get(msg.getDestino().getNome());
		if(listaMgs==null){
			listaMgs=new ArrayList<>();
			mensagens.put(msg.getDestino().getNome(), listaMgs);
		}
		listaMgs.add(msg);
	}
	/** 
	 * Adcina uma nova mensagem na caixa de mensagem a quem deseja falar
	 * @param msg
	 */
	public void addMsg(Usuario u,String msg) {
		if(!mensagens.containsKey(u)){
			mensagens.put(u.getNome(),new ArrayList<Msg>());
		}
		
		Msg m = new Msg();
		m.setData(new Date(System.currentTimeMillis()));
		m.setMsg(msg);
		m.setDestino(u);
		mensagens.get(u).add(m);
		
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
}
