package br.ufma.lsd.mobileSUS.entidades;

import javax.persistence.Persistence;

public class TesteConexao {
	public static void main(String[] args) {
		try {
			
			Persistence.createEntityManagerFactory("conexao")
					.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	void init1() {
//		Configuration cfg = new Configuration()
//				.addAnnotatedClass(Chamado.class)
//				.addAnnotatedClass(Usuario.class)
//				.setProperty("hibernate.hbm2ddl.auto", "create")
//				.setProperty("hibernate.connection.url", "jdbc:sqlite:sus.db")
//				.setProperty("hibernate.show_sql", "true")
//				.setProperty("javax.persistence.jdbc.driver", "org.sqlite.JDBC")
//				.setProperty("hibernate.dialect",
//						"br.ufma.lsd.mobileSUS.util.SQLiteDialect");
//
//		SessionFactory sessions = cfg.buildSessionFactory();
//		Session session = sessions.openSession(); // open a new Session
//	}
}
