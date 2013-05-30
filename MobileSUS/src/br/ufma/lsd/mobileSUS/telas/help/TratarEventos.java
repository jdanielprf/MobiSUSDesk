package br.ufma.lsd.mobileSUS.telas.help;

import java.util.ArrayList;
import java.util.Iterator;

import br.ufma.lsd.mobileSUS.entidades.Chamados;
import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;

public class TratarEventos {
	public static Sessao sessao = new Sessao();

	public static void addChamado(Chamados c) {
		sessao.addChamado(c);
	}

	public static void addUsuario(Usuario u) {
		sessao.addUsuario(u);
	}

	public static void atualizarPosicao(Usuario u, String lat, String log) {
		u.setLatitude(lat);
		u.setLongitude(log);
	}

	public static void atualizarPosicao(String u, String lat, String log) {
		Usuario usuario = buscarUsuario(u);
		if (usuario != null) {
			atualizarPosicao(usuario, lat, log);
		}
	}

	public static void atualizarPosicao(String u, String msg) {
		Usuario usuario = buscarUsuario(u);
		sessao.addMsg(usuario, msg);
	}

	public static Usuario buscarUsuario(String u) {
		if (u == null)
			return null;
		java.util.List<Usuario> lista = sessao.getUsuarios();
		for (Iterator<Usuario> iterator = lista.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			if (usuario.getNome().equals(u)) {
				return usuario;
			}
		}
		return null;
	}

	public static Chamados buscarChamado(String c) {
		if (c == null)
			return null;
		java.util.List<Chamados> lista = sessao.getChamados();
		for (Iterator<Chamados> iterator = lista.iterator(); iterator.hasNext();) {
			Chamados chamado = (Chamados) iterator.next();
			if (chamado.getId().equals(c)) {
				return chamado;
			}
		}
		return null;
	}

	public static ArrayList<Msg> buscarMgs(Usuario u) {
		return sessao.buscarMgs(u);
	}

	public static boolean iniciarAtendimentoChamado(Usuario u, Chamados c) {
		if (u.getChamado() == null) {
			if (c.getResponsavel() != null)
				c.getResponsavel().setChamado(null);
			c.setResponsavel(u);
			u.setChamado(c);
			return true;
		}

		return false;
	}

	public static boolean terminarAtendimentoChamado(Chamados c) {
		c.getResponsavel().setChamado(null);
		c.setStatus(Chamados.STATUS_FECHADO);
		return true;

	}

	public static void testar() {
		double lat = -2.5164330206204784;
		double log = -44.30511474609375;

		for (int i = 1; i <= 2; i++) {

			Chamados chamado = new Chamados();
			chamado.setDescricao("descricao " + i);
			chamado.setLatitude("" + (lat + (i / 10000.0)));
			chamado.setLongitude("" + (log + (i / 10000.0)));
			chamado.setId("" + i);
			addChamado(chamado);
		}

		for (int i = 1; i <= 10; i++) {
			Usuario u = new Usuario();
			u.setId("" + i);
			u.setNome("nome " + i);
			u.setLatitude("" + (lat + (i / 1000.0)));
			u.setLongitude("" + (log + (i / 1000.0)));
			addUsuario(u);
		}
	}

}
