package br.ufma.lsd.mobileSUS.telas;

import java.util.List;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;

import br.ufma.lsd.mobileSUS.entidades.Chamados;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;

public class TelaListaChamados {

	protected Shell shlListaChamados;
	private Table table;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaListaChamados window = new TelaListaChamados();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlListaChamados.open();
		shlListaChamados.layout();
		while (!shlListaChamados.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlListaChamados = new Shell();
		shlListaChamados.setSize(600, 600);
		shlListaChamados.setText("Lista Chamados");

		table = new Table(shlListaChamados, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 0, 584, 522);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		processarTabela(table);
	}

	private void processarTabela(final Table table) {
		String[] titles = { "Nº", "Status", "Latitude", "Logitude" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}
		for (int i = 0; i < table.getColumns().length; i++) {
			table.getColumn(i).pack();
		}
		
		table.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				TableItem item = table.getItem(table.getSelectionIndex());
			
				Chamados chamado = TratarEventos.buscarChamado(item.getText(0));
				ControllerTelasAbertas.abrirChamado(chamado);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
				
			}
		});
		
		carregarTabela(TratarEventos.sessao.getChamados());
	}

	public void carregarTabela(List<Chamados> lista) {

		for (int i = 0; i < lista.size(); i++) {
			Chamados c = lista.get(i);
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, "" + c.getId());
			if (c.getStatus() != null)
				item.setText(1, "" + c.getStatus());
			if (c.getLatitude() != null)
				item.setText(2, "" + c.getLatitude());
			if (c.getLongitude() != null)
				item.setText(3, "" + c.getLongitude());

		}
		for (int i = 0; i < table.getColumns().length; i++) {
			table.getColumn(i).pack();
		}
	}

	public boolean aberto() {

		return !shlListaChamados.isDisposed();
	}

}
