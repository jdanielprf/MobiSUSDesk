package br.ufma.lsd.mobileSUS.telas;

import java.util.ArrayList;
import java.util.Iterator;

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

import br.ufma.lsd.mobileSUS.entidades.Chamados;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;
import br.ufma.lsd.mobileSUS.util.Utilidade;

public class TelaPrincipal {

	protected Shell shell;
	private Table table;
	private Browser browser;
	private BrowserFunction function;
	private List list;
	public static TelaPrincipal window = null;
	private TelaListaChamados tela;
	private ChamarChamados function2;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
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

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		carregarDados();
		shell.open();
		shell.layout();
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
		shell = new Shell();
		shell.setSize(800, 800);
		shell.setText("MOBISUS");

		browser = new Browser(shell, SWT.NONE);
		browser.setText(Utilidade.lerArquivo("teste.html"));
		browser.setBounds(0, 319, 569, 443);
		function = new ChamarMensagens(browser, "visualizarMsgs");
		function2 = new ChamarChamados(browser, "visualizarChamado");
		

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
		mntmServidor.setText("Servidor");

		MenuItem mntmReiniciar = new MenuItem(menu_5, SWT.NONE);
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
				System.exit(0);
			}
		});
	}

	void carregarMapa() {
		browser.evaluate("clear();");
		ArrayList<Usuario> lista = TratarEventos.sessao.getUsuarios();
		for (Iterator<Usuario> iterator = lista.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			StringBuffer exec = new StringBuffer("addUnidade('");
			exec.append("" + usuario.getNome());
			exec.append("',");
			exec.append(usuario.getLatitude());
			exec.append(",");
			exec.append(usuario.getLongitude());
			exec.append(");");
			try {
				browser.evaluate(exec.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(exec);
		}

		ArrayList<Chamados> l2 =TratarEventos.sessao.getChamados();

		for (Iterator<Chamados> iterator = l2.iterator(); iterator.hasNext();) {
			Chamados chamado = (Chamados) iterator.next();
			StringBuffer exec = new StringBuffer("addChamado('");
			exec.append("" + chamado.getId());
			exec.append("',");
			exec.append(chamado.getLatitude());
			exec.append(",");
			exec.append(chamado.getLongitude());
			exec.append(");");
			try {
				browser.evaluate(exec.toString());
				System.out.println(exec.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(exec);
		}

	}

	void criarListaChamados(final List lista, final ArrayList<Chamados> chamados) {
		lista.removeAll();
		for (Iterator<Chamados> iterator = chamados.iterator(); iterator
				.hasNext();) {
			Chamados c = (Chamados) iterator.next();
			lista.add(c.toString());
		}
		lista.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				try {
					if (lista.getSelectionIndex() >= 0) {
						Chamados chamado = chamados.get(lista
								.getSelectionIndex());
						System.out.println(chamado);
						TelaChamado c = new TelaChamado(chamado);
						c.open();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	void criarTabelaUsuarios(final Table table) {
		String[] titles = { "Unidade", "Nome", "Status", "Nº Ocorrencia",
				"Localização" };
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
			Usuario c = lista.get(i);
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "" + c.getId());
			item.setText(1, "" + c.getNome());
			if(c.getChamado()==null){
				item.setText(2, "Livre");
				item.setText(3, "");
			}else{
				item.setText(2, "Ocupado");
				item.setText(3, c.getChamado().getId());
			}
			item.setText(4, "(" + c.getLatitude() + "," + c.getLongitude()
					+ ")");
		}

		for (int i = 0; i < table.getColumns().length; i++) {
			table.getColumn(i).pack();
		}
	}

	static class ChamarMensagens extends BrowserFunction {
		ChamarMensagens(Browser browser, String name) {
			super(browser, name);
		}

		public Object function(Object[] arguments) {

			Usuario usuario = TratarEventos.buscarUsuario("" + arguments[0]);
			new TelaMensagem().open(usuario);
			return null;
		}
	}
	static class ChamarChamados extends BrowserFunction {
		ChamarChamados(Browser browser, String name) {
			super(browser, name);
		}

		public Object function(Object[] arguments) {

			Chamados c = TratarEventos.buscarChamado("" + arguments[0]);
			new TelaChamado(c).open();
			return null;
		}
	}
}
