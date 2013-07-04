package br.ufma.lsd.mobileSUS.telas.help;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.ufma.lsd.mobileSUS.entidades.Chamado;
import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;

public class DAO {
//	private ArrayList<Chamado> chamados = new ArrayList<Chamado>();
//	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	private HashMap<String, ArrayList<Msg>> mensagens = new HashMap<String, ArrayList<Msg>>();
	private String dir = "arquivos/";

	private static EntityManager em;

	static {
		try {
			em = Persistence.createEntityManagerFactory("conexao")
					.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Chamado> getChamados() {
		javax.persistence.Query query = em.createQuery("FROM Chamado");       
		return query.getResultList(); 
	}
	
	public List<Chamado> getTodosChamados() {
		javax.persistence.Query query = em.createQuery("FROM Chamado c WHERE c.status<>'FECHADO'");       
		return query.getResultList(); 
	}

	public boolean removerChamado(Chamado o) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.remove(o);
		t.commit();
		return true;
	}



	public List<Usuario> getUsuarios() {
		javax.persistence.Query query = em.createQuery("FROM Usuario u");       
		return query.getResultList(); 
	}


	public void addChamado(Chamado c) {
		salvar(c);
	}

	public void addUsuario(Usuario u) {
		salvar(u);
	}

	public void salvar(Object o) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(o);
		t.commit();
	}

	public ArrayList<Msg> buscarMgs(Usuario u) {
		return mensagens.get(u.getNome());
	}

	public void addMsgRecebida(Msg msg) {
		ArrayList<Msg> listaMgs = mensagens.get(msg.getRemetente().getNome());
		System.out.println("de:" + msg.getRemetente().getNome());
		if (listaMgs == null) {
			listaMgs = new ArrayList<Msg>();
			mensagens.put(msg.getRemetente().getNome(), listaMgs);
		}

		listaMgs.add(msg);
	}

	/**
	 * Ao receber uam mensagem de um usuario é necessario adicinar uma nova
	 * mensagem na caixa de mensagem de acordo com que estar falando é
	 * necessario que o destino seja diferente de NULL, pois se naão, não é
	 * possivel identificar com quem estar falando
	 * 
	 * @param msg
	 */
	public void addMsgEnviada(Msg msg) {
		ArrayList<Msg> listaMgs = mensagens.get(msg.getDestino().getNome());
		if (listaMgs == null) {
			listaMgs = new ArrayList<>();
			mensagens.put(msg.getDestino().getNome(), listaMgs);
		}
		listaMgs.add(msg);
	}

	/**
	 * Adcina uma nova mensagem na caixa de mensagem a quem deseja falar
	 * 
	 * @param msg
	 */
	public void addMsg(Usuario u, String msg) {
		if (!mensagens.containsKey(u)) {
			mensagens.put(u.getNome(), new ArrayList<Msg>());
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
	
	public Usuario buscarUsuario(String idUsuario) {
		List<Usuario> lista = getUsuarios();
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			if(usuario.getId()==idUsuario){
				return usuario;
			}
		}
		return  null;
	}
}
