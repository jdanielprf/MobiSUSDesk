package br.ufma.lsd.mobileSUS.telas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import br.ufma.lsd.mobileSUS.entidades.Chamado;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.mobha.LogicaProcessamento;
import br.ufma.lsd.mobileSUS.mobha.MOBHAUtil;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;
import br.ufma.lsd.mobileSUS.util.Utilidade;

public class TelaPrincipal {

	protected Shell shell;
	private Table table;
	private Browser browser;
	private List list;
	public static TelaPrincipal window = null;
	private TelaListaChamados tela;
	private java.util.List<Chamado> listaChamados;
	private static LogicaProcessamento processamento;

	public static LogicaProcessamento getProcessamento() {
		return processamento;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			lerID();
			TratarEventos.testar();

			window = new TelaPrincipal();
			window.open();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarDados() {
		carregarTabelaUsuarios(TratarEventos.sessao.getUsuarios());
		criarListaChamados(list, TratarEventos.sessao.getChamados());
		
	}

	public void initProcessamentoInformacoesMBHealthNet() {
		processamento = new LogicaProcessamento();
		processamento.init();
	}

	/**
	 * Open the window.
	 */
	public void open() {

		Display display = Display.getDefault();
		createContents();
		carregarDados();
		initProcessamentoInformacoesMBHealthNet();

		shell.open();
		shell.layout();
		// Display.getDefault().syncExec( new RecarregarMapa());
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell =  new Shell( SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setSize(800, 800);
		shell.setText("MOBISUS");

		browser = new Browser(shell, SWT.NONE);
		browser.setText(Utilidade.lerArquivo("principal.html"));
		browser.setBounds(0, 319, 569, 443);
		new ChamarMensagens(browser, "visualizarMsgs");
		new ChamarChamados(browser, "visualizarChamado");

		Button btnAtualizarMapa = new Button(shell, SWT.NONE);
		btnAtualizarMapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				carregarMapa();
			}
		});
		btnAtualizarMapa.setBounds(575, 10, 184, 25);
		btnAtualizarMapa.setText("Atualizar Mapa");

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 10, 569, 302);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);

		criarTabelaUsuarios(table);

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmArquivo = new MenuItem(menu, SWT.CASCADE);
		mntmArquivo.setText("Arquivo");

		Menu menu_2 = new Menu(mntmArquivo);
		mntmArquivo.setMenu(menu_2);

		MenuItem mntmSair = new MenuItem(menu_2, SWT.NONE);
		mntmSair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
			}
		});
		mntmSair.setText("Sair");

		MenuItem mntmPreferencias = new MenuItem(menu, SWT.CASCADE);
		mntmPreferencias.setText("Preferencias");

		Menu menu_5 = new Menu(mntmPreferencias);
		mntmPreferencias.setMenu(menu_5);

		MenuItem mntmServidor = new MenuItem(menu_5, SWT.NONE);
		mntmServidor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				configInfoServidor();
			}
		});
		mntmServidor.setText("Configura\u00E7\u00F5es");

		MenuItem mntmReiniciar = new MenuItem(menu_5, SWT.NONE);
		mntmReiniciar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				resetarConexaoServidor();
			}
		});
		mntmReiniciar.setText("Reiniciar");

		MenuItem mntmChamados = new MenuItem(menu, SWT.CASCADE);
		mntmChamados.setText("Chamados");

		Menu menu_3 = new Menu(mntmChamados);
		mntmChamados.setMenu(menu_3);

		MenuItem mntmAdicionar = new MenuItem(menu_3, SWT.NONE);
		mntmAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new TelaChamado().open();
			}
		});
		mntmAdicionar.setText("Adicionar");

		MenuItem mntmListar = new MenuItem(menu_3, SWT.NONE);
		mntmListar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (tela != null && tela.aberto()) {

				} else {
					tela = new TelaListaChamados();
					tela.open();
				}
			}
		});
		mntmListar.setText("Listar");

		MenuItem mntmAjdua = new MenuItem(menu, SWT.CASCADE);
		mntmAjdua.setText("Ajuda");

		Menu menu_4 = new Menu(mntmAjdua);
		mntmAjdua.setMenu(menu_4);

		MenuItem mntmManual = new MenuItem(menu_4, SWT.NONE);
		mntmManual.setText("Manual");

		MenuItem mntmSobre = new MenuItem(menu_4, SWT.NONE);
		mntmSobre.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new TelaHelpSobre().open();
			}
		});
		mntmSobre.setText("Sobre");

		Menu menu_1 = new Menu(shell);
		shell.setMenu(menu_1);

		list = new List(shell, SWT.BORDER);
		list.setBounds(575, 340, 199, 392);

		Label lblNovasOcorrencias = new Label(shell, SWT.NONE);
		lblNovasOcorrencias.setBounds(575, 319, 188, 15);
		lblNovasOcorrencias.setText("Novas Ocorrencias:");

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				new TelaChamado().open();
			}
		});
		btnNewButton.setBounds(575, 41, 184, 25);
		btnNewButton.setText("Nova Ocorrencia");
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				System.out.println("Salvando  banco");
				/*
				try{
				MOBHAConteudo.upload("sus.sql", Utilidade.lerArquivo("sus.db").getBytes());
				}catch(Exception e){
					e.printStackTrace();
				}
				*/
				System.exit(0);
			}
		});
		
		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				try {
					if (list.getSelectionIndex() >= 0) {
						Chamado chamado = listaChamados.get(list
								.getSelectionIndex());
						System.out.println(chamado);
						ControllerTelasAbertas.abrirChamado(chamado);
					//	TelaChamado c = new TelaChamado(chamado);
					//	c.open();
						list.select(-1);
			
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		

		processarMapa();
	}

	protected void configInfoServidor() {
		new TelaConfig().open();
	}

	protected void resetarConexaoServidor() {
		processamento.cancelar();
		processamento.init();
	}

	void processarMapa() {

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							carregarMapa();
						}
					});
				}
			}
		}).start();
	}

	void carregarMapa() {

		// System.out.println("Carregar mapa");
		try {
			browser.evaluate("clear();");
		} catch (Exception e) {
			// e.printStackTrace();
		}
		java.util.List<Usuario> lista = TratarEventos.sessao.getUsuarios();
		for (Iterator<Usuario> iterator = lista.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			if(usuario.getLatitude()==null||usuario.getLongitude()==null){
				continue;
			}
			// System.out.println(usuario);
			// System.out.println(usuario.getChamado());
			StringBuffer exec = new StringBuffer("addUnidadeOcupada('");
			if (usuario.getChamado() != null) {
				exec = new StringBuffer("addUnidadeLivre('");
			}
			exec.append("" + usuario.getNome());
			exec.append("',");
			exec.append(usuario.getLatitude());
			exec.append(",");
			exec.append(usuario.getLongitude());
			exec.append(");");
			try {
				browser.evaluate(exec.toString());
			} catch (Exception e) {
				// e.printStackTrace();
			}
			// System.out.println(exec);
		}

		java.util.List<Chamado> l2 = TratarEventos.sessao.getChamados();

		for (Iterator<Chamado> iterator = l2.iterator(); iterator.hasNext();) {
			Chamado chamado = (Chamado) iterator.next();
			if (chamado.getLatitude() == null || chamado.getLongitude() == null) {
				continue;
			}
			StringBuffer exec = new StringBuffer("addChamado('");
			if (chamado.getResponsavel() != null) {
				exec = new StringBuffer("addChamadoAtendimento('");
			}
			exec.append("" + chamado.getId());
			exec.append("',");
			exec.append(chamado.getLatitude());
			exec.append(",");
			exec.append(chamado.getLongitude());
			exec.append(");");
			try {
				browser.evaluate(exec.toString());
			//	System.out.println(exec.toString());
			} catch (Exception e) {
				// e.printStackTrace();
			}
			// System.out.println(exec);
		}

	}
	public void limpar() {
		list.select(-1);
	}

	void criarListaChamados(final List lista,
			final java.util.List<Chamado> chamados) {
		lista.removeAll();
		listaChamados=chamados;
		
		for (Iterator<Chamado> iterator = chamados.iterator(); iterator
				.hasNext();) {
			Chamado c = (Chamado) iterator.next();
			if(!c.getStatus().equals(Chamado.STATUS_FECHADO))
			lista.add(c.toString());
		}
		lista.select(-1);
		
		
	}

	void criarTabelaUsuarios(final Table table) {
		String[] titles = { "Unidade", "Nome", "Situa��o", "N� Ocorrencia",
				"Localiza��o","Status" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}

		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {

				TableItem item = table.getItem(table.getSelectionIndex());
				Usuario usuario = TratarEventos.buscarUsuario(item.getText(1));

				ControllerTelasAbertas.abrirMgs(usuario);
			}
		});

		for (int i = 0; i < table.getColumns().length; i++) {
			table.getColumn(i).pack();
		}
	}

	public void carregarTabelaUsuarios(java.util.List<Usuario> lista) {
		table.removeAll();
		for (int i = 0; i < lista.size(); i++) {
			Usuario u = lista.get(i);
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "" + u.getId());
			item.setText(1, "" + u.getNome());
			
			if (u.getChamado() == null) {
			//	item.setText(2, "Livre");
				item.setText(3, "");
			} else {
			//	item.setText(2, "Ocupado");
				item.setText(3, "" + u.getChamado().getId());
			}
			
			if(u.getStatus()!=null){
				item.setText(2,u.getStatus());
			}else{
				item.setText(2, "?");
			}
			
			if (u.getLatitude() != null && u.getLongitude() != null) {
				item.setText(4, "(" + u.getLatitude() + "," + u.getLongitude()
						+ ")");
			} else {
				item.setText(4, "(?,?)");
			}
			
		}

		for (int i = 0; i < table.getColumns().length; i++) {
			table.getColumn(i).pack();
		}
		table.redraw();
		table.setVisible(true);
	}

	
	
	
	///////////////////////////////////////////////////////////////////////
	static class ChamarMensagens extends BrowserFunction {
		ChamarMensagens(Browser browser, String name) {
			super(browser, name);
		}

		public Object function(Object[] arguments) {

			Usuario usuario = TratarEventos.buscarUsuario("" + arguments[0]);
			new TelaChat().open(usuario);
			return null;
		}
	}

	static class ChamarChamados extends BrowserFunction {
		ChamarChamados(Browser browser, String name) {
			super(browser, name);
		}

		public Object function(Object[] arguments) {

			Chamado c = TratarEventos.buscarChamado(Integer.parseInt(""
					+ arguments[0]));
			new TelaChamado(c).open();
			return null;
		}
	}
	
	public static void lerID(){
		Scanner scan;
		try {
			scan = new Scanner(new FileInputStream("id.txt"));
			if(scan.hasNextLine()){
				MOBHAUtil.central=scan.nextLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
